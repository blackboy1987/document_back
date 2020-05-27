package com.igomall.wechat.controller;

import com.igomall.entity.wechat.BaiDuResource;
import com.igomall.entity.wechat.BaiDuTag;
import com.igomall.service.wechat.BaiDuResourceService;
import com.igomall.service.wechat.BaiDuTagService;
import com.igomall.util.JsonUtils;
import com.igomall.util.XmlUtils;
import com.igomall.wechat.entity.WeChatMessage;
import com.igomall.wechat.entity.WeChatUser;
import com.igomall.wechat.entity.WeChatUserLog;
import com.igomall.wechat.entity.event.BaseEvent;
import com.igomall.wechat.entity.response.WeChatUserResponse;
import com.igomall.wechat.entity.send.MsgType;
import com.igomall.wechat.entity.send.TextMessage;
import com.igomall.wechat.service.*;
import com.igomall.wechat.util.SignUtil;
import com.igomall.wechat.util.WechatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController("wechatIndexController")
@RequestMapping("/wechat")
public class IndexController {

    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private WechatUserLogService wechatUserLogService;
    @Autowired
    private WechatMessageService wechatMessageService;
    @Autowired
    private BaiDuTagService baiDuTagService;
    @Autowired
    private BaiDuResourceService baiDuResourceService;
    @Autowired
    private SubscribeLogService subscribeLogService;

    @Resource
    private WechatAutoReplyMessageService wechatAutoReplyMessageService;

    @GetMapping
    public String index(String signature, String timestamp, String nonce, String echostr) {
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return "error";
    }


    @PostMapping
    public String index(HttpServletRequest request) {
        Map<String, String> map = WechatUtils.parseXml(request);
        String event = map.get("event");
        WeChatMessage weChatMessage = null;
        try {
            weChatMessage = wechatMessageService.saveMessage(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotEmpty(event)) {
            // 处理订阅事件
            return subscribe(map, weChatMessage);
        } else {
            return parseMessage(map, weChatMessage);
        }
    }


    private String subscribe(Map<String, String> map, WeChatMessage weChatMessage) {
        String event = map.get("event");
        BaseEvent baseEvent = null;
        TextMessage textMessage = null;
        WeChatUserLog weChatUserLog = new WeChatUserLog();
        weChatUserLog.setEvent(event);
        baseEvent = JsonUtils.toObject(JsonUtils.toJson(map), BaseEvent.class);
        WeChatUser weChatUser = wechatUserService.findByOpenId(baseEvent.getFromUserName());
        Integer status = 0;
        if (StringUtils.equalsAnyIgnoreCase(event, "unsubscribe")) {
            // 取消关注
            status = 2;
            weChatUserLog.setMemo("取消关注");
        } else if (StringUtils.equalsAnyIgnoreCase(event, "subscribe")) {
            status = 1;
            weChatUserLog.setMemo("关注");
            StringBuffer sb = new StringBuffer();
            sb.append("感谢您的关注\n\n");
            sb.append(wechatMessageService.getHelpMessage());
            // 关注就给回复消息
            textMessage = new TextMessage();
            textMessage.setContent(sb.toString());
            textMessage.setFromUserName(baseEvent.getToUserName());
            textMessage.setToUserName(baseEvent.getFromUserName());
            textMessage.setMsgType(MsgType.text);
        }
        if (weChatUser == null) {
            // 创建用户
            weChatUser = new WeChatUser();
            WeChatUserResponse weChatUserResponse = WechatUtils.getUserInfo(baseEvent.getFromUserName());
            if (weChatUserResponse.getErrCode() != -1) {
                BeanUtils.copyProperties(weChatUserResponse, weChatUser, "id", "fromUserName", "status", "updateTime");
            }
            weChatUser.setOpenId(baseEvent.getFromUserName());
            weChatUser.setStatus(status);
            weChatUser.setUpdateTime(new Date(baseEvent.getCreateTime()));
            wechatUserService.save(weChatUser);

        } else {
            WeChatUserResponse weChatUserResponse = WechatUtils.getUserInfo(baseEvent.getFromUserName());
            if (weChatUserResponse.getErrCode() != -1) {
                BeanUtils.copyProperties(weChatUserResponse, weChatUser, "id", "fromUserName", "status", "updateTime");
            }
            weChatUser.setStatus(status);
            weChatUser.setUpdateTime(new Date(baseEvent.getCreateTime()));
            wechatUserService.update(weChatUser);
        }
        weChatUserLog.setContent(JsonUtils.toJson(baseEvent));
        weChatUserLog.setWeChatUser(weChatUser);
        wechatUserLogService.save(weChatUserLog);
        if (StringUtils.equalsAnyIgnoreCase(event, "unsubscribe")) {
            subscribeLogService.save(weChatUser, "取消关注", 2);
        } else if (StringUtils.equalsAnyIgnoreCase(event, "subscribe")) {
            subscribeLogService.save(weChatUser, "关注", 1);
        }


        if (textMessage != null) {
            wechatMessageService.updateMessage(weChatMessage, JsonUtils.toJson(textMessage));
            return XmlUtils.toXml(textMessage);
        }
        return null;
    }

    private String parseMessage(Map<String, String> map, WeChatMessage weChatMessage) {
        String msgType = map.get("msgType");
        String content = map.get("content");
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(map.get("toUserName"));
        textMessage.setToUserName(map.get("fromUserName"));
        textMessage.setMsgType(MsgType.text);
        if (StringUtils.equalsAnyIgnoreCase(msgType, com.igomall.wechat.entity.MsgType.text.name())) {
            String regEx = "^\\d{3}$";
            String regEx1 = "^\\d{5}$";
            if (content.matches(regEx)) {
                // 分类
                BaiDuTag baiDuTag = baiDuTagService.findByCode(content);
                if (baiDuTag == null || baiDuTag.getBaiDuResources().isEmpty()) {
                    StringBuffer sb = new StringBuffer();
                    // 关注就给回复消息
                    textMessage.setContent("无相关课程");
                    wechatMessageService.updateMessage(weChatMessage, JsonUtils.toJson(textMessage));
                    return XmlUtils.toXml(textMessage);
                }

                StringBuffer sb = new StringBuffer();
                sb.append("已为您找到如下课程：\n");
                for (BaiDuResource baiDuResource : baiDuTag.getBaiDuResources()) {
                    sb.append("\n" + baiDuResource.getCode() + "  " + baiDuResource.getTitle());
                }
                sb.append("\n\n输入课程前面编号获取课程地址");
                sb.append("\n\n回复“?”显示帮助菜单");
                // 关注就给回复消息
                textMessage.setContent(sb.toString());
            } else if (content.matches(regEx1)) {
                // 课程
                BaiDuResource baiDuResource = baiDuResourceService.findByCode(content);
                if (baiDuResource == null) {
                    StringBuffer sb = new StringBuffer();
                    // 关注就给回复消息
                    textMessage.setContent("无课程");
                }

                StringBuffer sb = new StringBuffer();
                sb.append("课程信息如下：\n");
                sb.append("\n课程名称：" + baiDuResource.getTitle());
                sb.append("\n课程地址：" + baiDuResource.getBaiDuUrl());
                sb.append("\n\n回复“?”显示帮助菜单");
                // 关注就给回复消息
                textMessage.setContent(sb.toString());
            } else {
                String replyContent = wechatAutoReplyMessageService.autoReply(content);
                if (StringUtils.isNotEmpty(replyContent)) {
                    textMessage.setContent(replyContent);
                } else {
                    textMessage.setContent(wechatMessageService.getHelpMessage());
                }
            }
        } else if (StringUtils.equalsAnyIgnoreCase(msgType, com.igomall.wechat.entity.MsgType.video.name())) {
            // 视频消息

        }
        String replyContent = wechatAutoReplyMessageService.autoReply(content);
        if (StringUtils.isNotEmpty(replyContent)) {
            textMessage.setContent(replyContent);
        } else {
            textMessage.setContent(wechatMessageService.getHelpMessage());
        }
        wechatMessageService.updateMessage(weChatMessage, JsonUtils.toJson(textMessage));
        return XmlUtils.toXml(textMessage);


    }
}

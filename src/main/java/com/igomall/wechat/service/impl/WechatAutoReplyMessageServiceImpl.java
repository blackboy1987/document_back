package com.igomall.wechat.service.impl;

import com.igomall.entity.wechat.BaiDuResource;
import com.igomall.entity.wechat.BaiDuTag;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.BaiDuResourceService;
import com.igomall.service.wechat.BaiDuTagService;
import com.igomall.util.JsonUtils;
import com.igomall.util.XmlUtils;
import com.igomall.wechat.dao.WechatAutoReplyMessageDao;
import com.igomall.wechat.entity.WeChatAutoReplyMessage;
import com.igomall.wechat.service.WechatAutoReplyMessageService;
import com.igomall.wechat.service.WechatMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Service
public class WechatAutoReplyMessageServiceImpl extends BaseServiceImpl<WeChatAutoReplyMessage,Long> implements WechatAutoReplyMessageService {

    @Resource
    private WechatAutoReplyMessageDao wechatAutoReplyMessageDao;
    @Resource
    private WechatMessageService wechatMessageService;

    @Resource
    private BaiDuTagService baiDuTagService;
    @Resource
    private BaiDuResourceService baiDuResourceService;

    @Override
    public WeChatAutoReplyMessage findByMsgKey(String msgKey) {
        return wechatAutoReplyMessageDao.find("msgKey",msgKey);
    }

    @Override
    public String autoReply(String msgKey) {
        WeChatAutoReplyMessage wechatAutoReplyMessage = findByMsgKey(msgKey);
        if(wechatAutoReplyMessage!=null){
            if(StringUtils.isNotEmpty(wechatAutoReplyMessage.getCallback())){
                try {
                    System.out.println(wechatAutoReplyMessage.getCallback());
                    Method[] methods = wechatMessageService.getClass().getDeclaredMethods();
                    for (Method method1:methods) {
                        System.out.println(method1.getName());
                    }
                    Method callback = wechatMessageService.getClass().getDeclaredMethod(wechatAutoReplyMessage.getCallback(),String.class);
                    if(callback!=null){
                        String result = (String) callback.invoke(wechatMessageService,msgKey);
                        return result;
                    }
                    return null;
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
            return wechatAutoReplyMessage.getContent();
        }
        return null;
    }


    @Override
    public String autoReplyBaiDuTag(String content) {
        // 分类
        BaiDuTag baiDuTag = baiDuTagService.findByCode(content);
        if (baiDuTag == null || baiDuTag.getBaiDuResources().isEmpty()) {
            return "无相关课程";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("已为您找到如下课程：\n");
        for (BaiDuResource baiDuResource : baiDuTag.getBaiDuResources()) {
            sb.append("\n" + baiDuResource.getCode() + "  " + baiDuResource.getTitle());
        }
        sb.append("\n\n输入课程前面编号获取课程地址");
        sb.append("\n\n回复“?”显示帮助菜单");
        return sb.toString();
    }

    @Override
    public String autoReplyBaiDuResource(String content) {
        // 课程
        BaiDuResource baiDuResource = baiDuResourceService.findByCode(content);
        if (baiDuResource == null) {
            // 关注就给回复消息
            return "无课程";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("课程信息如下：\n");
        sb.append("\n课程名称：" + baiDuResource.getTitle());
        sb.append("\n课程地址：" + baiDuResource.getBaiDuUrl());
        sb.append("\n\n回复“?”显示帮助菜单");
        return sb.toString();
    }
}

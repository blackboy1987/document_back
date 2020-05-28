package com.igomall.wechat.service.impl;

import com.igomall.common.Filter;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.active.ShareUrlDao;
import com.igomall.dao.active.ShareUrlRecordDao;
import com.igomall.entity.wechat.BaiDuTag;
import com.igomall.service.wechat.BaiDuTagService;
import com.igomall.wechat.dao.WechatMessageDao;
import com.igomall.entity.activity.ShareUrl;
import com.igomall.entity.activity.ShareUrlRecord;
import com.igomall.entity.wechat.BaiDuResource;
import com.igomall.wechat.entity.WeChatMessage;
import com.igomall.wechat.entity.WeChatUser;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.BaiDuResourceService;
import com.igomall.wechat.service.WechatAutoReplyMessageService;
import com.igomall.wechat.service.WechatMessageService;
import com.igomall.wechat.service.WechatUserService;
import com.igomall.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class WechatMessageServiceImpl extends BaseServiceImpl<WeChatMessage,Long> implements WechatMessageService {

    @Autowired
    private BaiDuResourceService baiDuResourceService;
    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private WechatMessageDao wechatMessageDao;

    @Autowired
    private BaiDuTagService baiDuTagService;

    @Resource
    private WechatAutoReplyMessageService wechatAutoReplyMessageService;


   public String getHelpMessage(){
       return wechatAutoReplyMessageService.findByMsgKey("?").getContent();
    }

    public WeChatMessage saveMessage(Map<String,String> map){
       // 保存一下用户。这样可以把以前已经关注的用户也可以保存进来
        CompletableFuture.runAsync(()->{
            wechatUserService.saveUser(map.get("FromUserName"));
        });


        WeChatMessage weChatMessage = JsonUtils.toObject(JsonUtils.toJson(map),WeChatMessage.class);
        return super.save(weChatMessage);
    }
    public WeChatMessage updateMessage(WeChatMessage weChatMessage,String receiveContent){
       try {
           if(weChatMessage!=null&&!weChatMessage.isNew()){
               weChatMessage.setReceiveContent(receiveContent);
               return super.update(weChatMessage);
           }
       }catch (Exception e){
           e.printStackTrace();
       }
       return weChatMessage;
    }

    @Override
    public String getCourseListInfo(String title){
       List<Filter> filters = new ArrayList<>();
       filters.add(new Filter("title", Filter.Operator.like,"%"+title+"%"));
        List<BaiDuResource> baiDuResources = baiDuResourceService.findList(null,filters,null);
        StringBuffer sb = new StringBuffer();
        if(!baiDuResources.isEmpty()){
            sb.append("已为您找到如下课程：\n");
            for (BaiDuResource baiDuResource:baiDuResources) {
                sb.append("\n"+baiDuResource.getCode()+"  "+baiDuResource.getTitle());
            }
            sb.append("\n\n输入课程前面编号获取课程地址");
        }else{
            sb.append("暂未找到相关课程。");
        }


        sb.append("\n\n回复“?”显示帮助菜单");
        return sb.toString();
    }

    @Override
    public String getAllBaiDuTags(String content) {
        List<BaiDuTag> baiDuTags = baiDuTagService.findAll();
        StringBuffer sb = new StringBuffer();
        if(!baiDuTags.isEmpty()){
            sb.append("已为您找到如下分类：\n");
            for (BaiDuTag baiDuTag:baiDuTags) {
                sb.append("\n"+baiDuTag.getCode()+"  "+baiDuTag.getName());
            }
            sb.append("\n\n输入分类前面编号获取分类下面的资源");
        }else{
            sb.append("暂无资料。");
        }
        return sb.toString();
    }

    @Override
    public Page<WeChatMessage> findPage(Pageable pageable, String content, String toUserName, String fromUserName, String msgType, Date beginDate, Date endDate) {
        return wechatMessageDao.findPage(pageable,content,toUserName,fromUserName,msgType,beginDate,endDate);
    }
}

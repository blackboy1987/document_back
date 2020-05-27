package com.igomall.wechat.service.impl;

import com.igomall.service.impl.BaseServiceImpl;
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
}

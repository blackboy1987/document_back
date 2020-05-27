package com.igomall.wechat.service;

import com.igomall.service.BaseService;
import com.igomall.wechat.entity.WeChatAutoReplyMessage;

public interface WechatAutoReplyMessageService extends BaseService<WeChatAutoReplyMessage,Long> {

    WeChatAutoReplyMessage findByMsgKey(String msgKey);

    String autoReply(String msgKey);

}

package com.igomall.wechat.service;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.wechat.entity.WeChatMessage;
import com.igomall.wechat.entity.WeChatUser;
import com.igomall.service.BaseService;

import java.util.Date;
import java.util.Map;

public interface WechatMessageService extends BaseService<WeChatMessage,Long> {

    String getHelpMessage();

    WeChatMessage saveMessage(Map<String,String> map);
    WeChatMessage updateMessage(WeChatMessage weChatMessage,String receiveContent);

    String getCourseListInfo(String title);

    String getAllBaiDuTags(String content);

    Page<WeChatMessage> findPage(Pageable pageable, String content, String toUserName, String fromUserName, String msgType, Date beginDate, Date endDate);
}

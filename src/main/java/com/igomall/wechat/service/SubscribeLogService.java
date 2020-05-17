
package com.igomall.wechat.service;

import com.igomall.wechat.entity.SubscribeLog;
import com.igomall.wechat.entity.WeChatUser;
import com.igomall.service.BaseService;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface SubscribeLogService extends BaseService<SubscribeLog, Long> {

    SubscribeLog save(WeChatUser weChatUser,String memo,Integer status);
}
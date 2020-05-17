
package com.igomall.wechat.service;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.wechat.entity.WeChatUser;
import com.igomall.service.BaseService;

import java.util.Date;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface WechatUserService extends BaseService<WeChatUser, Long> {

	WeChatUser findByOpenId(String openId);

	WeChatUser saveUser(String openId);

	Page<WeChatUser> findPage(Pageable pageable,String nickName, Integer status, Date beginDate, Date endDate);

	String updateInfo(String openId,String info,String type);

	WeChatUser remark(String openId,String remark);
}
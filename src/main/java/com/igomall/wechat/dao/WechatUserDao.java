
package com.igomall.wechat.dao;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.wechat.entity.WeChatUser;

import java.util.Date;

/**
 * Dao - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface WechatUserDao extends BaseDao<WeChatUser, Long> {

    Page<WeChatUser> findPage(Pageable pageable,String nickName, Integer status, Date beginDate, Date endDate);

}
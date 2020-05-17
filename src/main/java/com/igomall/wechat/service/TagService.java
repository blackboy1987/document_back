
package com.igomall.wechat.service;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.service.BaseService;
import com.igomall.wechat.entity.Tag;

import java.util.Date;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface TagService extends BaseService<Tag, Long> {

	Tag findByName(String name);

	Tag findByWeChatId(Long weChatId);

	Page<Tag> findPage(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate);

	Tag save1(Tag tag);

	Tag update1(Tag tag);
}
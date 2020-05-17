
package com.igomall.wechat.dao;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.wechat.entity.Tag;

import java.util.Date;

/**
 * Dao - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface TagDao extends BaseDao<Tag, Long> {

    Page<Tag> findPage(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate);

}
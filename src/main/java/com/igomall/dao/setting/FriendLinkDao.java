
package com.igomall.dao.setting;

import java.util.List;

import com.igomall.dao.BaseDao;
import com.igomall.entity.setting.FriendLink;

/**
 * Dao - 友情链接
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface FriendLinkDao extends BaseDao<FriendLink, Long> {

	/**
	 * 查找友情链接
	 * 
	 * @param type
	 *            类型
	 * @return 友情链接
	 */
	List<FriendLink> findList(FriendLink.Type type);

}
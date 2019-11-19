
package com.igomall.dao;

import com.igomall.entity.Permission;

/**
 * Dao - 权限
 * 
 * @author blackboy
 * @version 1.0
 */
public interface PermissionDao extends BaseDao<Permission, Long> {

	Boolean exists(String attributeName, String attributeValue, Long id);

}
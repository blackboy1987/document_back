
package com.igomall.service;

import com.igomall.entity.Permission;

/**
 * Service - 权限
 * 
 * @author blackboy
 * @version 1.0
 */
public interface PermissionService extends BaseService<Permission, Long> {

	Boolean exists(Permission permissions);
}
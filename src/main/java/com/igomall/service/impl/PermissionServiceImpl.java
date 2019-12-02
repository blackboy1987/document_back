
package com.igomall.service.impl;

import com.igomall.dao.PermissionDao;
import com.igomall.entity.Permission;
import com.igomall.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 权限
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long> implements PermissionService {

	@Autowired
	private PermissionDao permissionsDao;

	@Override
	@Transactional
	@CacheEvict(value = { "permission","authorization" }, allEntries = true)
	public Permission save(Permission permissions) {
		Assert.notNull(permissions,"");
		return super.save(permissions);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "permission","authorization" }, allEntries = true)
	public Permission update(Permission permission) {
		Assert.notNull(permission,"");
		return super.update(permission);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "permission","authorization" }, allEntries = true)
	public Permission update(Permission permission, String... ignoreProperties) {
		return super.update(permission, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "permission","authorization" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "permission","authorization" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "permission","authorization" }, allEntries = true)
	public void delete(Permission permission) {
		super.delete(permission);
	}


	@Override
	public Boolean exists(Permission permission){
		return permissionsDao.exists(permission.getName(),permission.getMenu(),permission.getId());
	}

}

package com.igomall.service.wechat.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.wechat.ProjectCategoryDao;
import com.igomall.dao.wechat.ProjectItemDao;
import com.igomall.entity.wechat.ProjectCategory;
import com.igomall.entity.wechat.ProjectItem;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.ProjectItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Service - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class ProjectItemServiceImpl extends BaseServiceImpl<ProjectItem, Long> implements ProjectItemService {

	@Autowired
	private ProjectItemDao projectItemDao;
	@Autowired
	private ProjectCategoryDao projectCategoryDao;

	@Transactional(readOnly = true)
	public List<ProjectItem> findList(ProjectCategory projectCategory, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders) {
		return projectItemDao.findList(projectCategory, isPublication, count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "projectItem", condition = "#useCache")
	public List<ProjectItem> findList(Long projectCategoryId, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		ProjectCategory projectCategory = projectCategoryDao.find(projectCategoryId);
		if (projectCategoryId != null && projectCategory == null) {
			return Collections.emptyList();
		}
		return projectItemDao.findList(projectCategory, isPublication, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public Page<ProjectItem> findPage(ProjectCategory projectCategory,String name, Boolean isPublication, Pageable pageable) {
		return projectItemDao.findPage(projectCategory,name, isPublication, pageable);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "projectItem", "projectCategory" }, allEntries = true)
	public ProjectItem save(ProjectItem projectItem) {
		return super.save(projectItem);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "projectItem", "projectCategory" }, allEntries = true)
	public ProjectItem update(ProjectItem projectItem) {
		return super.update(projectItem);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "projectItem", "projectCategory" }, allEntries = true)
	public ProjectItem update(ProjectItem projectItem, String... ignoreProperties) {
		return super.update(projectItem, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "projectItem", "projectCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "projectItem", "projectCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "projectItem", "projectCategory" }, allEntries = true)
	public void delete(ProjectItem projectItem) {
		super.delete(projectItem);
	}

}
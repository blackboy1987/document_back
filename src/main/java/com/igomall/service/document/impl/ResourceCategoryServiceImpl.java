
package com.igomall.service.document.impl;

import com.igomall.dao.document.ResourceCategoryDao;
import com.igomall.entity.document.ResourceCategory;
import com.igomall.service.document.ResourceCategoryService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * Service - 商品分类
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class ResourceCategoryServiceImpl extends BaseServiceImpl<ResourceCategory, Long> implements ResourceCategoryService {

	@Autowired
	private ResourceCategoryDao resourceCategoryDao;

	@Override
	public ResourceCategory findByName(String name) {
		return resourceCategoryDao.find("name",name);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "resourceCategory")
	public List<ResourceCategory> findRoots() {
		return resourceCategoryDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "resourceCategory")
	public List<ResourceCategory> findRoots(Integer count) {
		return resourceCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "resourceCategory", condition = "#useCache")
	public List<ResourceCategory> findRoots(Integer count, boolean useCache) {
		return resourceCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<ResourceCategory> findParents(ResourceCategory resourceCategory, boolean recursive, Integer count) {
		return resourceCategoryDao.findParents(resourceCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "resourceCategory", condition = "#useCache")
	public List<ResourceCategory> findParents(Long productCategoryId, boolean recursive, Integer count, boolean useCache) {
		ResourceCategory resourceCategory = resourceCategoryDao.find(productCategoryId);
		if (productCategoryId != null && resourceCategory == null) {
			return Collections.emptyList();
		}
		return resourceCategoryDao.findParents(resourceCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "resourceCategory")
	public List<ResourceCategory> findTree() {
		return resourceCategoryDao.findChildren(null, true, null);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "resourceCategory")
	public List<ResourceCategory> findChildren(ResourceCategory resourceCategory, boolean recursive, Integer count) {
		return resourceCategoryDao.findChildren(resourceCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "resourceCategory", condition = "#useCache")
	public List<ResourceCategory> findChildren(Long productCategoryId, boolean recursive, Integer count, boolean useCache) {
		ResourceCategory resourceCategory = resourceCategoryDao.find(productCategoryId);
		if (productCategoryId != null && resourceCategory == null) {
			return Collections.emptyList();
		}
		return resourceCategoryDao.findChildren(resourceCategory, recursive, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "resource", "resourceCategory" }, allEntries = true)
	public ResourceCategory save(ResourceCategory resourceCategory) {
		Assert.notNull(resourceCategory);

		setValue(resourceCategory);
		return super.save(resourceCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "resource", "resourceCategory" }, allEntries = true)
	public ResourceCategory update(ResourceCategory resourceCategory) {
		Assert.notNull(resourceCategory);

		setValue(resourceCategory);
		for (ResourceCategory children : resourceCategoryDao.findChildren(resourceCategory, true, null)) {
			setValue(children);
		}
		return super.update(resourceCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "resource", "resourceCategory" }, allEntries = true)
	public ResourceCategory update(ResourceCategory resourceCategory, String... ignoreProperties) {
		return super.update(resourceCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "resource", "resourceCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "resource", "resourceCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "resource", "resourceCategory" }, allEntries = true)
	public void delete(ResourceCategory resourceCategory) {
		super.delete(resourceCategory);
	}

	/**
	 * 设置值
	 * 
	 * @param resourceCategory
	 *            商品分类
	 */
	private void setValue(ResourceCategory resourceCategory) {
		if (resourceCategory == null) {
			return;
		}
		ResourceCategory parent = resourceCategory.getParent();
		if (parent != null) {
			resourceCategory.setTreePath(parent.getTreePath() + parent.getId() + ResourceCategory.TREE_PATH_SEPARATOR);
		} else {
			resourceCategory.setTreePath(ResourceCategory.TREE_PATH_SEPARATOR);
		}
		resourceCategory.setGrade(resourceCategory.getParentIds().length);
	}

}
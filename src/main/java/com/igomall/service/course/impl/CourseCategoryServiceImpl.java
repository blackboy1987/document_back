
package com.igomall.service.course.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.course.CourseCategoryDao;
import com.igomall.entity.course.CourseCategory;
import com.igomall.service.course.CourseCategoryService;
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
public class CourseCategoryServiceImpl extends BaseServiceImpl<CourseCategory, Long> implements CourseCategoryService {

	@Autowired
	private CourseCategoryDao courseCategoryDao;

	@Transactional(readOnly = true)
	public List<CourseCategory> findList(Integer count, List<Filter> filters, List<Order> orders) {
		return courseCategoryDao.findList(count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "courseCategoryRoot")
	public List<CourseCategory> findRoots() {
		return courseCategoryDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "courseCategoryRoot")
	public List<CourseCategory> findRoots(Integer count) {
		return courseCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "courseCategoryRoot", condition = "#useCache")
	public List<CourseCategory> findRoots(Integer count, boolean useCache) {
		return courseCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<CourseCategory> findParents(CourseCategory courseCategory, boolean recursive, Integer count) {
		return courseCategoryDao.findParents(courseCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "courseCategory", condition = "#useCache")
	public List<CourseCategory> findParents(Long courseCategoryId, boolean recursive, Integer count, boolean useCache) {
		CourseCategory courseCategory = courseCategoryDao.find(courseCategoryId);
		if (courseCategoryId != null && courseCategory == null) {
			return Collections.emptyList();
		}
		return courseCategoryDao.findParents(courseCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "courseCategoryTree")
	public List<CourseCategory> findTree() {
		return courseCategoryDao.findChildren(null, true, null);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "courseCategory")
	public List<CourseCategory> findChildren(CourseCategory courseCategory, boolean recursive, Integer count) {
		return courseCategoryDao.findChildren(courseCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "courseCategory", condition = "#useCache")
	public List<CourseCategory> findChildren(Long courseCategoryId, boolean recursive, Integer count, boolean useCache) {
		CourseCategory courseCategory = courseCategoryDao.find(courseCategoryId);
		if (courseCategoryId != null && courseCategory == null) {
			return Collections.emptyList();
		}
		return courseCategoryDao.findChildren(courseCategory, recursive, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "course", "courseCategory" }, allEntries = true)
	public CourseCategory save(CourseCategory courseCategory) {
		Assert.notNull(courseCategory,"");

		setValue(courseCategory);
		return super.save(courseCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "course", "courseCategory" }, allEntries = true)
	public CourseCategory update(CourseCategory courseCategory) {
		Assert.notNull(courseCategory,"");

		setValue(courseCategory);
		for (CourseCategory children : courseCategoryDao.findChildren(courseCategory, true, null)) {
			setValue(children);
		}
		return super.update(courseCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "course", "courseCategory" }, allEntries = true)
	public CourseCategory update(CourseCategory courseCategory, String... ignoreProperties) {
		return super.update(courseCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "course", "courseCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "course", "courseCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "course", "courseCategory" }, allEntries = true)
	public void delete(CourseCategory courseCategory) {
		super.delete(courseCategory);
	}

	/**
	 * 设置值
	 * 
	 * @param courseCategory
	 *            商品分类
	 */
	private void setValue(CourseCategory courseCategory) {
		if (courseCategory == null) {
			return;
		}
		CourseCategory parent = courseCategory.getParent();
		if (parent != null) {
			courseCategory.setTreePath(parent.getTreePath() + parent.getId() + CourseCategory.TREE_PATH_SEPARATOR);
		} else {
			courseCategory.setTreePath(CourseCategory.TREE_PATH_SEPARATOR);
		}
		courseCategory.setGrade(courseCategory.getParentIds().length);
	}

}

package com.igomall.service.course.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.course.CourseTagDao;
import com.igomall.entity.course.CourseTag;
import com.igomall.service.course.CourseTagService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service - 商品标签
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class CourseTagServiceImpl extends BaseServiceImpl<CourseTag, Long> implements CourseTagService {

	@Autowired
	private CourseTagDao courseTagDao;

	@Override
	public CourseTag findByName(String name) {
		return courseTagDao.find("name",name);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "courseTag", condition = "#useCache")
	public List<CourseTag> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		return courseTagDao.findList(null, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseTag", allEntries = true)
	public CourseTag save(CourseTag courseTag) {
		return super.save(courseTag);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseTag", allEntries = true)
	public CourseTag update(CourseTag courseTag) {
		return super.update(courseTag);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseTag", allEntries = true)
	public CourseTag update(CourseTag courseTag, String... ignoreProperties) {
		return super.update(courseTag, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseTag", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseTag", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseTag", allEntries = true)
	public void delete(CourseTag courseTag) {
		super.delete(courseTag);
	}

}
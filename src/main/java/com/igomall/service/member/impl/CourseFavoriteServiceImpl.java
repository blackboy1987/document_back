
package com.igomall.service.member.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.member.CourseFavoriteDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.member.CourseFavorite;
import com.igomall.entity.member.Member;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.member.CourseFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service - 商品收藏
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class CourseFavoriteServiceImpl extends BaseServiceImpl<CourseFavorite, Long> implements CourseFavoriteService {

	@Autowired
	private CourseFavoriteDao courseFavoriteDao;

	@Transactional(readOnly = true)
	public boolean exists(Member member, Course course) {
		return courseFavoriteDao.exists(member, course);
	}

	@Transactional(readOnly = true)
	public List<CourseFavorite> findList(Member member, Integer count, List<Filter> filters, List<Order> orders) {
		return courseFavoriteDao.findList(member, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public Page<CourseFavorite> findPage(Member member, Pageable pageable) {
		return courseFavoriteDao.findPage(member, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Member member) {
		return courseFavoriteDao.count(member);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "courseFavorite", condition = "#useCache")
	public List<CourseFavorite> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		return courseFavoriteDao.findList((Integer) null, count, filters, orders);
	}

	@Override
	@CacheEvict(value = "courseFavorite", allEntries = true)
	public CourseFavorite save(CourseFavorite courseFavorite) {
		return super.save(courseFavorite);
	}

	@Override
	@CacheEvict(value = "courseFavorite", allEntries = true)
	public CourseFavorite update(CourseFavorite courseFavorite) {
		return super.update(courseFavorite);
	}

	@Override
	@CacheEvict(value = "courseFavorite", allEntries = true)
	public CourseFavorite update(CourseFavorite courseFavorite, String... ignoreProperties) {
		return super.update(courseFavorite, ignoreProperties);
	}

	@Override
	@CacheEvict(value = "courseFavorite", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@CacheEvict(value = "courseFavorite", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@CacheEvict(value = "courseFavorite", allEntries = true)
	public void delete(CourseFavorite courseFavorite) {
		super.delete(courseFavorite);
	}

}

package com.igomall.service.course.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.course.CourseConsultationDao;
import com.igomall.dao.course.CourseDao;
import com.igomall.dao.member.MemberDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseConsultation;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.member.Member;
import com.igomall.service.course.CourseConsultationService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Service - 咨询
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class CourseConsultationServiceImpl extends BaseServiceImpl<CourseConsultation, Long> implements CourseConsultationService {

	@Autowired
	private CourseConsultationDao courseConsultationDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private CourseDao courseDao;

	@Override
	@Transactional(readOnly = true)
	public List<CourseConsultation> findList(Member member, Course course, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
		return courseConsultationDao.findList(member, course, isShow, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "courseConsultation", condition = "#useCache")
	public List<CourseConsultation> findList(Long memberId, Long courseId, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		Member member = memberDao.find(memberId);
		if (memberId != null && member == null) {
			return Collections.emptyList();
		}
		Course course = courseDao.find(courseId);
		if (courseId != null && course == null) {
			return Collections.emptyList();
		}
		return courseConsultationDao.findList(member, course, isShow, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CourseConsultation> findPage(Member member, Course course, Lesson lesson, Boolean isShow, Pageable pageable) {
		return courseConsultationDao.findPage(member, course, lesson, isShow, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Member member, Course course, Boolean isShow) {
		return courseConsultationDao.count(member, course, isShow);
	}

	@Override
	@CacheEvict(value = "courseConsultation", allEntries = true)
	public void reply(CourseConsultation courseConsultation, CourseConsultation replyConsultation) {
		if (courseConsultation == null || replyConsultation == null) {
			return;
		}
		courseConsultation.setIsShow(true);

		replyConsultation.setIsShow(true);
		replyConsultation.setCourse(courseConsultation.getCourse());
		replyConsultation.setForConsultation(courseConsultation);
		replyConsultation.setLesson(courseConsultation.getLesson());
		courseConsultationDao.persist(replyConsultation);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseConsultation", allEntries = true)
	public CourseConsultation save(CourseConsultation consultation) {
		return super.save(consultation);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseConsultation", allEntries = true)
	public CourseConsultation update(CourseConsultation consultation) {
		return super.update(consultation);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseConsultation", allEntries = true)
	public CourseConsultation update(CourseConsultation consultation, String... ignoreProperties) {
		return super.update(consultation, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseConsultation", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseConsultation", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseConsultation", allEntries = true)
	public void delete(CourseConsultation courseConsultation) {
		super.delete(courseConsultation);
	}

}
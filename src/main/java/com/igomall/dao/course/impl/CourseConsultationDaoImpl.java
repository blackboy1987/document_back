
package com.igomall.dao.course.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.igomall.dao.course.CourseConsultationDao;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseConsultation;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.member.Member;
import org.springframework.stereotype.Repository;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;

/**
 * Dao - 咨询
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class CourseConsultationDaoImpl extends BaseDaoImpl<CourseConsultation, Long> implements CourseConsultationDao {

	@Override
	public List<CourseConsultation> findList(Member member, Course course, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseConsultation> criteriaQuery = criteriaBuilder.createQuery(CourseConsultation.class);
		Root<CourseConsultation> root = criteriaQuery.from(CourseConsultation.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forConsultation")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (course != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), course));
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	@Override
	public Page<CourseConsultation> findPage(Member member, Course course, Lesson lesson, Boolean isShow, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseConsultation> criteriaQuery = criteriaBuilder.createQuery(CourseConsultation.class);
		Root<CourseConsultation> root = criteriaQuery.from(CourseConsultation.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forConsultation")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (course != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("course"), course));
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		if (lesson != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("lesson"), lesson));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Long count(Member member, Course course, Boolean isShow) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseConsultation> criteriaQuery = criteriaBuilder.createQuery(CourseConsultation.class);
		Root<CourseConsultation> root = criteriaQuery.from(CourseConsultation.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forConsultation")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (course != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("course"), course));
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

}
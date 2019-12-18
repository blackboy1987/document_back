
package com.igomall.dao.course.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.igomall.dao.course.CourseCommentDao;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseComment;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.member.Member;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;


/**
 * Dao - 评论
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class CourseCommentDaoImpl extends BaseDaoImpl<CourseComment, Long> implements CourseCommentDao {

	@Override
	public List<CourseComment> findList(Member member, Course course, CourseComment.Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseComment> criteriaQuery = criteriaBuilder.createQuery(CourseComment.class);
		Root<CourseComment> root = criteriaQuery.from(CourseComment.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forReview")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (course != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("course"), course));
		}
		if (type != null) {
			switch (type) {
			case positive:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get("score"), 4));
				break;
			case moderate:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Number>get("score"), 3));
				break;
			case negative:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get("score"), 2));
				break;
				default:
					break;
			}
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	@Override
	public Page<CourseComment> findPage(Member member, Course course, Lesson lesson, CourseComment.Type type, Boolean isShow, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseComment> criteriaQuery = criteriaBuilder.createQuery(CourseComment.class);
		Root<CourseComment> root = criteriaQuery.from(CourseComment.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forReview")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (course != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("course"), course));
		}
		if (type != null) {
			switch (type) {
			case positive:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get("score"), 4));
				break;
			case moderate:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Number>get("score"), 3));
				break;
			case negative:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get("score"), 2));
				break;
				default:
					break;
			}
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
	public Long count(Member member, Course course, CourseComment.Type type, Boolean isShow) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseComment> criteriaQuery = criteriaBuilder.createQuery(CourseComment.class);
		Root<CourseComment> root = criteriaQuery.from(CourseComment.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forReview")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (course != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("course"), course));
		}
		if (type != null) {
			switch (type) {
			case positive:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get("score"), 4));
				break;
			case moderate:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Number>get("score"), 3));
				break;
			case negative:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get("score"), 2));
				break;
				default:
					break;
			}
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	@Override
	public long calculateTotalScore(Course course) {
		Assert.notNull(course,"");

		String jpql = "select sum(courseComment.score) from CourseComment courseComment where courseComment.course = :course and courseComment.isShow = :isShow and courseComment.forReview is null";
		Long totalScore = entityManager.createQuery(jpql, Long.class).setParameter("course", course).setParameter("isShow", true).getSingleResult();
		return totalScore != null ? totalScore : 0L;
	}

	@Override
	public long calculateScoreCount(Course course) {
		Assert.notNull(course,"");

		String jpql = "select count(*) from CourseComment courseComment where courseComment.course = :course and courseComment.isShow = :isShow and courseComment.forReview is null";
		return entityManager.createQuery(jpql, Long.class).setParameter("course", course).setParameter("isShow", true).getSingleResult();
	}

}
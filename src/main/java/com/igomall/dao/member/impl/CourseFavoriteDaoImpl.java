
package com.igomall.dao.member.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.member.CourseFavoriteDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.member.CourseFavorite;
import com.igomall.entity.member.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Dao - 商品收藏
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Repository
public class CourseFavoriteDaoImpl extends BaseDaoImpl<CourseFavorite, Long> implements CourseFavoriteDao {

	public boolean exists(Member member, Course course) {
		String jpql = "select count(1) from CourseFavorite courseFavorite where courseFavorite.member = :member and courseFavorite.course = :course";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("member", member).setParameter("course", course).getSingleResult();
		return count > 0;
	}

	public List<CourseFavorite> findList(Member member, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseFavorite> criteriaQuery = criteriaBuilder.createQuery(CourseFavorite.class);
		Root<CourseFavorite> root = criteriaQuery.from(CourseFavorite.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public Page<CourseFavorite> findPage(Member member, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseFavorite> criteriaQuery = criteriaBuilder.createQuery(CourseFavorite.class);
		Root<CourseFavorite> root = criteriaQuery.from(CourseFavorite.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseFavorite> criteriaQuery = criteriaBuilder.createQuery(CourseFavorite.class);
		Root<CourseFavorite> root = criteriaQuery.from(CourseFavorite.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery);
	}

}
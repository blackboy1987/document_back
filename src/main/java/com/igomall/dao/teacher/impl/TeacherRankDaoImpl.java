
package com.igomall.dao.teacher.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.teacher.TeacherRankDao;
import com.igomall.entity.teacher.TeacherRank;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Dao - 会员等级
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Repository
public class TeacherRankDaoImpl extends BaseDaoImpl<TeacherRank, Long> implements TeacherRankDao {

	public TeacherRank findDefault() {
		try {
			String jpql = "select teacherRank from TeacherRank teacherRank where teacherRank.isDefault = true";
			return entityManager.createQuery(jpql, TeacherRank.class).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void clearDefault() {
		String jpql = "update TeacherRank teacherRank set teacherRank.isDefault = false where teacherRank.isDefault = true";
		entityManager.createQuery(jpql).executeUpdate();
	}

	public void clearDefault(TeacherRank exclude) {
		Assert.notNull(exclude,"");

		String jpql = "update TeacherRank teacherRank set teacherRank.isDefault = false where teacherRank.isDefault = true and teacherRank != :exclude";
		entityManager.createQuery(jpql).setParameter("exclude", exclude).executeUpdate();
	}


	@Override
	public Page<TeacherRank> findPage(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TeacherRank> criteriaQuery = criteriaBuilder.createQuery(TeacherRank.class);
		Root<TeacherRank> root = criteriaQuery.from(TeacherRank.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (StringUtils.isNotEmpty(name)) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("name"), "%"+name+"%"));
		}
		if (isEnabled!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"), isEnabled));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
}
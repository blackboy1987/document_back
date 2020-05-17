
package com.igomall.dao.wechat.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.wechat.ProjectItemDao;
import com.igomall.entity.wechat.ProjectCategory;
import com.igomall.entity.wechat.ProjectItem;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

/**
 * Dao - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class ProjectItemDaoImpl extends BaseDaoImpl<ProjectItem, Long> implements ProjectItemDao {

	@Override
	public List<ProjectItem> findList(ProjectCategory bookCategory, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProjectItem> criteriaQuery = criteriaBuilder.createQuery(ProjectItem.class);
		Root<ProjectItem> root = criteriaQuery.from(ProjectItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (bookCategory != null) {
			Subquery<ProjectCategory> subquery = criteriaQuery.subquery(ProjectCategory.class);
			Root<ProjectCategory> subqueryRoot = subquery.from(ProjectCategory.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, bookCategory), criteriaBuilder.like(subqueryRoot.<String>get("treePath"), "%" + ProjectCategory.TREE_PATH_SEPARATOR + bookCategory.getId() + ProjectCategory.TREE_PATH_SEPARATOR + "%")));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("bookCategory")).value(subquery));
		}
		if (isPublication != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), isPublication));
		}
		criteriaQuery.where(restrictions);
		if (orders == null || orders.isEmpty()) {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("order")), criteriaBuilder.desc(root.get("createdDate")));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public List<ProjectItem> findList(ProjectCategory bookCategory, Boolean isPublication, Date beginDate, Date endDate, Integer first, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProjectItem> criteriaQuery = criteriaBuilder.createQuery(ProjectItem.class);
		Root<ProjectItem> root = criteriaQuery.from(ProjectItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (bookCategory != null) {
			Subquery<ProjectCategory> subquery = criteriaQuery.subquery(ProjectCategory.class);
			Root<ProjectCategory> subqueryRoot = subquery.from(ProjectCategory.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, bookCategory), criteriaBuilder.like(subqueryRoot.<String>get("treePath"), "%" + ProjectCategory.TREE_PATH_SEPARATOR + bookCategory.getId() + ProjectCategory.TREE_PATH_SEPARATOR + "%")));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("bookCategory")).value(subquery));
		}
		if (isPublication != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), isPublication));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, first, count);
	}

	public Page<ProjectItem> findPage(ProjectCategory bookCategory,String name, Boolean isPublication, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProjectItem> criteriaQuery = criteriaBuilder.createQuery(ProjectItem.class);
		Root<ProjectItem> root = criteriaQuery.from(ProjectItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (bookCategory != null) {
			Subquery<ProjectCategory> subquery = criteriaQuery.subquery(ProjectCategory.class);
			Root<ProjectCategory> subqueryRoot = subquery.from(ProjectCategory.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, bookCategory), criteriaBuilder.like(subqueryRoot.<String>get("treePath"), "%" + ProjectCategory.TREE_PATH_SEPARATOR + bookCategory.getId() + ProjectCategory.TREE_PATH_SEPARATOR + "%")));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("bookCategory")).value(subquery));
		}
		if (isPublication != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), isPublication));
		}
		if (StringUtils.isNotEmpty(name)) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("name"), "%"+name+"%"));
		}
		criteriaQuery.where(restrictions);
		if (pageable == null || ((StringUtils.isEmpty(pageable.getOrderProperty()) || pageable.getOrderDirection() == null) && CollectionUtils.isEmpty(pageable.getOrders()))) {
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")), criteriaBuilder.desc(root.get("createdDate")));
		}
		return super.findPage(criteriaQuery, pageable);
	}

}
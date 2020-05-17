
package com.igomall.dao.wechat.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.wechat.BookItemDao;
import com.igomall.entity.wechat.BookCategory;
import com.igomall.entity.wechat.BookItem;
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
public class BookItemDaoImpl extends BaseDaoImpl<BookItem, Long> implements BookItemDao {

	@Override
	public List<BookItem> findList(BookCategory bookCategory, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BookItem> criteriaQuery = criteriaBuilder.createQuery(BookItem.class);
		Root<BookItem> root = criteriaQuery.from(BookItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (bookCategory != null) {
			Subquery<BookCategory> subquery = criteriaQuery.subquery(BookCategory.class);
			Root<BookCategory> subqueryRoot = subquery.from(BookCategory.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, bookCategory), criteriaBuilder.like(subqueryRoot.<String>get("treePath"), "%" + BookCategory.TREE_PATH_SEPARATOR + bookCategory.getId() + BookCategory.TREE_PATH_SEPARATOR + "%")));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("bookCategory")).value(subquery));
		}
		if (isPublication != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), isPublication));
		}
		criteriaQuery.where(restrictions);
		if (orders == null || orders.isEmpty()) {
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")), criteriaBuilder.desc(root.get("createdDate")));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public List<BookItem> findList(BookCategory bookCategory, Boolean isPublication, Date beginDate, Date endDate, Integer first, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BookItem> criteriaQuery = criteriaBuilder.createQuery(BookItem.class);
		Root<BookItem> root = criteriaQuery.from(BookItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (bookCategory != null) {
			Subquery<BookCategory> subquery = criteriaQuery.subquery(BookCategory.class);
			Root<BookCategory> subqueryRoot = subquery.from(BookCategory.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, bookCategory), criteriaBuilder.like(subqueryRoot.<String>get("treePath"), "%" + BookCategory.TREE_PATH_SEPARATOR + bookCategory.getId() + BookCategory.TREE_PATH_SEPARATOR + "%")));
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

	public Page<BookItem> findPage(BookCategory bookCategory,String name, Boolean isPublication, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BookItem> criteriaQuery = criteriaBuilder.createQuery(BookItem.class);
		Root<BookItem> root = criteriaQuery.from(BookItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (bookCategory != null) {
			Subquery<BookCategory> subquery = criteriaQuery.subquery(BookCategory.class);
			Root<BookCategory> subqueryRoot = subquery.from(BookCategory.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, bookCategory), criteriaBuilder.like(subqueryRoot.<String>get("treePath"), "%" + BookCategory.TREE_PATH_SEPARATOR + bookCategory.getId() + BookCategory.TREE_PATH_SEPARATOR + "%")));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("bookCategory")).value(subquery));
		}
		if (StringUtils.isNotEmpty(name)) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("name"), "%"+name+"%"));
		}
		if (isPublication != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), isPublication));
		}
		criteriaQuery.where(restrictions);
		if (pageable == null || ((StringUtils.isEmpty(pageable.getOrderProperty()) || pageable.getOrderDirection() == null) && CollectionUtils.isEmpty(pageable.getOrders()))) {
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("bookCategory")),criteriaBuilder.asc(root.get("order")), criteriaBuilder.desc(root.get("createdDate")));
		}
		return super.findPage(criteriaQuery, pageable);
	}
}
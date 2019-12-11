
package com.igomall.dao.course.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.course.CourseCategoryDao;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.entity.course.CourseCategory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Dao - 商品分类
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class CourseCategoryDaoImpl extends BaseDaoImpl<CourseCategory, Long> implements CourseCategoryDao {

	public List<CourseCategory> findList(Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseCategory> criteriaQuery = criteriaBuilder.createQuery(CourseCategory.class);
		Root<CourseCategory> root = criteriaQuery.from(CourseCategory.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery);
	}

	public List<CourseCategory> findRoots(Integer count) {
		String jpql = "select courseCategory from CourseCategory courseCategory where courseCategory.parent is null order by courseCategory.order asc";
		TypedQuery<CourseCategory> query = entityManager.createQuery(jpql, CourseCategory.class);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<CourseCategory> findParents(CourseCategory courseCategory, boolean recursive, Integer count) {
		if (courseCategory == null || courseCategory.getParent() == null) {
			return Collections.emptyList();
		}
		TypedQuery<CourseCategory> query;
		if (recursive) {
			String jpql = "select courseCategory from CourseCategory courseCategory where courseCategory.id in (:ids) order by courseCategory.grade asc";
			query = entityManager.createQuery(jpql, CourseCategory.class).setParameter("ids", Arrays.asList(courseCategory.getParentIds()));
		} else {
			String jpql = "select courseCategory from CourseCategory courseCategory where courseCategory = :courseCategory";
			query = entityManager.createQuery(jpql, CourseCategory.class).setParameter("courseCategory", courseCategory.getParent());
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<CourseCategory> findChildren(CourseCategory courseCategory, boolean recursive, Integer count) {
		TypedQuery<CourseCategory> query;
		if (recursive) {
			if (courseCategory != null) {
				String jpql = "select courseCategory from CourseCategory courseCategory where courseCategory.treePath like :treePath order by courseCategory.grade asc, courseCategory.order asc";
				query = entityManager.createQuery(jpql, CourseCategory.class).setParameter("treePath", "%" + CourseCategory.TREE_PATH_SEPARATOR + courseCategory.getId() + CourseCategory.TREE_PATH_SEPARATOR + "%");
			} else {
				String jpql = "select courseCategory from CourseCategory courseCategory order by courseCategory.grade asc, courseCategory.order asc";
				query = entityManager.createQuery(jpql, CourseCategory.class);
			}
			if (count != null) {
				query.setMaxResults(count);
			}
			List<CourseCategory> result = query.getResultList();
			sort(result);
			return result;
		} else {
			String jpql = "select courseCategory from CourseCategory courseCategory where courseCategory.parent = :parent order by courseCategory.order asc";
			query = entityManager.createQuery(jpql, CourseCategory.class).setParameter("parent", courseCategory);
			if (count != null) {
				query.setMaxResults(count);
			}
			return query.getResultList();
		}
	}

	/**
	 * 排序商品分类
	 * 
	 * @param productCategories
	 *            商品分类
	 */
	private void sort(List<CourseCategory> productCategories) {
		if (CollectionUtils.isEmpty(productCategories)) {
			return;
		}
		final Map<Long, Integer> orderMap = new HashMap<>();
		for (CourseCategory courseCategory : productCategories) {
			orderMap.put(courseCategory.getId(), courseCategory.getOrder());
		}
		Collections.sort(productCategories, new Comparator<CourseCategory>() {
			@Override
			public int compare(CourseCategory courseCategory1, CourseCategory courseCategory2) {
				Long[] ids1 = (Long[]) ArrayUtils.add(courseCategory1.getParentIds(), courseCategory1.getId());
				Long[] ids2 = (Long[]) ArrayUtils.add(courseCategory2.getParentIds(), courseCategory2.getId());
				Iterator<Long> iterator1 = Arrays.asList(ids1).iterator();
				Iterator<Long> iterator2 = Arrays.asList(ids2).iterator();
				CompareToBuilder compareToBuilder = new CompareToBuilder();
				while (iterator1.hasNext() && iterator2.hasNext()) {
					Long id1 = iterator1.next();
					Long id2 = iterator2.next();
					Integer order1 = orderMap.get(id1);
					Integer order2 = orderMap.get(id2);
					compareToBuilder.append(order1, order2).append(id1, id2);
					if (!iterator1.hasNext() || !iterator2.hasNext()) {
						compareToBuilder.append(courseCategory1.getGrade(), courseCategory2.getGrade());
					}
				}
				return compareToBuilder.toComparison();
			}
		});
	}

}

package com.igomall.dao.document.impl;

import com.igomall.dao.document.ResourceCategoryDao;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.entity.document.ResourceCategory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.*;

/**
 * Dao - 商品分类
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class ResourceCategoryDaoImpl extends BaseDaoImpl<ResourceCategory, Long> implements ResourceCategoryDao {


	public List<ResourceCategory> findRoots(Integer count) {
		String jpql = "select resourceCategory from ResourceCategory resourceCategory where resourceCategory.parent is null order by resourceCategory.order asc";
		TypedQuery<ResourceCategory> query = entityManager.createQuery(jpql, ResourceCategory.class);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ResourceCategory> findParents(ResourceCategory resourceCategory, boolean recursive, Integer count) {
		if (resourceCategory == null || resourceCategory.getParent() == null) {
			return Collections.emptyList();
		}
		TypedQuery<ResourceCategory> query;
		if (recursive) {
			String jpql = "select resourceCategory from ResourceCategory resourceCategory where resourceCategory.id in (:ids) order by resourceCategory.grade asc";
			query = entityManager.createQuery(jpql, ResourceCategory.class).setParameter("ids", Arrays.asList(resourceCategory.getParentIds()));
		} else {
			String jpql = "select resourceCategory from ResourceCategory resourceCategory where resourceCategory = :resourceCategory";
			query = entityManager.createQuery(jpql, ResourceCategory.class).setParameter("resourceCategory", resourceCategory.getParent());
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ResourceCategory> findChildren(ResourceCategory resourceCategory, boolean recursive, Integer count) {
		TypedQuery<ResourceCategory> query;
		if (recursive) {
			if (resourceCategory != null) {
				String jpql = "select resourceCategory from ResourceCategory resourceCategory where resourceCategory.treePath like :treePath order by resourceCategory.grade asc, resourceCategory.order asc";
				query = entityManager.createQuery(jpql, ResourceCategory.class).setParameter("treePath", "%" + ResourceCategory.TREE_PATH_SEPARATOR + resourceCategory.getId() + ResourceCategory.TREE_PATH_SEPARATOR + "%");
			} else {
				String jpql = "select resourceCategory from ResourceCategory resourceCategory order by resourceCategory.grade asc, resourceCategory.order asc";
				query = entityManager.createQuery(jpql, ResourceCategory.class);
			}
			if (count != null) {
				query.setMaxResults(count);
			}
			List<ResourceCategory> result = query.getResultList();
			sort(result);
			return result;
		} else {
			String jpql = "select resourceCategory from ResourceCategory resourceCategory where resourceCategory.parent = :parent order by resourceCategory.order asc";
			query = entityManager.createQuery(jpql, ResourceCategory.class).setParameter("parent", resourceCategory);
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
	private void sort(List<ResourceCategory> productCategories) {
		if (CollectionUtils.isEmpty(productCategories)) {
			return;
		}
		final Map<Long, Integer> orderMap = new HashMap<>();
		for (ResourceCategory resourceCategory : productCategories) {
			orderMap.put(resourceCategory.getId(), resourceCategory.getOrder());
		}
		Collections.sort(productCategories, new Comparator<ResourceCategory>() {
			@Override
			public int compare(ResourceCategory productCategory1, ResourceCategory productCategory2) {
				Long[] ids1 = (Long[]) ArrayUtils.add(productCategory1.getParentIds(), productCategory1.getId());
				Long[] ids2 = (Long[]) ArrayUtils.add(productCategory2.getParentIds(), productCategory2.getId());
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
						compareToBuilder.append(productCategory1.getGrade(), productCategory2.getGrade());
					}
				}
				return compareToBuilder.toComparison();
			}
		});
	}

}

package com.igomall.dao.wechat.impl;

import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.wechat.ToolCategoryDao;
import com.igomall.entity.wechat.ToolCategory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.*;

/**
 * Dao - 文章分类
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class ToolCategoryDaoImpl extends BaseDaoImpl<ToolCategory, Long> implements ToolCategoryDao {

	public List<ToolCategory> findRoots(Integer count) {
		String jpql = "select toolCategory from ToolCategory toolCategory where toolCategory.parent is null order by toolCategory.order asc";
		TypedQuery<ToolCategory> query = entityManager.createQuery(jpql, ToolCategory.class);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ToolCategory> findParents(ToolCategory toolCategory, boolean recursive, Integer count) {
		if (toolCategory == null || toolCategory.getParent() == null) {
			return Collections.emptyList();
		}
		TypedQuery<ToolCategory> query;
		if (recursive) {
			String jpql = "select toolCategory from ToolCategory toolCategory where toolCategory.id in (:ids) order by toolCategory.grade asc";
			query = entityManager.createQuery(jpql, ToolCategory.class).setParameter("ids", Arrays.asList(toolCategory.getParentIds()));
		} else {
			String jpql = "select toolCategory from ToolCategory toolCategory where toolCategory = :toolCategory";
			query = entityManager.createQuery(jpql, ToolCategory.class).setParameter("toolCategory", toolCategory.getParent());
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ToolCategory> findChildren(ToolCategory toolCategory, boolean recursive, Integer count) {
		TypedQuery<ToolCategory> query;
		if (recursive) {
			if (toolCategory != null) {
				String jpql = "select toolCategory from ToolCategory toolCategory where toolCategory.treePath like :treePath order by toolCategory.grade asc, toolCategory.order asc";
				query = entityManager.createQuery(jpql, ToolCategory.class).setParameter("treePath", "%" + ToolCategory.TREE_PATH_SEPARATOR + toolCategory.getId() + ToolCategory.TREE_PATH_SEPARATOR + "%");
			} else {
				String jpql = "select toolCategory from ToolCategory toolCategory order by toolCategory.grade asc, toolCategory.order asc";
				query = entityManager.createQuery(jpql, ToolCategory.class);
			}
			if (count != null) {
				query.setMaxResults(count);
			}
			List<ToolCategory> result = query.getResultList();
			sort(result);
			return result;
		} else {
			String jpql = "select toolCategory from ToolCategory toolCategory where toolCategory.parent = :parent order by toolCategory.order asc";
			query = entityManager.createQuery(jpql, ToolCategory.class).setParameter("parent", toolCategory);
			if (count != null) {
				query.setMaxResults(count);
			}
			return query.getResultList();
		}
	}

	/**
	 * 排序文章分类
	 * 
	 * @param toolCategories
	 *            文章分类
	 */
	private void sort(List<ToolCategory> toolCategories) {
		if (CollectionUtils.isEmpty(toolCategories)) {
			return;
		}
		final Map<Long, Integer> orderMap = new HashMap<>();
		for (ToolCategory toolCategory : toolCategories) {
			orderMap.put(toolCategory.getId(), toolCategory.getOrder());
		}
		Collections.sort(toolCategories, new Comparator<ToolCategory>() {
			@Override
			public int compare(ToolCategory toolCategory1, ToolCategory toolCategory2) {
				Long[] ids1 = (Long[]) ArrayUtils.add(toolCategory1.getParentIds(), toolCategory1.getId());
				Long[] ids2 = (Long[]) ArrayUtils.add(toolCategory2.getParentIds(), toolCategory2.getId());
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
						compareToBuilder.append(toolCategory1.getGrade(), toolCategory2.getGrade());
					}
				}
				return compareToBuilder.toComparison();
			}
		});
	}

}
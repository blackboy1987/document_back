
package com.igomall.dao.wechat.impl;

import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.wechat.ProjectCategoryDao;
import com.igomall.entity.wechat.ProjectCategory;
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
public class ProjectCategoryDaoImpl extends BaseDaoImpl<ProjectCategory, Long> implements ProjectCategoryDao {

	public List<ProjectCategory> findRoots(Integer count) {
		String jpql = "select projectCategory from ProjectCategory projectCategory where projectCategory.parent is null order by projectCategory.order asc";
		TypedQuery<ProjectCategory> query = entityManager.createQuery(jpql, ProjectCategory.class);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ProjectCategory> findParents(ProjectCategory projectCategory, boolean recursive, Integer count) {
		if (projectCategory == null || projectCategory.getParent() == null) {
			return Collections.emptyList();
		}
		TypedQuery<ProjectCategory> query;
		if (recursive) {
			String jpql = "select projectCategory from ProjectCategory projectCategory where projectCategory.id in (:ids) order by projectCategory.grade asc";
			query = entityManager.createQuery(jpql, ProjectCategory.class).setParameter("ids", Arrays.asList(projectCategory.getParentIds()));
		} else {
			String jpql = "select projectCategory from ProjectCategory projectCategory where projectCategory = :projectCategory";
			query = entityManager.createQuery(jpql, ProjectCategory.class).setParameter("projectCategory", projectCategory.getParent());
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ProjectCategory> findChildren(ProjectCategory projectCategory, boolean recursive, Integer count) {
		TypedQuery<ProjectCategory> query;
		if (recursive) {
			if (projectCategory != null) {
				String jpql = "select projectCategory from ProjectCategory projectCategory where projectCategory.treePath like :treePath order by projectCategory.grade asc, projectCategory.order asc";
				query = entityManager.createQuery(jpql, ProjectCategory.class).setParameter("treePath", "%" + ProjectCategory.TREE_PATH_SEPARATOR + projectCategory.getId() + ProjectCategory.TREE_PATH_SEPARATOR + "%");
			} else {
				String jpql = "select projectCategory from ProjectCategory projectCategory order by projectCategory.grade asc, projectCategory.order asc";
				query = entityManager.createQuery(jpql, ProjectCategory.class);
			}
			if (count != null) {
				query.setMaxResults(count);
			}
			List<ProjectCategory> result = query.getResultList();
			sort(result);
			return result;
		} else {
			String jpql = "select projectCategory from ProjectCategory projectCategory where projectCategory.parent = :parent order by projectCategory.order asc";
			query = entityManager.createQuery(jpql, ProjectCategory.class).setParameter("parent", projectCategory);
			if (count != null) {
				query.setMaxResults(count);
			}
			return query.getResultList();
		}
	}

	/**
	 * 排序文章分类
	 * 
	 * @param projectCategories
	 *            文章分类
	 */
	private void sort(List<ProjectCategory> projectCategories) {
		if (CollectionUtils.isEmpty(projectCategories)) {
			return;
		}
		final Map<Long, Integer> orderMap = new HashMap<>();
		for (ProjectCategory projectCategory : projectCategories) {
			orderMap.put(projectCategory.getId(), projectCategory.getOrder());
		}
		Collections.sort(projectCategories, new Comparator<ProjectCategory>() {
			@Override
			public int compare(ProjectCategory projectCategory1, ProjectCategory projectCategory2) {
				Long[] ids1 = (Long[]) ArrayUtils.add(projectCategory1.getParentIds(), projectCategory1.getId());
				Long[] ids2 = (Long[]) ArrayUtils.add(projectCategory2.getParentIds(), projectCategory2.getId());
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
						compareToBuilder.append(projectCategory1.getGrade(), projectCategory2.getGrade());
					}
				}
				return compareToBuilder.toComparison();
			}
		});
	}

}
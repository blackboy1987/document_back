
package com.igomall.dao.course.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.course.FolderDao;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Folder;
import com.igomall.entity.course.Lesson;
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
 * Dao - 地区
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class FolderDaoImpl extends BaseDaoImpl<Folder, Long> implements FolderDao {

	public List<Folder> findRoots(Integer count) {
		String jpql = "select folder from Folder folder where folder.parent is null order by folder.order asc";
		TypedQuery<Folder> query = entityManager.createQuery(jpql, Folder.class);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<Folder> findParents(Folder folder, boolean recursive, Integer count) {
		if (folder == null || folder.getParent() == null) {
			return Collections.emptyList();
		}
		TypedQuery<Folder> query;
		if (recursive) {
			String jpql = "select folder from Folder folder where folder.id in (:ids) order by folder.grade asc";
			query = entityManager.createQuery(jpql, Folder.class).setParameter("ids", Arrays.asList(folder.getParentIds()));
		} else {
			String jpql = "select folder from Folder folder where folder = :folder";
			query = entityManager.createQuery(jpql, Folder.class).setParameter("folder", folder.getParent());
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<Folder> findChildren(Folder folder, boolean recursive, Integer count) {
		TypedQuery<Folder> query;
		if (recursive) {
			if (folder != null) {
				String jpql = "select folder from Folder folder where folder.treePath like :treePath order by folder.grade asc, folder.order asc";
				query = entityManager.createQuery(jpql, Folder.class).setParameter("treePath", "%" + Folder.TREE_PATH_SEPARATOR + folder.getId() + Folder.TREE_PATH_SEPARATOR + "%");
			} else {
				String jpql = "select folder from Folder folder order by folder.grade asc, folder.order asc";
				query = entityManager.createQuery(jpql, Folder.class);
			}
			if (count != null) {
				query.setMaxResults(count);
			}
			List<Folder> result = query.getResultList();
			sort(result);
			return result;
		} else {
			String jpql = "select folder from Folder folder where folder.parent = :parent order by folder.order asc";
			query = entityManager.createQuery(jpql, Folder.class).setParameter("parent", folder);
			if (count != null) {
				query.setMaxResults(count);
			}
			return query.getResultList();
		}
	}

	/**
	 * 排序地区
	 * 
	 * @param folders
	 *            地区
	 */
	private void sort(List<Folder> folders) {
		if (CollectionUtils.isEmpty(folders)) {
			return;
		}
		final Map<Long, Integer> orderMap = new HashMap<>();
		for (Folder folder : folders) {
			orderMap.put(folder.getId(), folder.getOrder());
		}
		Collections.sort(folders, new Comparator<Folder>() {
			@Override
			public int compare(Folder folder1, Folder folder2) {
				Long[] ids1 = (Long[]) ArrayUtils.add(folder1.getParentIds(), folder1.getId());
				Long[] ids2 = (Long[]) ArrayUtils.add(folder2.getParentIds(), folder2.getId());
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
						compareToBuilder.append(folder1.getGrade(), folder2.getGrade());
					}
				}
				return compareToBuilder.toComparison();
			}
		});
	}

	@Override
	public List<Folder> findList(Course course, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Folder> criteriaQuery = criteriaBuilder.createQuery(Folder.class);
		Root<Folder> root = criteriaQuery.from(Folder.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (course==null) {
			return Collections.emptyList();
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("course"), course));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery,null,count,filters,orders);
	}
}
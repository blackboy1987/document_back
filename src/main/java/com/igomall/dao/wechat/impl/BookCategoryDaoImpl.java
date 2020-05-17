
package com.igomall.dao.wechat.impl;

import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.wechat.BookCategoryDao;
import com.igomall.entity.wechat.BookCategory;
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
public class BookCategoryDaoImpl extends BaseDaoImpl<BookCategory, Long> implements BookCategoryDao {

	public List<BookCategory> findRoots(Integer count) {
		String jpql = "select bookCategory from BookCategory bookCategory where bookCategory.parent is null order by bookCategory.order asc";
		TypedQuery<BookCategory> query = entityManager.createQuery(jpql, BookCategory.class);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<BookCategory> findParents(BookCategory bookCategory, boolean recursive, Integer count) {
		if (bookCategory == null || bookCategory.getParent() == null) {
			return Collections.emptyList();
		}
		TypedQuery<BookCategory> query;
		if (recursive) {
			String jpql = "select bookCategory from BookCategory bookCategory where bookCategory.id in (:ids) order by bookCategory.grade asc";
			query = entityManager.createQuery(jpql, BookCategory.class).setParameter("ids", Arrays.asList(bookCategory.getParentIds()));
		} else {
			String jpql = "select bookCategory from BookCategory bookCategory where bookCategory = :bookCategory";
			query = entityManager.createQuery(jpql, BookCategory.class).setParameter("bookCategory", bookCategory.getParent());
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<BookCategory> findChildren(BookCategory bookCategory, boolean recursive, Integer count) {
		TypedQuery<BookCategory> query;
		if (recursive) {
			if (bookCategory != null) {
				String jpql = "select bookCategory from BookCategory bookCategory where bookCategory.treePath like :treePath order by bookCategory.grade asc, bookCategory.order asc";
				query = entityManager.createQuery(jpql, BookCategory.class).setParameter("treePath", "%" + BookCategory.TREE_PATH_SEPARATOR + bookCategory.getId() + BookCategory.TREE_PATH_SEPARATOR + "%");
			} else {
				String jpql = "select bookCategory from BookCategory bookCategory order by bookCategory.grade asc, bookCategory.order asc";
				query = entityManager.createQuery(jpql, BookCategory.class);
			}
			if (count != null) {
				query.setMaxResults(count);
			}
			List<BookCategory> result = query.getResultList();
			sort(result);
			return result;
		} else {
			String jpql = "select bookCategory from BookCategory bookCategory where bookCategory.parent = :parent order by bookCategory.order asc";
			query = entityManager.createQuery(jpql, BookCategory.class).setParameter("parent", bookCategory);
			if (count != null) {
				query.setMaxResults(count);
			}
			return query.getResultList();
		}
	}

	/**
	 * 排序文章分类
	 * 
	 * @param articleCategories
	 *            文章分类
	 */
	private void sort(List<BookCategory> articleCategories) {
		if (CollectionUtils.isEmpty(articleCategories)) {
			return;
		}
		final Map<Long, Integer> orderMap = new HashMap<>();
		for (BookCategory bookCategory : articleCategories) {
			orderMap.put(bookCategory.getId(), bookCategory.getOrder());
		}
		Collections.sort(articleCategories, new Comparator<BookCategory>() {
			@Override
			public int compare(BookCategory bookCategory1, BookCategory bookCategory2) {
				Long[] ids1 = (Long[]) ArrayUtils.add(bookCategory1.getParentIds(), bookCategory1.getId());
				Long[] ids2 = (Long[]) ArrayUtils.add(bookCategory2.getParentIds(), bookCategory2.getId());
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
						compareToBuilder.append(bookCategory1.getGrade(), bookCategory2.getGrade());
					}
				}
				return compareToBuilder.toComparison();
			}
		});
	}

}
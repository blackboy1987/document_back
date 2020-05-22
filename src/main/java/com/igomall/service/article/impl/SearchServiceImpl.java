
package com.igomall.service.article.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.article.Article;
import com.igomall.service.article.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Service - 搜索
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class SearchServiceImpl implements SearchService {

	@PersistenceContext
	private EntityManager entityManager;

	public void index(Class<?> type) {
		index(type, true);
	}

	public void index(Class<?> type, boolean purgeAll) {
		Assert.notNull(type,"");

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		fullTextEntityManager.createIndexer(type).purgeAllOnStart(purgeAll).start();
	}

	public Page<Article> search(String keyword, Pageable pageable) {
		if (StringUtils.isEmpty(keyword)) {
			return new Page<>();
		}

		if (pageable == null) {
			pageable = new Pageable();
		}

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Article.class).get();

		Query titleQuery = queryBuilder.keyword().fuzzy().onField("title").matching(keyword).createQuery();
		Query contentQuery = queryBuilder.keyword().onField("content").matching(keyword).createQuery();
		Query isPublicationQuery = queryBuilder.phrase().onField("isPublication").sentence("true").createQuery();
		Query query = queryBuilder.bool().must(queryBuilder.bool().should(titleQuery).should(contentQuery).createQuery()).must(isPublicationQuery).createQuery();

		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, Article.class);
		fullTextQuery.setSort(new Sort(new SortField("isTop", SortField.Type.STRING, true), new SortField(null, SortField.Type.SCORE), new SortField("createdDate", SortField.Type.LONG, true)));
		fullTextQuery.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
		fullTextQuery.setMaxResults(pageable.getPageSize());
		return new Page<>(fullTextQuery.getResultList(), fullTextQuery.getResultSize(), pageable);
	}

}
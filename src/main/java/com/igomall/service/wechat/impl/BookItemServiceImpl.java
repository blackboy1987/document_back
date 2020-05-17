
package com.igomall.service.wechat.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.wechat.BookCategoryDao;
import com.igomall.dao.wechat.BookItemDao;
import com.igomall.entity.wechat.BookCategory;
import com.igomall.entity.wechat.BookItem;
import com.igomall.entity.wechat.ProjectItem;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.BookItemService;
import io.jsonwebtoken.lang.Assert;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Service - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class BookItemServiceImpl extends BaseServiceImpl<BookItem, Long> implements BookItemService {

	@Autowired
	private BookItemDao bookItemDao;
	@Autowired
	private BookCategoryDao bookCategoryDao;
	@Autowired
	private CacheManager cacheManager;

	@Transactional(readOnly = true)
	public List<BookItem> findList(BookCategory bookCategory, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders) {
		return bookItemDao.findList(bookCategory, isPublication, count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "bookItem", condition = "#useCache")
	public List<BookItem> findList(Long bookCategoryId, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		BookCategory bookCategory = bookCategoryDao.find(bookCategoryId);
		if (bookCategoryId != null && bookCategory == null) {
			return Collections.emptyList();
		}
		return bookItemDao.findList(bookCategory, isPublication, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public Page<BookItem> findPage(BookCategory bookCategory,String name,  Boolean isPublication, Pageable pageable) {
		return bookItemDao.findPage(bookCategory, name,isPublication, pageable);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "bookItem", "bookCategory" }, allEntries = true)
	public BookItem save(BookItem bookItem) {
		return super.save(bookItem);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "bookItem", "bookCategory" }, allEntries = true)
	public BookItem update(BookItem bookItem) {
		return super.update(bookItem);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "bookItem", "bookCategory" }, allEntries = true)
	public BookItem update(BookItem bookItem, String... ignoreProperties) {
		return super.update(bookItem, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "bookItem", "bookCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "bookItem", "bookCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "bookItem", "bookCategory" }, allEntries = true)
	public void delete(BookItem bookItem) {
		super.delete(bookItem);
	}

	@Override
	public long viewHits(Long id) {
		Assert.notNull(id,"");
		Ehcache cache = cacheManager.getEhcache(ProjectItem.HITS_CACHE_NAME);
		cache.acquireWriteLockOnKey(id);
		try {
			Element element = cache.get(id);
			Long hits;
			if (element != null) {
				hits = (Long) element.getObjectValue() + 1;
			} else {
				BookItem bookItem = bookItemDao.find(id);
				if (bookItem == null) {
					return 0L;
				}
				hits = (bookItem.getDownloadHits()==null?0:bookItem.getDownloadHits()) + 1;
			}
			cache.put(new Element(id, hits));
			return hits;
		} finally {
			cache.releaseWriteLockOnKey(id);
		}
	}
}
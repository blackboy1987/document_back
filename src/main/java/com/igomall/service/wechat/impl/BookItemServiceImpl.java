
package com.igomall.service.wechat.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.wechat.BookCategoryDao;
import com.igomall.dao.wechat.BookItemDao;
import com.igomall.entity.wechat.BookCategory;
import com.igomall.entity.wechat.BookItem;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.BookItemService;
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

}
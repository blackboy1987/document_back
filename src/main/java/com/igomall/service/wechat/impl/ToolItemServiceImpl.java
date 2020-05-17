
package com.igomall.service.wechat.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.wechat.ToolCategoryDao;
import com.igomall.dao.wechat.ToolItemDao;
import com.igomall.entity.wechat.ToolCategory;
import com.igomall.entity.wechat.ToolItem;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.ToolItemService;
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
public class ToolItemServiceImpl extends BaseServiceImpl<ToolItem, Long> implements ToolItemService {

	@Autowired
	private ToolItemDao toolItemDao;
	@Autowired
	private ToolCategoryDao toolCategoryDao;

	@Transactional(readOnly = true)
	public List<ToolItem> findList(ToolCategory toolCategory, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders) {
		return toolItemDao.findList(toolCategory, isPublication, count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "toolItem", condition = "#useCache")
	public List<ToolItem> findList(Long toolCategoryId, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		ToolCategory toolCategory = toolCategoryDao.find(toolCategoryId);
		if (toolCategoryId != null && toolCategory == null) {
			return Collections.emptyList();
		}
		return toolItemDao.findList(toolCategory, isPublication, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public Page<ToolItem> findPage(ToolCategory toolCategory,String name, Boolean isPublication, Pageable pageable) {
		return toolItemDao.findPage(toolCategory,name, isPublication, pageable);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "toolItem", "toolCategory" }, allEntries = true)
	public ToolItem save(ToolItem toolItem) {
		return super.save(toolItem);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "toolItem", "toolCategory" }, allEntries = true)
	public ToolItem update(ToolItem toolItem) {
		return super.update(toolItem);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "toolItem", "toolCategory" }, allEntries = true)
	public ToolItem update(ToolItem toolItem, String... ignoreProperties) {
		return super.update(toolItem, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "toolItem", "toolCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "toolItem", "toolCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "toolItem", "toolCategory" }, allEntries = true)
	public void delete(ToolItem toolItem) {
		super.delete(toolItem);
	}

}
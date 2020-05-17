
package com.igomall.service.wechat.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.wechat.BaiDuResourceDao;
import com.igomall.entity.wechat.BaiDuResource;
import com.igomall.entity.wechat.BaiDuTag;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.BaiDuResourceService;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Service - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class BaiDuResourceServiceImpl extends BaseServiceImpl<BaiDuResource, Long> implements BaiDuResourceService {

	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private BaiDuResourceDao baiDuResourceDao;


	@Transactional(readOnly = true)
	public BaiDuResource findByCode(String code){
		return baiDuResourceDao.find("code",code);
	}

	@Transactional(readOnly = true)
	public boolean codeExist(String code){
		return baiDuResourceDao.exists("code",code);
	}

	@Transactional(readOnly = true)
	public List<BaiDuResource> findList(BaiDuTag baiDuTag, Integer count, List<Filter> filters, List<Order> orders) {
		return baiDuResourceDao.findList(baiDuTag, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public Page<BaiDuResource> findPage(BaiDuTag baiDuTag, Pageable pageable) {
		return baiDuResourceDao.findPage(baiDuTag, pageable);
	}

	public long viewHits(Long id) {
		Assert.notNull(id,"");

		Ehcache cache = cacheManager.getEhcache(BaiDuResource.HITS_CACHE_NAME);
		cache.acquireWriteLockOnKey(id);
		try {
			Element element = cache.get(id);
			Long hits;
			if (element != null) {
				hits = (Long) element.getObjectValue() + 1;
			} else {
				BaiDuResource article = baiDuResourceDao.find(id);
				if (article == null) {
					return 0L;
				}
				hits = article.getHits() + 1;
			}
			cache.put(new Element(id, hits));
			return hits;
		} finally {
			cache.releaseWriteLockOnKey(id);
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "baiDuTag", "baiDuResource" }, allEntries = true)
	public BaiDuResource save(BaiDuResource article) {
		return super.save(article);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "baiDuTag", "baiDuResource" }, allEntries = true)
	public BaiDuResource update(BaiDuResource article) {
		return super.update(article);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "baiDuTag", "baiDuResource" }, allEntries = true)
	public BaiDuResource update(BaiDuResource article, String... ignoreProperties) {
		return super.update(article, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "baiDuTag", "baiDuResource" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "baiDuTag", "baiDuResource" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "baiDuTag", "baiDuResource" }, allEntries = true)
	public void delete(BaiDuResource article) {
		super.delete(article);
	}

}
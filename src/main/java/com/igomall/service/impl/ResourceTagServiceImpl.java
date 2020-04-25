
package com.igomall.service.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.ResourceTagDao;
import com.igomall.entity.ResourceTag;
import com.igomall.service.ResourceTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service - 商品标签
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class ResourceTagServiceImpl extends BaseServiceImpl<ResourceTag, Long> implements ResourceTagService {

	@Autowired
	private ResourceTagDao resourceTagDao;

	@Override
	public ResourceTag findByName(String name) {
		return resourceTagDao.find("name",name);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "resourceTag", condition = "#useCache")
	public List<ResourceTag> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		return resourceTagDao.findList(null, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = "resourceTag", allEntries = true)
	public ResourceTag save(ResourceTag resourceTag) {
		return super.save(resourceTag);
	}

	@Override
	@Transactional
	@CacheEvict(value = "resourceTag", allEntries = true)
	public ResourceTag update(ResourceTag resourceTag) {
		return super.update(resourceTag);
	}

	@Override
	@Transactional
	@CacheEvict(value = "resourceTag", allEntries = true)
	public ResourceTag update(ResourceTag resourceTag, String... ignoreProperties) {
		return super.update(resourceTag, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "resourceTag", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "resourceTag", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "resourceTag", allEntries = true)
	public void delete(ResourceTag resourceTag) {
		super.delete(resourceTag);
	}

}
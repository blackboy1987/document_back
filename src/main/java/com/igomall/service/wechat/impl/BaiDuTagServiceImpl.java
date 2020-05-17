
package com.igomall.service.wechat.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.wechat.BaiDuTagDao;
import com.igomall.entity.wechat.BaiDuTag;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.BaiDuTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service - 文章标签
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class BaiDuTagServiceImpl extends BaseServiceImpl<BaiDuTag, Long> implements BaiDuTagService {

	@Autowired
	private BaiDuTagDao baiDuTagDao;

	@Transactional(readOnly = true)
	public boolean codeExist(String code){
		return baiDuTagDao.exists("code",code);
	}

	@Transactional(readOnly = true)
	public BaiDuTag findByCode(String code){
		return baiDuTagDao.find("code",code);
	}

	@Transactional(readOnly = true)
	public BaiDuTag findByName(String name){
		return baiDuTagDao.find("name",name);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "baiDuTag", condition = "#useCache")
	public List<BaiDuTag> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		return baiDuTagDao.findList(null, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = "baiDuTag", allEntries = true)
	public BaiDuTag save(BaiDuTag baiDuTag) {
		return super.save(baiDuTag);
	}

	@Override
	@Transactional
	@CacheEvict(value = "baiDuTag", allEntries = true)
	public BaiDuTag update(BaiDuTag baiDuTag) {
		return super.update(baiDuTag);
	}

	@Override
	@Transactional
	@CacheEvict(value = "baiDuTag", allEntries = true)
	public BaiDuTag update(BaiDuTag baiDuTag, String... ignoreProperties) {
		return super.update(baiDuTag, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "baiDuTag", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "baiDuTag", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "baiDuTag", allEntries = true)
	public void delete(BaiDuTag baiDuTag) {
		super.delete(baiDuTag);
	}

}
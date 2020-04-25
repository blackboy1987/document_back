
package com.igomall.service;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.entity.ResourceTag;

import java.util.List;

/**
 * Service - 商品标签
 * 
 * @author blackboy
 * @version 1.0
 */
public interface ResourceTagService extends BaseService<ResourceTag, Long> {

	ResourceTag findByName(String name);

	/**
	 * 查找商品标签
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 商品标签
	 */
	List<ResourceTag> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

}
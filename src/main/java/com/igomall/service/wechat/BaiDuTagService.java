
package com.igomall.service.wechat;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.entity.wechat.BaiDuTag;
import com.igomall.service.BaseService;

import java.util.List;

/**
 * Service - 文章标签
 * 
 * @author blackboy
 * @version 1.0
 */
public interface BaiDuTagService extends BaseService<BaiDuTag, Long> {

	boolean codeExist(String code);

	BaiDuTag findByCode(String code);

	BaiDuTag findByName(String name);

	/**
	 * 查找文章标签
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 文章标签
	 */
	List<BaiDuTag> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

}

package com.igomall.service.course;

import java.util.List;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.entity.course.CourseTag;
import com.igomall.service.BaseService;

/**
 * Service - 商品标签
 * 
 * @author blackboy
 * @version 1.0
 */
public interface CourseTagService extends BaseService<CourseTag, Long> {

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
	List<CourseTag> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

}
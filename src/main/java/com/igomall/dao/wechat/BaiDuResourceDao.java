
package com.igomall.dao.wechat;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.wechat.BaiDuResource;
import com.igomall.entity.wechat.BaiDuTag;

import java.util.List;

/**
 * Dao - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
public interface BaiDuResourceDao extends BaseDao<BaiDuResource, Long> {

	/**
	 * 查找文章
	 *
	 * @param baiDuTag
	 *            文章标签
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 文章
	 */
	List<BaiDuResource> findList(BaiDuTag baiDuTag, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找文章分页
	 *
	 * @param baiDuTag
	 *            文章标签
	 * @param pageable
	 *            分页信息
	 * @return 文章分页
	 */
	Page<BaiDuResource> findPage(BaiDuTag baiDuTag, Pageable pageable);

}

package com.igomall.service.wechat;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.wechat.BookCategory;
import com.igomall.entity.wechat.BookItem;
import com.igomall.service.BaseService;

import java.util.List;

/**
 * Service - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
public interface BookItemService extends BaseService<BookItem, Long> {

	/**
	 * 查找文章
	 * 
	 * @param bookCategory
	 *            文章分类
	 * @param isPublication
	 *            是否发布
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 文章
	 */
	List<BookItem> findList(BookCategory bookCategory, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找文章
	 * 
	 * @param bookCategoryId
	 *            文章分类ID
	 * @param isPublication
	 *            是否发布
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 文章
	 */
	List<BookItem> findList(Long bookCategoryId, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

	/**
	 * 查找文章分页
	 * 
	 * @param bookCategory
	 *            文章分类
	 * @param isPublication
	 *            是否发布
	 * @param pageable
	 *            分页信息
	 * @return 文章分页
	 */
	Page<BookItem> findPage(BookCategory bookCategory,String name, Boolean isPublication, Pageable pageable);


}
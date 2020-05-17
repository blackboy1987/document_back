
package com.igomall.dao.wechat;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.wechat.BookCategory;
import com.igomall.entity.wechat.BookItem;

import java.util.Date;
import java.util.List;

/**
 * Dao - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
public interface BookItemDao extends BaseDao<BookItem, Long> {

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
	 * @param bookCategory
	 *            文章分类
	 * @param isPublication
	 *            是否发布
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @return 文章
	 */
	List<BookItem> findList(BookCategory bookCategory, Boolean isPublication, Date beginDate, Date endDate, Integer first, Integer count);

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
	Page<BookItem> findPage(BookCategory bookCategory,String name,  Boolean isPublication, Pageable pageable);

}
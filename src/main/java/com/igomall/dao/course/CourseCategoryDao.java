
package com.igomall.dao.course;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.BaseDao;
import com.igomall.entity.course.CourseCategory;

import java.util.List;

/**
 * Dao - 商品分类
 * 
 * @author blackboy
 * @version 1.0
 */
public interface CourseCategoryDao extends BaseDao<CourseCategory, Long> {

	/**
	 * 查找商品分类
	 *
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 商品分类
	 */
	List<CourseCategory> findList(Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<CourseCategory> findRoots(Integer count);

	/**
	 * 查找上级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级商品分类
	 */
	List<CourseCategory> findParents(CourseCategory productCategory, boolean recursive, Integer count);

	/**
	 * 查找下级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级商品分类
	 */
	List<CourseCategory> findChildren(CourseCategory productCategory, boolean recursive, Integer count);

}
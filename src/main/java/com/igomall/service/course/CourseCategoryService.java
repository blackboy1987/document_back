
package com.igomall.service.course;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.entity.course.CourseCategory;
import com.igomall.service.BaseService;

import java.util.List;

/**
 * Service - 商品分类
 * 
 * @author blackboy
 * @version 1.0
 */
public interface CourseCategoryService extends BaseService<CourseCategory, Long> {

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
	 * @return 顶级商品分类
	 */
	List<CourseCategory> findRoots();

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<CourseCategory> findRoots(Integer count);

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 顶级商品分类
	 */
	List<CourseCategory> findRoots(Integer count, boolean useCache);

	/**
	 * 查找上级商品分类
	 * 
	 * @param courseCategory
	 *            商品分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级商品分类
	 */
	List<CourseCategory> findParents(CourseCategory courseCategory, boolean recursive, Integer count);

	/**
	 * 查找上级商品分类
	 * 
	 * @param courseCategoryId
	 *            商品分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 上级商品分类
	 */
	List<CourseCategory> findParents(Long courseCategoryId, boolean recursive, Integer count, boolean useCache);

	/**
	 * 查找商品分类树
	 * 
	 * @return 商品分类树
	 */
	List<CourseCategory> findTree();

	/**
	 * 查找下级商品分类
	 * 
	 * @param courseCategory
	 *            商品分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级商品分类
	 */
	List<CourseCategory> findChildren(CourseCategory courseCategory, boolean recursive, Integer count);

	/**
	 * 查找下级商品分类
	 * 
	 * @param courseCategoryId
	 *            商品分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 下级商品分类
	 */
	List<CourseCategory> findChildren(Long courseCategoryId, boolean recursive, Integer count, boolean useCache);

}
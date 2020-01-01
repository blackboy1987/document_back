
package com.igomall.service.member;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.course.Course;
import com.igomall.entity.member.CourseFavorite;
import com.igomall.entity.member.Member;
import com.igomall.service.BaseService;

import java.util.List;

/**
 * Service - 商品收藏
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface CourseFavoriteService extends BaseService<CourseFavorite, Long> {

	/**
	 * 判断商品收藏是否存在
	 * 
	 * @param member
	 *            会员
	 * @param course
	 *            商品
	 * @return 商品收藏是否存在
	 */
	boolean exists(Member member, Course course);

	/**
	 * 查找商品收藏
	 * 
	 * @param member
	 *            会员
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 商品收藏
	 */
	List<CourseFavorite> findList(Member member, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找商品收藏
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 商品收藏
	 */
	List<CourseFavorite> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

	/**
	 * 查找商品收藏分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 商品收藏分页
	 */
	Page<CourseFavorite> findPage(Member member, Pageable pageable);

	/**
	 * 查找商品收藏数量
	 * 
	 * @param member
	 *            会员
	 * @return 商品收藏数量
	 */
	Long count(Member member);

}
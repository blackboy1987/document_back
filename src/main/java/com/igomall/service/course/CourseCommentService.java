
package com.igomall.service.course;

import java.util.List;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseComment;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.member.Member;
import com.igomall.service.BaseService;

/**
 * Service - 评论
 * 
 * @author blackboy
 * @version 1.0
 */
public interface CourseCommentService extends BaseService<CourseComment, Long> {

	/**
	 * 查找评论
	 * 
	 * @param member
	 *            会员
	 * @param course
	 *            商品
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 评论
	 */
	List<CourseComment> findList(Member member, Course course, CourseComment.Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找评论
	 * 
	 * @param memberId
	 *            会员ID
	 * @param courseId
	 *            商品ID
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 评论
	 */
	List<CourseComment> findList(Long memberId, Long courseId, CourseComment.Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

	/**
	 * 查找评论分页
	 * 
	 * @param member
	 *            会员
	 * @param course
	 *            商品
	 * @param lesson
	 *            店铺
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @param pageable
	 *            分页信息
	 * @return 评论分页
	 */
	Page<CourseComment> findPage(Member member, Course course, Lesson lesson, CourseComment.Type type, Boolean isShow, Pageable pageable);

	/**
	 * 查找评论数量
	 * 
	 * @param member
	 *            会员
	 * @param course
	 *            商品
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @return 评论数量
	 */
	Long count(Member member, Course course, CourseComment.Type type, Boolean isShow);

	/**
	 * 评论回复
	 * 
	 * @param review
	 *            评论
	 * @param replyCourseComment
	 *            回复评论
	 */
	void reply(CourseComment review, CourseComment replyCourseComment);

	/**
	 * 判断是否拥有评论权限
	 * 
	 * @param member
	 *            会员
	 * @param course
	 *            商品
	 * @return 是否拥有评论权限
	 */
	boolean hasPermission(Member member, Course course);
}
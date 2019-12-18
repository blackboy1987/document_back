
package com.igomall.dao.course;

import java.util.List;
import java.util.Map;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseComment;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.member.Member;
import com.igomall.entity.teacher.Teacher;

/**
 * Dao - 评论
 * 
 * @author blackboy
 * @version 1.0
 */
public interface CourseCommentDao extends BaseDao<CourseComment, Long> {

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
	 * 计算商品总评分
	 * 
	 * @param course
	 *            商品
	 * @return 商品总评分，仅计算显示评论
	 */
	long calculateTotalScore(Course course);

	/**
	 * 计算商品评分次数
	 * 
	 * @param course
	 *            商品
	 * @return 商品评分次数，仅计算显示评论
	 */
	long calculateScoreCount(Course course);

	List<Map<String,Object>> findListBySQL(Course course);
}
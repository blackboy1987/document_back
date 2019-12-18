
package com.igomall.dao.course;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseConsultation;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.member.Member;

import java.util.List;

/**
 * Dao - 咨询
 * 
 * @author blackboy
 * @version 1.0
 */
public interface CourseConsultationDao extends BaseDao<CourseConsultation, Long> {

	/**
	 * 查找咨询
	 * 
	 * @param member
	 *            会员
	 * @param course
	 *            商品
	 * @param isShow
	 *            是否显示
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 咨询，不包含咨询回复
	 */
	List<CourseConsultation> findList(Member member, Course course, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找咨询分页
	 * 
	 * @param member
	 *            会员
	 * @param course
	 *            商品
	 * @param lesson
	 *            店铺
	 * @param isShow
	 *            是否显示
	 * @param pageable
	 *            分页信息
	 * @return 咨询分页，不包含咨询回复
	 */
	Page<CourseConsultation> findPage(Member member, Course course, Lesson lesson, Boolean isShow, Pageable pageable);

	/**
	 * 查找咨询数量
	 * 
	 * @param member
	 *            会员
	 * @param course
	 *            商品
	 * @param isShow
	 *            是否显示
	 * @return 咨询数量，不包含咨询回复
	 */
	Long count(Member member, Course course, Boolean isShow);

}
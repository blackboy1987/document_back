package com.igomall.dao.course;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseCategory;

import java.util.Date;
import java.util.List;

public interface CourseDao extends BaseDao<Course,Long> {

    Page<Course> findPage(Pageable pageable, String title, String description, Date beginDate, Date endDate);

    Page<Course> findPage(CourseCategory courseCategory, Boolean isVip, Pageable pageable);

    List<Course> findList(CourseCategory courseCategory, Integer first, Integer count, List<Filter> filters, List<Order> orders);
}

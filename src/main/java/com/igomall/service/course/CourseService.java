package com.igomall.service.course;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseCategory;
import com.igomall.service.BaseService;

import java.util.Date;
import java.util.List;

public interface CourseService extends BaseService<Course,Long> {

    /**
     * 判断编号是否存在
     *
     * @param sn
     *            编号(忽略大小写)
     * @return 编号是否存在
     */
    boolean snExists(String sn);

    /**
     * 根据编号查找商品
     *
     * @param sn
     *            编号(忽略大小写)
     * @return 商品，若不存在则返回null
     */
    Course findBySn(String sn);

    Page<Course> findPage(Pageable pageable, String title, String description, Date beginDate, Date endDate);

    Page<Course> findPage(CourseCategory courseCategory, Boolean isVip,Pageable pageable);

    List<Course> findList(CourseCategory courseCategory, Integer first, Integer count, List<Filter> filters, List<Order> orders);
}

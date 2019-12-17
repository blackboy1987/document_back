package com.igomall.service.course;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.course.Course;
import com.igomall.service.BaseService;

import java.util.Date;

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
}

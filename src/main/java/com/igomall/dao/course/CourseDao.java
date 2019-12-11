package com.igomall.dao.course;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.course.Course;

import java.util.Date;

public interface CourseDao extends BaseDao<Course,Long> {

    Page<Course> findPage(Pageable pageable, String title, String description, Date beginDate, Date endDate);
}

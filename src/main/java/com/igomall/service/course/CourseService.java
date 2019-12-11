package com.igomall.service.course;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.course.Course;
import com.igomall.service.BaseService;

import java.util.Date;

public interface CourseService extends BaseService<Course,Long> {

    Page<Course> findPage(Pageable pageable, String title, String description, Date beginDate, Date endDate);
}

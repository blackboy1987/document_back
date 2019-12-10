package com.igomall.service;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Course;

import java.util.Date;

public interface CourseService extends BaseService<Course,Long> {

    Page<Course> findPage(Pageable pageable, String title, String description, Date beginDate, Date endDate);
}

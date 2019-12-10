package com.igomall.dao;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Course;

import java.util.Date;
import java.util.List;

public interface CourseDao extends BaseDao<Course,Long> {

    Page<Course> findPage(Pageable pageable, String title, String description, Date beginDate, Date endDate);
}

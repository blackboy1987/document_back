package com.igomall.dao.course;

import com.igomall.dao.BaseDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Part;

import java.util.List;

public interface PartDao extends BaseDao<Part,Long> {
    List<Part> findList(Course course);
}

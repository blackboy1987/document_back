package com.igomall.dao;

import com.igomall.entity.Course;
import com.igomall.entity.Part;

import java.util.List;

public interface PartDao extends BaseDao<Part,Long> {
    List<Part> findList(Course course);
}

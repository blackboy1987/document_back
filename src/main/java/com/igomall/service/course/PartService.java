package com.igomall.service.course;

import com.igomall.entity.course.Course;
import com.igomall.entity.course.Part;
import com.igomall.service.BaseService;

import java.util.List;

public interface PartService extends BaseService<Part,Long> {
    List<Part> findList(Course course);
}

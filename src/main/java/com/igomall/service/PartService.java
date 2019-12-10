package com.igomall.service;

import com.igomall.entity.Course;
import com.igomall.entity.Part;

import java.util.List;

public interface PartService extends BaseService<Part,Long> {
    List<Part> findList(Course course);
}

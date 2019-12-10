package com.igomall.service;

import com.igomall.entity.Chapter;
import com.igomall.entity.Course;
import com.igomall.entity.Part;

import java.util.List;

public interface ChapterService extends BaseService<Chapter,Long> {

    List<Chapter> findList(Course course, Part part);
}

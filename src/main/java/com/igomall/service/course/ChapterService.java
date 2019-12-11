package com.igomall.service.course;

import com.igomall.entity.course.Chapter;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Part;
import com.igomall.service.BaseService;

import java.util.List;

public interface ChapterService extends BaseService<Chapter,Long> {

    List<Chapter> findList(Course course, Part part);
}

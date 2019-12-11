package com.igomall.service.course;

import com.igomall.entity.course.Chapter;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.course.Part;
import com.igomall.service.BaseService;

import java.util.List;

public interface LessonService extends BaseService<Lesson,Long> {

    List<Lesson> findList(Course course, Part part, Chapter chapter);
}

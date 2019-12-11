package com.igomall.dao.course;

import com.igomall.dao.BaseDao;
import com.igomall.entity.course.Chapter;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.course.Part;

import java.util.List;

public interface LessonDao extends BaseDao<Lesson,Long> {

    List<Lesson> findList(Course course, Part part, Chapter chapter);
}

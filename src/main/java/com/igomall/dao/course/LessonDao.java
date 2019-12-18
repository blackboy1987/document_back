package com.igomall.dao.course;

import com.igomall.dao.BaseDao;
import com.igomall.entity.course.Chapter;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.course.Part;

import java.util.List;
import java.util.Map;

public interface LessonDao extends BaseDao<Lesson,Long> {

    List<Lesson> findList(Course course, Part part, Chapter chapter);

    List<Map<String,Object>> findListByCourseSQL(Course course);
}

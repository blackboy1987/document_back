package com.igomall.dao;

import com.igomall.entity.Chapter;
import com.igomall.entity.Course;
import com.igomall.entity.Lesson;
import com.igomall.entity.Part;

import java.util.List;

public interface LessonDao extends BaseDao<Lesson,Long> {

    List<Lesson> findList(Course course, Part part, Chapter chapter);
}

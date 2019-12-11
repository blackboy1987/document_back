package com.igomall.dao.course;

import com.igomall.dao.BaseDao;
import com.igomall.entity.course.Chapter;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Part;

import java.util.List;

public interface ChapterDao extends BaseDao<Chapter,Long> {

    List<Chapter> findList(Course course, Part part);
}

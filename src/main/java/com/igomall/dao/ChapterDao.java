package com.igomall.dao;

import com.igomall.entity.Chapter;
import com.igomall.entity.Course;
import com.igomall.entity.Part;

import java.util.List;

public interface ChapterDao extends BaseDao<Chapter,Long> {

    List<Chapter> findList(Course course, Part part);
}

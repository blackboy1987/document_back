package com.igomall.service.impl;

import com.igomall.dao.LessonDao;
import com.igomall.entity.Chapter;
import com.igomall.entity.Course;
import com.igomall.entity.Lesson;
import com.igomall.entity.Part;
import com.igomall.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl extends BaseServiceImpl<Lesson,Long> implements LessonService {

    @Autowired
    private LessonDao lessonDao;

    @Override
    public List<Lesson> findList(Course course, Part part, Chapter chapter) {
        return lessonDao.findList(course,part,chapter);
    }
}

package com.igomall.service.course.impl;

import com.igomall.dao.course.LessonDao;
import com.igomall.entity.course.Chapter;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.course.Part;
import com.igomall.service.course.LessonService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LessonServiceImpl extends BaseServiceImpl<Lesson,Long> implements LessonService {

    @Autowired
    private LessonDao lessonDao;

    @Transactional(readOnly = true)
    public boolean snExists(String sn) {
        return lessonDao.exists("sn", sn, true);
    }

    @Transactional(readOnly = true)
    public Lesson findBySn(String sn) {
        return lessonDao.find("sn", sn, true);
    }

    @Override
    public List<Lesson> findList(Course course, Part part, Chapter chapter) {
        return lessonDao.findList(course,part,chapter);
    }
}

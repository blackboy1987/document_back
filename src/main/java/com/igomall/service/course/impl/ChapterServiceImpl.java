package com.igomall.service.course.impl;

import com.igomall.dao.course.ChapterDao;
import com.igomall.entity.course.Chapter;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Part;
import com.igomall.service.course.ChapterService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChapterServiceImpl extends BaseServiceImpl<Chapter,Long> implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;

    @Transactional(readOnly = true)
    public boolean snExists(String sn) {
        return chapterDao.exists("sn", sn, true);
    }

    @Transactional(readOnly = true)
    public Chapter findBySn(String sn) {
        return chapterDao.find("sn", sn, true);
    }

    @Override
    public List<Chapter> findList(Course course, Part part) {
        return chapterDao.findList(course,part);
    }
}

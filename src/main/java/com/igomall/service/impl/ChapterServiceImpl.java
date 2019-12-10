package com.igomall.service.impl;

import com.igomall.dao.ChapterDao;
import com.igomall.entity.Chapter;
import com.igomall.entity.Course;
import com.igomall.entity.Part;
import com.igomall.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl extends BaseServiceImpl<Chapter,Long> implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;

    @Override
    public List<Chapter> findList(Course course, Part part) {
        return chapterDao.findList(course,part);
    }
}

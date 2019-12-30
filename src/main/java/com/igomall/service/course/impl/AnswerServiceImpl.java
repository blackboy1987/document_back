package com.igomall.service.course.impl;

import com.igomall.dao.course.ChapterDao;
import com.igomall.entity.course.Answer;
import com.igomall.service.course.AnswerService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerServiceImpl extends BaseServiceImpl<Answer,Long> implements AnswerService {

    @Autowired
    private ChapterDao chapterDao;

    @Transactional(readOnly = true)
    public boolean snExists(String sn) {
        return chapterDao.exists("sn", sn, true);
    }

}

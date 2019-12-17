package com.igomall.service.course.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.course.CourseDao;
import com.igomall.entity.course.Course;
import com.igomall.service.course.CourseService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course,Long> implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Transactional(readOnly = true)
    public boolean snExists(String sn) {
        return courseDao.exists("sn", sn, true);
    }

    @Transactional(readOnly = true)
    public Course findBySn(String sn) {
        return courseDao.find("sn", sn, true);
    }

    public Page<Course> findPage(Pageable pageable, String title, String description, Date beginDate, Date endDate){
        return courseDao.findPage(pageable,title,description,beginDate,endDate);
    }
}

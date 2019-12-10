package com.igomall.service.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.CourseDao;
import com.igomall.entity.Course;
import com.igomall.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course,Long> implements CourseService {

    @Autowired
    private CourseDao courseDao;

    public Page<Course> findPage(Pageable pageable, String title, String description, Date beginDate, Date endDate){
        return courseDao.findPage(pageable,title,description,beginDate,endDate);
    }
}

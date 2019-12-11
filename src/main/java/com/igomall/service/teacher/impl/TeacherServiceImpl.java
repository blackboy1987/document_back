package com.igomall.service.teacher.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.teacher.TeacherDao;
import com.igomall.entity.teacher.Teacher;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TeacherServiceImpl extends BaseServiceImpl<Teacher,Long> implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public Page<Teacher> findPage(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate) {
        return teacherDao.findPage(pageable,name,isEnabled,beginDate,endDate);
    }
}

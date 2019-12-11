package com.igomall.service.teacher;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.teacher.Teacher;
import com.igomall.service.BaseService;

import java.util.Date;

public interface TeacherService extends BaseService<Teacher,Long> {

    Page<Teacher> findPage(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate);
}

package com.igomall.dao.teacher;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.teacher.Teacher;

import java.util.Date;

public interface TeacherDao extends BaseDao<Teacher,Long> {

    Page<Teacher> findPage(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate);
}

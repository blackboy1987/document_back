package com.igomall.service.course.impl;

import com.igomall.dao.course.PartDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Part;
import com.igomall.service.course.PartService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartServiceImpl extends BaseServiceImpl<Part,Long> implements PartService {

    @Autowired
    private PartDao partDao;

    public List<Part> findList(Course course){
        return partDao.findList(course);
    }
}

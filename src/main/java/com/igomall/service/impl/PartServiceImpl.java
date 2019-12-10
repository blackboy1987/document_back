package com.igomall.service.impl;

import com.igomall.dao.PartDao;
import com.igomall.entity.Course;
import com.igomall.entity.Part;
import com.igomall.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.IconUIResource;
import java.util.List;

@Service
public class PartServiceImpl extends BaseServiceImpl<Part,Long> implements PartService {

    @Autowired
    private PartDao partDao;

    public List<Part> findList(Course course){
        return partDao.findList(course);
    }
}

package com.igomall.service.impl;

import com.igomall.dao.ResourceDao;
import com.igomall.entity.Resource;
import com.igomall.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource,Long> implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public Resource findByName(String name) {
        return resourceDao.find("name",name);
    }
}

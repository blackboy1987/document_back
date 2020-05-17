
package com.igomall.wechat.service.material.impl;

import com.igomall.wechat.dao.material.NewsMaterialDao;
import com.igomall.wechat.entity.material.NewsMaterial;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.wechat.service.material.NewsMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class NewsMaterialServiceImpl extends BaseServiceImpl<NewsMaterial, Long> implements NewsMaterialService {

    @Autowired
    private NewsMaterialDao newsMaterialDao;

    public boolean mediaIdExists(String mediaId){
        return newsMaterialDao.exists("mediaId",mediaId);
    }

    public NewsMaterial findByMediaId(String mediaId){
        return newsMaterialDao.find("mediaId",mediaId);
    }
}
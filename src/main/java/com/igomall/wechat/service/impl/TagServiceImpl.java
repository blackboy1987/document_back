
package com.igomall.wechat.service.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.wechat.dao.TagDao;
import com.igomall.wechat.entity.Tag;
import com.igomall.wechat.service.TagService;
import com.igomall.wechat.util.TagUtils;
import com.igomall.wechat.util.response.tag.CreateResponse;
import com.igomall.wechat.util.response.tag.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class TagServiceImpl extends BaseServiceImpl<Tag, Long> implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public Tag findByName(String name){
        return tagDao.find("name",name);
    }

    @Override
    public Tag findByWeChatId(Long weChatId){
        return tagDao.find("weChatId",weChatId);
    }

    @Override
    public Page<Tag> findPage(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate){
        return tagDao.findPage(pageable,name,isEnabled,beginDate,endDate);
    }

    @Override
    public Tag save1(Tag tag){
        tag.setCount(0L);
        CreateResponse createResponse = TagUtils.create(tag.getName());
        if(createResponse.getErrCode()==null){
            tag.setName(createResponse.getTag().getName());
            tag.setWeChatId(createResponse.getTag().getId());
            return super.save(tag);
        }
        return null;
    }

    @Override
    public Tag update1(Tag tag){
        Tag parent = super.find(tag.getId());
        parent.setName(tag.getName());
        parent.setMemo(tag.getMemo());
        parent.setIsEnabled(tag.getIsEnabled());
        UpdateResponse updateResponse = TagUtils.update(parent.getWeChatId(),parent.getName());
        // 接口调用成功
        if(updateResponse.getErrCode()==0){
            return super.update(parent,"count","weChatId");
        }
        return null;
    }

}
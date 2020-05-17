package com.igomall.service;

import com.igomall.entity.Resource;

public interface ResourceService extends BaseService<Resource,Long> {

    Resource findByName(String name);

    /**
     * 查看点击数
     *
     * @param id
     *            ID
     * @return 点击数
     */
    long viewHits(Long id);
}

package com.igomall.service;

import com.igomall.entity.Resource;

public interface ResourceService extends BaseService<Resource,Long> {

    Resource findByName(String name);

    /**
     * 查看下载次数
     *
     * @param id
     *            ID
     * @return 下载次数
     */
    long downloadHits(Long id);
}

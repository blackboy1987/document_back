package com.igomall.service.document;

import com.igomall.entity.document.Resource;
import com.igomall.service.BaseService;

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

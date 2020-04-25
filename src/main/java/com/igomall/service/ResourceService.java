package com.igomall.service;

import com.igomall.entity.Resource;

public interface ResourceService extends BaseService<Resource,Long> {

    Resource findByName(String name);
}

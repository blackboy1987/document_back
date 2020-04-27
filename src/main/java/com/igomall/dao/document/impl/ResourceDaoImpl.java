package com.igomall.dao.document.impl;

import com.igomall.dao.document.ResourceDao;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.entity.document.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceDaoImpl extends BaseDaoImpl<Resource,Long> implements ResourceDao {
}

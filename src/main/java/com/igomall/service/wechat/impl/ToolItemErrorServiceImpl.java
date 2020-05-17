
package com.igomall.service.wechat.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.member.PointLogDao;
import com.igomall.dao.wechat.ToolItemErrorDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.PointLog;
import com.igomall.entity.wechat.ToolItemError;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.ToolItemErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class ToolItemErrorServiceImpl extends BaseServiceImpl<ToolItemError, Long> implements ToolItemErrorService {
    @Autowired
    private ToolItemErrorDao toolItemErrorDao;

    @Transactional(readOnly = true)
    public Page<ToolItemError> findPage(Member member, Pageable pageable) {
        return toolItemErrorDao.findPage(member, pageable);
    }
}

package com.igomall.service.document.impl;

import com.igomall.dao.AuditLogDao;
import com.igomall.dao.document.ResourceLogDao;
import com.igomall.entity.AuditLog;
import com.igomall.entity.document.ResourceLog;
import com.igomall.entity.member.Member;
import com.igomall.service.AuditLogService;
import com.igomall.service.document.ResourceLogService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Service - 审计日志
 *
 * @author blackboy
 * @version 1.0
 */
@Service
public class ResourceLogServiceImpl extends BaseServiceImpl<ResourceLog, Long> implements ResourceLogService {

	@Autowired
	private ResourceLogDao resourceLogDao;

	@Async
	@Override
	public void create(ResourceLog auditLog) {
    resourceLogDao.persist(auditLog);
	}

}


package com.igomall.service.document;

import com.igomall.entity.AuditLog;
import com.igomall.entity.document.ResourceLog;
import com.igomall.entity.member.Member;
import com.igomall.service.BaseService;

import javax.servlet.http.HttpServletRequest;

/**
 * Service - 审计日志
 *
 * @author blackboy
 * @version 1.0
 */
public interface ResourceLogService extends BaseService<ResourceLog, Long> {

	/**
	 * 创建审计日志(异步)
	 *
	 * @param resourceLog
	 *            审计日志
	 */
	void create(ResourceLog resourceLog);


}


package com.igomall.security;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.ApplicationEventPublisher;

import com.igomall.entity.User;
import com.igomall.event.UserLoggedOutEvent;
import com.igomall.service.UserService;

/**
 * Security - 注销过滤器
 * 
 * @author blackboy
 * @version 1.0
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	@Autowired
	private UserService userService;

	/**
	 * 请求前处理
	 * 
	 * @param servletRequest
	 *            ServletRequest
	 * @param servletResponse
	 *            ServletResponse
	 * @return 是否继续执行
	 */
	@Override
	protected boolean preHandle(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		User currentUser = userService.getCurrent();
		applicationEventPublisher.publishEvent(new UserLoggedOutEvent(this, currentUser));

		return super.preHandle(servletRequest, servletResponse);
	}

}
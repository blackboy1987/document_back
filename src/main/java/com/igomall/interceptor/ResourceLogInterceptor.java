/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.igomall.interceptor;

import com.igomall.entity.User;
import com.igomall.entity.document.ResourceLog;
import com.igomall.entity.member.Member;
import com.igomall.service.UserService;
import com.igomall.service.document.ResourceLogService;
import com.igomall.util.IpUtil;
import com.igomall.util.JsonUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Map.Entry;

public class ResourceLogInterceptor extends HandlerInterceptorAdapter {

	private static final String[] DEFAULT_IGNORE_PARAMETERS = new String[] { "password", "rePassword", "currentPassword" };


	private String[] ignoreParameters = DEFAULT_IGNORE_PARAMETERS;

  @Autowired
  private UserService userService;

  @Autowired
  private ResourceLogService resourceLogService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    User user = userService.getCurrent();
    ResourceLog resourceLog = new ResourceLog();
    String ip = IpUtil.getIpAddr(request);
    System.out.println(ip);
    if (user instanceof Member) {
      Member member = (Member) user;
      resourceLog.setUsername(member.getUsername());

    }
    StringBuffer parameter = new StringBuffer();
    Map<String, String[]> parameterMap = request.getParameterMap();
    if (parameterMap != null) {
      for (Entry<String, String[]> entry : parameterMap.entrySet()) {
        String parameterName = entry.getKey();
        if (!ArrayUtils.contains(ignoreParameters, parameterName)) {
          String[] parameterValues = entry.getValue();
          if (parameterValues != null) {
            for (String parameterValue : parameterValues) {
              parameter.append(parameterName + " = " + parameterValue + "\n");
            }
          }
        }
      }
    }
    resourceLog.setUser(user);
    resourceLog.setIp(ip);
    resourceLog.setParameter(JsonUtils.toJson(parameterMap));
    resourceLogService.create(resourceLog);

	}

	public String[] getIgnoreParameters() {
		return ignoreParameters;
	}

	public void setIgnoreParameters(String[] ignoreParameters) {
		this.ignoreParameters = ignoreParameters;
	}

}

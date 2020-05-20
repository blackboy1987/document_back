package com.igomall.interceptor;

import com.igomall.common.Message;
import com.igomall.entity.Admin;
import com.igomall.entity.member.Member;
import com.igomall.service.AdminService;
import com.igomall.service.UserService;
import com.igomall.service.member.MemberService;
import com.igomall.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		Member member = memberService.getCurrent();
		if(member==null){
			response.setContentType("application/json");
			Map<String, Object> data = new HashMap<>();
			data.put("message", Message.error("请先登录"));
			response.setStatus(999);
			return false;
		}
		return true;
	}
}

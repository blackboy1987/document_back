
package com.igomall.listener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.igomall.entity.member.Member;
import com.igomall.entity.member.PointLog;
import com.igomall.service.member.MemberService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.igomall.common.Setting;

import com.igomall.entity.User;
import com.igomall.event.UserLoggedInEvent;
import com.igomall.event.UserLoggedOutEvent;
import com.igomall.event.UserRegisteredEvent;
import com.igomall.util.SystemUtils;
import com.igomall.util.WebUtils;

/**
 * Listener - 用户事件
 *
 * @author IGOMALL  Team
 * @version 1.0
 */
@Component
public class UserEventListener {

	@Autowired
	private MemberService memberService;

	/**
	 * 事件处理
	 *
	 * @param userRegisteredEvent
	 *            用户注册事件
	 */
	@EventListener
	public void handle(UserRegisteredEvent userRegisteredEvent) {
		User user = userRegisteredEvent.getUser();
		HttpServletRequest request = WebUtils.getRequest();

		if (user instanceof Member) {
			Member member = (Member) user;
			Setting setting = SystemUtils.getSetting();
			if (setting.getRegisterPoint() > 0) {
				memberService.addPoint(member, setting.getRegisterPoint(), PointLog.Type.reward, null);
			}
		}
	}

	/**
	 * 事件处理
	 *
	 * @param userLoggedInEvent
	 *            用户登录事件
	 */
	@EventListener
	public void handle(UserLoggedInEvent userLoggedInEvent) {
		User user = userLoggedInEvent.getUser();
		HttpServletRequest request = WebUtils.getRequest();
		HttpServletResponse response = WebUtils.getResponse();

		if (user instanceof Member) {
			Member member = (Member) user;
			Subject subject = SecurityUtils.getSubject();
			sessionFixationProtection(subject);

			WebUtils.addCookie(request, response, Member.CURRENT_USERNAME_COOKIE_NAME, member.getUsername());
		}

	}

	/**
	 * 事件处理
	 *
	 * @param userLoggedOutEvent
	 *            用户注销事件
	 */
	@EventListener
	public void handle(UserLoggedOutEvent userLoggedOutEvent) {
		HttpServletRequest request = WebUtils.getRequest();
		HttpServletResponse response = WebUtils.getResponse();

		WebUtils.removeCookie(request, response, Member.CURRENT_USERNAME_COOKIE_NAME);
	}

	/**
	 * Session固定攻击防护
	 *
	 * @param subject
	 *            Subject
	 */
	private void sessionFixationProtection(Subject subject) {
		Assert.notNull(subject,"");

		Session session = subject.getSession();
		Map<Object, Object> attributes = new HashMap<>();
		Collection<Object> attributeKeys = session.getAttributeKeys();
		for (Object attributeKey : attributeKeys) {
			attributes.put(attributeKey, session.getAttribute(attributeKey));
		}
		session.stop();
		session = subject.getSession(true);
		for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
			session.setAttribute(entry.getKey(), entry.getValue());
		}
	}

}

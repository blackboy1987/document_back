
package com.igomall.event;

import com.igomall.entity.User;

/**
 * Event - 用户登录
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public class UserLoggedInEvent extends UserEvent {

	private static final long serialVersionUID = 3087635924598684802L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param user
	 *            用户
	 */
	public UserLoggedInEvent(Object source, User user) {
		super(source, user);
	}

}

package com.igomall.controller.member;

import com.igomall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 会员登录
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("memberLogoutController")
@RequestMapping("/member/api/logout")
public class LogoutController extends BaseController {

	@Autowired
	private UserService userService;

	/**
	 * 登录页面
	 */
	@PostMapping
	public Map<String,Object> index() {
		Map<String,Object> data = new HashMap<>();
		userService.logout();

		data.put("status","ok");
		data.put("content","退出成功");
		return data;
	}

}
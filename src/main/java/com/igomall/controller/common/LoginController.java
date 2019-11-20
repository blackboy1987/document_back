
package com.igomall.controller.common;

import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.Admin;
import com.igomall.security.UserAuthenticationToken;
import com.igomall.service.AdminService;
import com.igomall.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * Controller - 登陆
 * 
 * @author 夏黎
 * @version 1.0
 */
@RestController("LoginController")
@RequestMapping("/api/login")
public class LoginController extends BaseController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserService userService;

	/**
	 *  老师的登陆
	 */
	@PostMapping
	public ResponseEntity<?> index(HttpServletRequest request, String username, String password, String type) {
		Map<String,Object> data = new HashMap<>();
		data.put("type",type);

		if(StringUtils.isEmpty(username)|| StringUtils.isEmpty(password)){
			data.put("status","error");
			data.put("message", Message.error("请填写用户名/密码"));
			return ResponseEntity.ok(data);
		}
		Admin admin = adminService.findByUsername(username);
		if(admin==null||!admin.isValidCredentials(password)){
			data.put("status","error");
			data.put("message", Message.error("用户不存在"));
			return ResponseEntity.ok(data);
		}

		admin.setLastLoginDate(new Date());
		admin.setLastLoginIp(request.getRemoteAddr());
		admin.setIsLocked(false);
		admin.setLockDate(null);
		adminService.update(admin);
		userService.login(new UserAuthenticationToken(Admin.class, username, password, false, request.getRemoteAddr()));
		List<String> currentAuthorities = new ArrayList<>();
		currentAuthorities.addAll(adminService.getPermissions(admin));
		data.put("status","ok");
		data.put("currentAuthority",currentAuthorities);
		data.put("message",Message.success("ok"));
		return ResponseEntity.ok(data);
	}
}
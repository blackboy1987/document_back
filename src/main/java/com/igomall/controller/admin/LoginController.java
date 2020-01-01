
package com.igomall.controller.admin;

import com.igomall.common.Message;
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
@RestController("adminLoginController")
@RequestMapping("//admin/apilogin")
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
		data.put("code",999);
		return ResponseEntity.ok(data);
	}
}
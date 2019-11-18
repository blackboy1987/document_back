
package com.igomall.controller.admin;

import com.igomall.entity.Admin;
import com.igomall.entity.Role;
import com.igomall.security.UserAuthenticationToken;
import com.igomall.service.AdminService;
import com.igomall.service.RoleService;
import com.igomall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Controller - 管理员登录
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminLoginController")
@RequestMapping("/admin/login")
public class LoginController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;

	/**
	 * 登录页面
	 */
	@PostMapping
	public Map<String,String> index(String username, String password, HttpServletRequest request) {
		Admin admin = null;

		if(!adminService.usernameExists(username)){
			List<Role> roles = roleService.findAll();
			if(roles.isEmpty()){
				Role role = new Role();
				role.setIsSystem(true);
				role.setDescription("aa");
				role.setName("admin");
				List<String> permissions = new ArrayList<>();
				permissions.add("admin:menu:list");
				role.setPermissions(permissions);
				roleService.save(role);
			}


			admin = new Admin();
			admin.setEmail(username+"@qq.com");
			admin.setUsername(username);
			admin.setPassword(password);
			admin.setIsLocked(false);
			admin.setLockDate(null);
			admin.setLastLoginIp(null);
			admin.setLastLoginDate(null);
			admin.setIsEnabled(true);
			admin.setRoles(new HashSet<>(roles));
			adminService.save(admin);
		}else {
			admin = adminService.findByUsername(username);
		}
		userService.login(new UserAuthenticationToken(Admin.class, admin.getUsername(), password, false, request.getRemoteAddr()));
		Map<String,String> data = new HashMap<>();
		data.put("status","ok");
		data.put("currentAuthority","admin");
		data.put("type","account");
		return data;
	}

}

package com.igomall.controller.admin;

import com.igomall.entity.Admin;
import com.igomall.security.CurrentUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 首页
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/api")
public class IndexController {

	/**
	 * 首页
	 */
	@GetMapping("/currentUser")
	public Map<String,Object> currentUser(@CurrentUser Admin admin) {
		Map<String,Object> data = new HashMap<>();
		data.put("username",admin.getUsername());
		data.put("name",admin.getName());
		data.put("email",admin.getEmail());
		return data;
	}

}
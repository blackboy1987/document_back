
package com.igomall.controller.admin;

import com.igomall.entity.Admin;
import com.igomall.security.CurrentUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;

/**
 * Controller - 首页
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController
@RequestMapping("/admin")
public class IndexController {


	/**
	 * 首页
	 */
	@GetMapping("/currentUser")
	public String currentUser(@CurrentUser Admin admin) {
		System.out.println("=============================="+admin);
		return "admin/index";
	}

}
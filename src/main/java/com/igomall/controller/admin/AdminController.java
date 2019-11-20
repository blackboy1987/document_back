
package com.igomall.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Admin;
import com.igomall.entity.BaseEntity;
import com.igomall.service.AdminService;
import com.igomall.service.RoleService;
import com.igomall.service.UserService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;

/**
 * Controller - 管理员
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/admin")
public class AdminController extends BaseController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	/**
	 * 检查用户名是否存在
	 */
	@GetMapping("/check_username")
	public @ResponseBody boolean checkUsername(String username) {
		return StringUtils.isNotEmpty(username) && !adminService.usernameExists(username);
	}

	/**
	 * 检查E-mail是否唯一
	 */
	@GetMapping("/check_email")
	public @ResponseBody boolean checkEmail(Long id, String email) {
		return StringUtils.isNotEmpty(email) && adminService.emailUnique(id, email);
	}

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		model.addAttribute("roles", roleService.findAll());
		return "admin/admin/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(Admin admin, Long[] roleIds, Boolean unlock) {
		admin.setRoles(new HashSet<>(roleService.findList(roleIds)));
		if(admin.isNew()){
			if (!isValid(admin, BaseEntity.Save.class)) {
				return ERROR_MESSAGE;
			}
			if (adminService.usernameExists(admin.getUsername())) {
				return ERROR_MESSAGE;
			}
			if (adminService.emailExists(admin.getEmail())) {
				return ERROR_MESSAGE;
			}
			admin.setIsLocked(false);
			admin.setLockDate(null);
			admin.setLastLoginIp(null);
			admin.setLastLoginDate(null);
			adminService.save(admin);
			return SUCCESS_MESSAGE;
		}else {
			admin.setRoles(new HashSet<>(roleService.findList(roleIds)));
			if (!isValid(admin)) {
				return ERROR_MESSAGE;
			}
			if (!adminService.emailUnique(admin.getId(), admin.getEmail())) {
				return ERROR_MESSAGE;
			}
			Admin pAdmin = adminService.find(admin.getId());
			if (pAdmin == null) {
				return ERROR_MESSAGE;
			}
			if (BooleanUtils.isTrue(pAdmin.getIsLocked()) && BooleanUtils.isTrue(unlock)) {
				userService.unlock(admin);
				adminService.update(admin, "username", "encodedPassword", "lastLoginIp", "lastLoginDate");
			} else {
				adminService.update(admin, "username", "encodedPassword", "isLocked", "lockDate", "lastLoginIp", "lastLoginDate");
			}
			return SUCCESS_MESSAGE;
		}

	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	public Admin edit(Long id) {
		return adminService.find(id);
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(Admin.ListView.class)
	public Page<Admin> list(Pageable pageable) {
		return adminService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		if (ids.length >= adminService.count()) {
			return Message.error("admin.common.deleteAllNotAllowed");
		}
		adminService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}
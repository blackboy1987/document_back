
package com.igomall.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Pageable;
import com.igomall.entity.Role;
import com.igomall.service.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller - 角色
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminRoleController")
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add() {
		return "admin/role/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(Role role, RedirectAttributes redirectAttributes) {
		if (!isValid(role)) {
			return ERROR_VIEW;
		}
		role.setIsSystem(false);
		role.setAdmins(null);
		roleService.save(role);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("role", roleService.find(id));
		return "admin/role/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(Role role, RedirectAttributes redirectAttributes) {
		if (!isValid(role)) {
			return ERROR_VIEW;
		}
		Role pRole = roleService.find(role.getId());
		if (pRole == null || pRole.getIsSystem()) {
			return ERROR_VIEW;
		}
		roleService.update(role, "isSystem", "admins");
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", roleService.findPage(pageable));
		return "admin/role/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Role role = roleService.find(id);
				if (role != null && (role.getIsSystem() || CollectionUtils.isNotEmpty(role.getAdmins()))) {
					return Message.error("admin.role.deleteExistNotAllowed", role.getName());
				}
			}
			roleService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 列表
	 */
	@PostMapping("/listAll")
	@JsonView(Role.ListAll.class)
	public List<Role> listAll() {
		return roleService.findAll();
	}

}
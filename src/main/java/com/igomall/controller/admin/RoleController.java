
package com.igomall.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Role;
import com.igomall.service.DepartmentService;
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
@RequestMapping("/admin/api/role")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private DepartmentService departmentService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(Role role,Long departmentId) {
		if(role.getIsSystem()==null){
			role.setIsSystem(false);
		}
		if(role.getIsEnabled()==null){
			role.setIsEnabled(false);
		}
		if (!isValid(role)) {
			return Message.error("参数错误");
		}
		if(role.isNew()){
			role.setDepartment(departmentService.find(departmentId));
			role.setAdmins(null);
			roleService.save(role);
		}else {
			Role pRole = roleService.find(role.getId());
			if (pRole == null) {
				return Message.error("对象不存在");
			}
			roleService.update(role, "permissions","isSystem","admins","department");
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	@JsonView(Role.EditView.class)
	public Role edit(Long id) {
		return roleService.find(id);
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(Role.ListView.class)
	public Page<Role> list(Pageable pageable) {
		return roleService.findPage(pageable);
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
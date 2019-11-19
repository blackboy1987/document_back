
package com.igomall.controller.admin;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Permission;
import com.igomall.service.MenuService;
import com.igomall.service.PermissionService;
import com.igomall.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller - 权限
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminPermissionsController")
@RequestMapping("/admin/api/permissions")
public class PermissionController extends BaseController {

	@Autowired
	private PermissionService permissionsService;
	@Autowired
	private MenuService menuService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(Permission permission,Long menuId) {
		permission.setMenu(menuService.find(menuId));
		if(permission.getIsEnabled()==null){
			permission.setIsEnabled(false);
		}

		permission.setUrl(permission.getMenu().getPermission()+":"+permission.getMethod());

		if(permissionsService.exists(permission)){
			return Message.error("权限已存在");
		}


		if (!isValid(permission)) {
			return Message.error("参数错误");
		}
		if(permission.isNew()){
			permissionsService.save(permission);
		}else{
			permissionsService.update(permission,"children");
		}

		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	public Permission edit(Long id) {
		return permissionsService.find(id);
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Page<Permission> list(Pageable pageable) {
		return permissionsService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		permissionsService.delete(ids);
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/getAll")
	public List<Permission> getAll() {
		return permissionsService.findAll();
	}

}
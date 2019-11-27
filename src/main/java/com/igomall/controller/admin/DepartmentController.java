
package com.igomall.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.entity.Department;
import com.igomall.service.DepartmentService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

/**
 * Controller - 部门
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminDepartmentController")
@RequestMapping("/admin/department")
public class DepartmentController extends BaseController {

	@Autowired
	private DepartmentService departmentService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(Department department, Long parentId) {
		department.setParent(departmentService.find(parentId));
		if(department.getIsEnabled()==null){
			department.setIsEnabled(false);
		}
		if (!isValid(department)) {
			return Message.error("参数错误");
		}
		if(department.isNew()){
			department.setTreePath(null);
			department.setGrade(null);
			department.setChildren(null);
			department.setAdmins(new HashSet<>());
			departmentService.save(department);
		}else{
			departmentService.update(department,"children","admins");
		}

		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	public Department edit(Long id) {
		return departmentService.find(id);
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public List<Department> list() {
		return departmentService.findRoots();
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Department department = departmentService.find(id);
				if (department != null && CollectionUtils.isNotEmpty(department.getAdmins())) {
					return Message.error("删除失败");
				}
			}
			departmentService.delete(ids);
		}
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/tree")
	@JsonView(Department.TreeView.class)
	public List<Department> tree() {
		return departmentService.findTree();
	}

}
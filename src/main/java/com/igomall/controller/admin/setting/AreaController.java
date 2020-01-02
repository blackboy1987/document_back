
package com.igomall.controller.admin.setting;

import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.setting.Area;
import com.igomall.service.setting.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("adminAreaController")
@RequestMapping("/admin/api/area")
public class AreaController extends BaseController {

	@Autowired
	private AreaService areaService;

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(Long parentId, ModelMap model) {
		model.addAttribute("parent", areaService.find(parentId));
		return "admin/area/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(Area area, Long parentId) {
		area.setParent(areaService.find(parentId));
		if (!isValid(area)) {
			return ERROR_VIEW;
		}
		area.setFullName(null);
		area.setTreePath(null);
		area.setGrade(null);
		area.setChildren(null);
		area.setMembers(null);
		areaService.save(area);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("area", areaService.find(id));
		return "admin/area/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(Area area) {
		if (!isValid(area)) {
			return ERROR_VIEW;
		}
		areaService.update(area, "fullName", "treePath", "grade", "parent", "children", "members");
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Long parentId, ModelMap model) {
		Area parent = areaService.find(parentId);
		if (parent != null) {
			model.addAttribute("parent", parent);
			model.addAttribute("areas", new ArrayList<>(parent.getChildren()));
		} else {
			model.addAttribute("areas", areaService.findRoots());
		}
		return "admin/area/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long id) {
		areaService.delete(id);
		return SUCCESS_MESSAGE;
	}

}
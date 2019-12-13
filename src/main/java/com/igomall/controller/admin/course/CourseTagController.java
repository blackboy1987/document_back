
package com.igomall.controller.admin.course;

import com.igomall.common.Message;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.CourseTag;
import com.igomall.service.course.CourseTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

/**
 * Controller - 商品标签
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminCourseTagController")
@RequestMapping("/course_tag")
public class CourseTagController extends BaseController {

	@Autowired
	private CourseTagService courseTagService;

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		return "admin/product_tag/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(CourseTag courseTag) {
		if (!isValid(courseTag, BaseEntity.Save.class)) {
			return ERROR_VIEW;
		}
		courseTag.setCourses(new HashSet<>());
		courseTagService.save(courseTag);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("courseTag", courseTagService.find(id));
		return "admin/product_tag/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(CourseTag courseTag) {
		if (!isValid(courseTag)) {
			return ERROR_VIEW;
		}
		courseTagService.update(courseTag, "products");
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", courseTagService.findPage(pageable));
		return "admin/product_tag/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody
	Message delete(Long[] ids) {
		courseTagService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}

package com.igomall.controller.admin.course;

import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseCategory;
import com.igomall.service.course.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller - 商品分类
 * 
 * @author blackboy
 * @version 1.0
 */
@Controller("adminProductCategoryController")
@RequestMapping("/admin/product_category")
public class CourseCategoryController extends BaseController {

	@Autowired
	private CourseCategoryService courseCategoryService;


	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(CourseCategory courseCategory, Long parentId) {
		courseCategory.setParent(courseCategoryService.find(parentId));
		if (!isValid(courseCategory)) {
			return ERROR_VIEW;
		}
		courseCategory.setTreePath(null);
		courseCategory.setGrade(null);
		courseCategory.setChildren(null);
		courseCategory.setCourses(new HashSet<>());
		courseCategoryService.save(courseCategory);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public CourseCategory edit(Long id) {
		return courseCategoryService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(CourseCategory courseCategory, Long parentId) {
		courseCategory.setParent(courseCategoryService.find(parentId));
		if (!isValid(courseCategory)) {
			return ERROR_VIEW;
		}
		if (courseCategory.getParent() != null) {
			CourseCategory parent = courseCategory.getParent();
			if (parent.equals(courseCategory)) {
				return ERROR_VIEW;
			}
			List<CourseCategory> children = courseCategoryService.findChildren(parent, true, null);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		courseCategoryService.update(courseCategory, "treePath", "grade", "children", "courses");
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public List<CourseCategory> list() {
		return courseCategoryService.findTree();
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody
	Message delete(Long id) {
		CourseCategory courseCategory = courseCategoryService.find(id);
		if (courseCategory == null) {
			return ERROR_MESSAGE;
		}
		Set<CourseCategory> children = courseCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("admin.productCategory.deleteExistChildrenNotAllowed");
		}
		Set<Course> courses = courseCategory.getCourses();
		if (courses != null && !courses.isEmpty()) {
			return Message.error("admin.productCategory.deleteExistProductNotAllowed");
		}
		courseCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}

}
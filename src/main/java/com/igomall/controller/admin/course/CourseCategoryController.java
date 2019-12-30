
package com.igomall.controller.admin.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseCategory;
import com.igomall.service.course.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller - 商品分类
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminCourseCategoryController")
@RequestMapping("/admin/api/course_category")
public class CourseCategoryController extends BaseController {

	@Autowired
	private CourseCategoryService courseCategoryService;


	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(CourseCategory courseCategory, Long parentId) {
		courseCategory.setParent(courseCategoryService.find(parentId));
		if(courseCategory.getIsEnabled()==null){
			courseCategory.setIsEnabled(false);
		}
		if (!isValid(courseCategory)) {
			return Message.error("参数错误");
		}
		courseCategory.setTreePath(null);
		courseCategory.setGrade(null);
		courseCategory.setChildren(null);
		courseCategory.setCourses(new HashSet<>());
		courseCategoryService.save(courseCategory);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	@JsonView(CourseCategory.EditView.class)
	public CourseCategory edit(Long id) {
		return courseCategoryService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(CourseCategory courseCategory, Long parentId) {
		courseCategory.setParent(courseCategoryService.find(parentId));
		if(courseCategory.getIsEnabled()==null){
			courseCategory.setIsEnabled(false);
		}
		if (!isValid(courseCategory)) {
			return Message.error("参数错误");
		}
		if (courseCategory.getParent() != null) {
			CourseCategory parent = courseCategory.getParent();
			if (parent.equals(courseCategory)) {
				return Message.error("参数错误");
			}
			List<CourseCategory> children = courseCategoryService.findChildren(parent, true, null);
			if (children != null && children.contains(parent)) {
				return Message.error("参数错误");
			}
		}
		courseCategoryService.update(courseCategory, "treePath", "grade", "children", "courses");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(CourseCategory.ListView.class)
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

	@PostMapping("/all")
	@JsonView(CourseCategory.AllView.class)
	public List<CourseCategory> all(){
		return courseCategoryService.findRoots();
	}

}
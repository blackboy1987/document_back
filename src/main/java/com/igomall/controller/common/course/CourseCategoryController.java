
package com.igomall.controller.common.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.CourseCategory;
import com.igomall.service.course.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller - 商品分类
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("commonCourseCategoryController")
@RequestMapping("/api/course_category")
public class CourseCategoryController extends BaseController {

	@Autowired
	private CourseCategoryService courseCategoryService;

	@PostMapping("/all")
	@JsonView(CourseCategory.AllView.class)
	public List<CourseCategory> all(){
		return courseCategoryService.findRoots();
	}

}
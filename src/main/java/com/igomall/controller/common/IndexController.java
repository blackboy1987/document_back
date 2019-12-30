
package com.igomall.controller.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.controller.member.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseCategory;
import com.igomall.entity.member.Member;
import com.igomall.entity.setting.Ad;
import com.igomall.security.CurrentUser;
import com.igomall.service.course.CourseCategoryService;
import com.igomall.service.course.CourseService;
import com.igomall.service.setting.AdService;
import org.jboss.jandex.IndexView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller - 会员登录
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("commonIndexController")
@RequestMapping("/api/index")
public class IndexController extends BaseController {

	@Autowired
	private AdService adService;
	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseCategoryService courseCategoryService;

	@PostMapping
	@JsonView(BaseEntity.CommonView.class)
	public Map<String,Object> index(){
		Map<String,Object> data = new HashMap<>();
		// 首页滚动图片
		List<Ad> ads = adService.findAll();
		data.put("ads",ads);

		List<CourseCategory> courseCategories = courseCategoryService.findRoots();
		List<Map<String,Object>> list = new ArrayList<>();
		for (CourseCategory courseCategory:courseCategories) {
			Map<String,Object> map = new HashMap<>();
			map.put("title",courseCategory.getName());
			// 首页最新课程
			List<Course> courses = courseService.findList(courseCategory,null,8,null,null);
			map.put("courses",courses);
			list.add(map);
		}
		data.put("list",list);
		return data;
	}

}
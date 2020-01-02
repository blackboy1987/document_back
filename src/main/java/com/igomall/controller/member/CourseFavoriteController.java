
package com.igomall.controller.member;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.common.Results;
import com.igomall.entity.course.Course;
import com.igomall.entity.member.CourseFavorite;
import com.igomall.entity.member.Member;
import com.igomall.service.course.CourseService;
import com.igomall.service.member.CourseFavoriteService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonView;

import com.igomall.entity.BaseEntity;
import com.igomall.exception.UnauthorizedException;
import com.igomall.security.CurrentUser;

/**
 * Controller - 商品收藏
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Controller("memberCourseFavoriteController")
@RequestMapping("/member/api/course_favorite")
public class CourseFavoriteController extends BaseController {

	@Autowired
	private CourseFavoriteService courseFavoriteService;
	@Autowired
	private CourseService courseService;

	/**
	 * 添加属性
	 */
	@ModelAttribute
	public void populateModel(Long courseId, Long courseFavoriteId, @CurrentUser Member currentUser, ModelMap model) {
		model.addAttribute("course", courseService.find(courseId));

		CourseFavorite courseFavorite = courseFavoriteService.find(courseFavoriteId);
		if (courseFavorite != null && !currentUser.equals(courseFavorite.getMember())) {
			throw new UnauthorizedException();
		}
		model.addAttribute("courseFavorite", courseFavorite);
	}

	/**
	 * 添加
	 */
	@PostMapping("/add")
	public ResponseEntity<?> add(@ModelAttribute(binding = false) Course course, @CurrentUser Member currentUser) {
		if (course == null || BooleanUtils.isNotTrue(course.getIsActive())) {
			return Results.NOT_FOUND;
		}
		if (courseFavoriteService.exists(currentUser, course)) {
			return Results.unprocessableEntity("member.courseFavorite.exist");
		}
		if (BooleanUtils.isNotTrue(course.getIsMarketable())) {
			return Results.unprocessableEntity("member.courseFavorite.notMarketable");
		}
		if (CourseFavorite.MAX_COURSE_FAVORITE_SIZE != null && courseFavoriteService.count(currentUser) >= CourseFavorite.MAX_COURSE_FAVORITE_SIZE) {
			return Results.unprocessableEntity("member.courseFavorite.addCountNotAllowed", CourseFavorite.MAX_COURSE_FAVORITE_SIZE);
		}
		CourseFavorite courseFavorite = new CourseFavorite();
		courseFavorite.setMember(currentUser);
		courseFavorite.setCourse(course);
		courseFavoriteService.save(courseFavorite);
		return Results.OK;
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Page<CourseFavorite> list(Pageable pageable, @CurrentUser Member currentUser) {
		return courseFavoriteService.findPage(currentUser, pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@ModelAttribute(binding = false) CourseFavorite courseFavorite) {
		if (courseFavorite == null) {
			return Results.NOT_FOUND;
		}

		courseFavoriteService.delete(courseFavorite);
		return Results.OK;
	}

}
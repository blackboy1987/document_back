
package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.common.Results;
import com.igomall.entity.course.Course;
import com.igomall.entity.member.CourseFavorite;
import com.igomall.entity.member.Member;
import com.igomall.security.CurrentUser;
import com.igomall.service.course.CourseService;
import com.igomall.service.member.CourseFavoriteService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 课程收藏
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("memberCourseFavoriteController")
@RequestMapping("/member/api/course_favorite")
public class CourseFavoriteController extends BaseController {

	@Autowired
	private CourseFavoriteService courseFavoriteService;
	@Autowired
	private CourseService courseService;

	/**
	 * 添加
	 */
	@PostMapping("/add")
	public Message add(String courseSn, @CurrentUser Member currentUser) {
		Course course = courseService.findBySn(courseSn);
		if (course == null || BooleanUtils.isNotTrue(course.getIsActive())) {
			return Message.error("课程不存在");
		}
		if (courseFavoriteService.exists(currentUser, course)) {
			return Message.error("您已收藏该课程");
		}
		if (BooleanUtils.isNotTrue(course.getIsMarketable())) {
			return Message.error("课程不存在");
		}
		if (CourseFavorite.MAX_COURSE_FAVORITE_SIZE != null && courseFavoriteService.count(currentUser) >= CourseFavorite.MAX_COURSE_FAVORITE_SIZE) {
			return Message.error("收藏课程已超过最大值");
		}
		CourseFavorite courseFavorite = new CourseFavorite();
		courseFavorite.setMember(currentUser);
		courseFavorite.setCourse(course);
		courseFavoriteService.save(courseFavorite);
		return Message.success("收藏成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(CourseFavorite.ListView.class)
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
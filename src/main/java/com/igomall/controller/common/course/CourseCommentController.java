package com.igomall.controller.common.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseCategory;
import com.igomall.entity.course.CourseComment;
import com.igomall.entity.course.CourseTag;
import com.igomall.service.course.CourseCategoryService;
import com.igomall.service.course.CourseCommentService;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.LessonService;
import com.igomall.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController("apiCourseCommentController")
@RequestMapping("/api/courseComment")
public class CourseCommentController extends BaseController {

    @Autowired
    private CourseCommentService courseCommentService;

    @PostMapping("/list")
    @JsonView(CourseComment.ListView.class)
    public Page<CourseComment> list(Pageable pageable,Long courseId){
        return courseCommentService.findPage(pageable);
    }

}

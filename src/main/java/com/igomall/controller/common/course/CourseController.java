package com.igomall.controller.common.course;

import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.Course;
import com.igomall.service.course.CourseCommentService;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.LessonService;
import com.igomall.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("apiCourseController")
@RequestMapping("/api/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseCommentService courseCommentService;

    @PostMapping("/info")
    public Map<String,Object> info(Long id){
        Map<String,Object> data = new HashMap<>();
        Course course = courseService.find(id);
        Map<String,Object> courseMap = new HashMap<>();
        courseMap.put("title",course.getTitle());
        courseMap.put("description",course.getDescription());
        // 课程信息
        data.put("course",courseMap);
        // 视频列表
        data.put("lessons",lessonService.findListByCourseSQL(course));
        // 老师信息
        data.put("teacher",teacherService.findBySQL(course.getTeacher()));
        // 评论信息
        data.put("courseComments",courseCommentService.findListBySQL(course));


        return data;
    }
}

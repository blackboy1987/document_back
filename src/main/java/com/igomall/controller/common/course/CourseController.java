package com.igomall.controller.common.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseCategory;
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

import java.io.File;
import java.util.*;

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
    @Autowired
    private CourseCategoryService courseCategoryService;

    @PostMapping("/info")
    public Map<String,Object> info(String sn){
        Map<String,Object> data = new HashMap<>();
        Course course = courseService.findBySn(sn);
        Map<String,Object> courseMap = new HashMap<>();
        courseMap.put("title",course.getTitle());
        courseMap.put("sn",course.getSn());
        courseMap.put("description",course.getDescription());
        courseMap.put("price",course.getPrice());
        courseMap.put("teacher",course.getTeacher().getName());
        Set<CourseTag> courseTags = course.getCourseTags();
        List<String> tags = new ArrayList<>();
        for (CourseTag courseTag:courseTags) {
            tags.add(courseTag.getName());
        }
        courseMap.put("tags",tags);
        courseMap.put("image",course.getImage());
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

    /**
     * 课程列表
     * @param pageable
     *      分页属性
     * @return
     *      课程列表
     */
    @PostMapping("/list")
    @JsonView(Course.WebView.class)
    public Message list(Pageable pageable,Long courseCategoryId,Boolean isVip){
        CourseCategory courseCategory = courseCategoryService.find(courseCategoryId);


       return Message.success1("操作成功",courseService.findPage(courseCategory,isVip,pageable));
    }

    @PostMapping("/create")
    public Message create(){
        String courseTitle = "JQuery基础教程";
        String path = "/Volumes/blackboy/百度云/传智播客全套/01.最新整理49期JAVA基础加就业班全套视频主要看/05.JQuery(共18集)/视频";
        File parent = new File(path);
        File[] files = parent.listFiles();
        System.out.println(files.length);

        return Message.success("aa");
    }

    public static void main(String[] args) {
        new CourseController().create();
    }
}

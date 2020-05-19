package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.controller.common.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseTag;
import com.igomall.entity.course.Lesson;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.CourseTagService;
import com.igomall.service.course.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("commonCourseController")
@RequestMapping("/member/api/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseTagService courseTagService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private LessonService lessonService;

    @PostMapping("/index")
    @JsonView(BaseEntity.ApiListView.class)
    public List<CourseTag> index(){
        List<Lesson> lessons = lessonService.findAll();
        for (Lesson lesson:lessons) {
            lesson.setDuration(Integer.valueOf(lesson.getProps().get("duration")));
            lessonService.update(lesson);
        }

        return courseTagService.findAll();
    }


    @PostMapping("/detail")
    @JsonView(BaseEntity.ApiListView.class)
    public Map<String,Object> index(Long id){
        Map<String,Object> data = new HashMap<>();
        Course course =courseService.find(id);
        if(course!=null){
            data.put("title",course.getTitle());
            data.putAll(course.getProps());
            data.put("lessons",course.getLessons());
        }
        return data;
    }

    @PostMapping("/download_hits")
    public Map<String,Object> downloadHits(Long id){
        Map<String, Object> data = new HashMap<>();
        courseService.viewHits(lessonService.find(id).getCourse().getId());
        data.put("downloadHits", lessonService.viewHits(id));
        return data;
    }
}

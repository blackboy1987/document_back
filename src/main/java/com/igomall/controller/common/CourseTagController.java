package com.igomall.controller.common;

import com.igomall.common.Message;
import com.igomall.service.course.CourseTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("commonCourseTagController")
@RequestMapping("/api/course_tag")
public class CourseTagController extends BaseController {

    @Autowired
    private CourseTagService courseTagService;


    @PostMapping("/delete")
    public Message delete(Long id){
        courseTagService.delete(id);
        return Message.success("ok");
    }
}

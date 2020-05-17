package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.controller.common.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.CourseTag;
import com.igomall.service.course.CourseTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("commonCourseController")
@RequestMapping("/member/api/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseTagService courseTagService;

    @PostMapping("/index")
    @JsonView(BaseEntity.ApiListView.class)
    public List<CourseTag> index(){
        return courseTagService.findAll();
    }
}

package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.CourseComment;
import com.igomall.entity.member.Member;
import com.igomall.security.CurrentUser;
import com.igomall.service.course.CourseCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("memberApiCourseCommentController")
@RequestMapping("/member/api/course_comment")
public class CourseCommentController extends BaseController {

    @Autowired
    private CourseCommentService courseCommentService;

    @PostMapping("/list")
    @JsonView(CourseComment.ListView.class)
    public Page<CourseComment> list(Pageable pageable, @CurrentUser Member member){
        pageable.setPageNumber(10);
        return courseCommentService.findPage(member,null,null,null,null,pageable );
    }

}

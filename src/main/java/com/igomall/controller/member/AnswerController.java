package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.Answer;
import com.igomall.entity.member.Member;
import com.igomall.security.CurrentUser;
import com.igomall.service.course.AnswerService;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("memberAnswerController")
@RequestMapping("/member/api/answer")
public class AnswerController extends BaseController {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private LessonService lessonService;

    public Message save(Answer answer, @CurrentUser Member member,Long courseId,Long lessonId){
        answer.setMember(member);
        answer.setCourse(courseService.find(courseId));
        answer.setLesson(lessonService.find(lessonId));
        if(isValid(answer)){
            return Message.error("参数错误");
        }
        answerService.save(answer);
        return SUCCESS_MESSAGE;
    }

    @PostMapping("/list")
    @JsonView(Answer.ListView.class)
    public Page<Answer> list(Pageable pageable){
        return answerService.findPage(pageable);
    }


    @PostMapping("/delete")
    public Message delete(Long id){
        answerService.delete(id);
        return SUCCESS_MESSAGE;
    }
}

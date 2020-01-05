package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.Sn;
import com.igomall.entity.course.Answer;
import com.igomall.entity.member.Member;
import com.igomall.security.CurrentUser;
import com.igomall.service.SnService;
import com.igomall.service.course.AnswerService;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RestController("memberAnswerController")
@RequestMapping("/member/api/answer")
public class AnswerController extends BaseController {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private SnService snService;

    @PostMapping("/save")
    public Message save(Answer answer, @CurrentUser Member member,String courseSn,Long lessonId){
        answer.setMember(member);
        answer.setCourse(courseService.findBySn(courseSn));
        answer.setLesson(lessonService.find(lessonId));
        answer.setSn(snService.generate(Sn.Type.answer));
        answer.setPoint(10L);
        answer.setStatus(0);
        answer.setForAnswer(null);
        answer.setReplyAnswers(null);
        if(!isValid(answer)){
            return Message.error("参数错误");
        }
        // 检测当前账号的爱豆余额够不够。如果不够，提示余额不足。
        if(member.getPoint().compareTo(10L)<0){
            return Message.error("爱豆余额不足");
        }
        answerService.save(answer);
        return Message.success("操作成功");
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

package com.igomall.controller.admin.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.Answer;
import com.igomall.service.course.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminAnswerController")
@RequestMapping("/admin/api/answer")
public class AnswerController extends BaseController {

    @Autowired
    private AnswerService answerService;

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

package com.igomall.controller.admin.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.Lesson;
import com.igomall.service.course.ChapterService;
import com.igomall.service.course.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lesson")
public class LessonController extends BaseController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private ChapterService chapterService;

    @PostMapping("/save")
    public Message save(Lesson lesson,Long chapterId){

        lesson.setChapter(chapterService.find(chapterId));
        if(!isValid(lesson)){
            return Message.error("参数错误");
        }
        if(lesson.isNew()){
            lessonService.save(lesson);
        }else {
            lessonService.update(lesson);
        }
        return SUCCESS_MESSAGE;
    }

    @PostMapping("/list")
    @JsonView(Lesson.ListView.class)
    public Page<Lesson> list(Pageable pageable){
        return lessonService.findPage(pageable);
    }

    @PostMapping("/edit")
    @JsonView(Lesson.EditView.class)
    public Lesson edit(Long id){
        return lessonService.find(id);
    }

    @PostMapping("/delete")
    public Message delete(Long id){
        lessonService.delete(id);
        return SUCCESS_MESSAGE;
    }
}

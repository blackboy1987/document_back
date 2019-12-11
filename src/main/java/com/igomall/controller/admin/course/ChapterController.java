package com.igomall.controller.admin.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.Chapter;
import com.igomall.service.course.ChapterService;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chapter")
public class ChapterController extends BaseController {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private PartService partService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/save")
    public Message save(Chapter chapter,Long partId, Long courseId){
        chapter.setPart(partService.find(partId));
        chapter.setCourse(courseService.find(courseId));
        if(!isValid(chapter)){
            return Message.error("参数错误");
        }
        if(chapter.isNew()){
            chapterService.save(chapter);
        }else {
            chapterService.update(chapter,"part","course");
        }
        return SUCCESS_MESSAGE;
    }

    @PostMapping("/list")
    @JsonView(Chapter.ListView.class)
    public Page<Chapter> list(Pageable pageable){
        return chapterService.findPage(pageable);
    }


    @PostMapping("/edit")
    @JsonView(Chapter.EditView.class)
    public Chapter edit(Long id){
        return chapterService.find(id);
    }

    @PostMapping("/delete")
    public Message delete(Long id){
        Chapter chapter = chapterService.find(id);
        if(chapter!=null&&chapter.getLessons().size()>0){
            return Message.error("存在章节数据，删除失败");
        }
        chapterService.delete(chapter);
        return SUCCESS_MESSAGE;
    }

    /**
     * 获取课程下面的chapter
     * @param courseId
     * @return
     */
    @PostMapping("/allList")
    @JsonView(Chapter.AllList.class)
    public List<Chapter> allList(Long courseId,Long partId){
        return chapterService.findList(courseService.find(courseId),partService.find(partId));
    }
}

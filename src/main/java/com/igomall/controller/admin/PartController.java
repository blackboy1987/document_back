package com.igomall.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Part;
import com.igomall.service.CourseService;
import com.igomall.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/part")
public class PartController extends BaseController {

    @Autowired
    private PartService partService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/save")
    public Message save(Part part,Long courseId){
        part.setCourse(courseService.find(courseId));
        if(!isValid(part)){
            return Message.error("参数错误");
        }
        if(part.isNew()){
            partService.save(part);
        }else {
            partService.update(part,"course");
        }
        return SUCCESS_MESSAGE;
    }

    @PostMapping("/list")
    @JsonView(Part.ListView.class)
    public Page<Part> list(Pageable pageable){
        return partService.findPage(pageable);
    }


    @PostMapping("/edit")
    @JsonView(Part.EditView.class)
    public Part edit(Long id){
        return partService.find(id);
    }
    
    @PostMapping("/delete")
    public Message delete(Long id){
        Part part = partService.find(id);
        if(part!=null&&part.getChapter().size()>0){
            return Message.error("存在章节数据，删除失败");
        }
        partService.delete(part);
        return SUCCESS_MESSAGE;
    }

    /**
     * 获取课程下面的part
     * @param courseId
     * @return
     */
    @PostMapping("/allList")
    @JsonView(Part.AllList.class)
    public List<Part> allList(Long courseId){
        return partService.findList(courseService.find(courseId));
    }
}

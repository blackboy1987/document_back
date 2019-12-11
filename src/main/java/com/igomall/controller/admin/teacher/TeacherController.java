package com.igomall.controller.admin.teacher;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.teacher.Teacher;
import com.igomall.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/teacher")
public class TeacherController extends BaseController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Message save(Teacher teacher) {
        if(teacher.isNew()){
            teacherService.save(teacher);
        }else{
            teacherService.update(teacher);
        }
        return Message.success("操作成功");
    }

    /**
     * 编辑
     */
    @PostMapping("/edit")
    @JsonView(Teacher.EditView.class)
    public Teacher edit(Long id) {
        return teacherService.find(id);
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @JsonView(Teacher.ListView.class)
    public Page<Teacher> list(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate) {
        return teacherService.findPage(pageable,name,isEnabled,beginDate,endDate);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Message delete(Long[] ids) {
        if (ids.length >= teacherService.count()) {
            return Message.error("admin.common.deleteAllNotAllowed");
        }
        teacherService.delete(ids);
        return SUCCESS_MESSAGE;
    }

}

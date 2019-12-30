package com.igomall.controller.admin.teacher;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.teacher.TeacherRank;
import com.igomall.service.teacher.TeacherRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/api/teacher_rank")
public class TeacherRankController extends BaseController {

    @Autowired
    private TeacherRankService teacherRankService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Message save(TeacherRank teacherRank) {
        if (teacherRank.getIsDefault()==null) {
            teacherRank.setIsDefault(false);
        }
        if (teacherRank.getIsDefault()==null) {
            teacherRank.setIsDefault(false);
        }
        if(teacherRank.isNew()){
            teacherRank.setTeachers(null);
            teacherRankService.save(teacherRank);
        }else{
            teacherRankService.update(teacherRank,"teachers");
        }
        return Message.success("操作成功");
    }

    /**
     * 编辑
     */
    @PostMapping("/edit")
    @JsonView(TeacherRank.EditView.class)
    public TeacherRank edit(Long id) {
        return teacherRankService.find(id);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Message update(TeacherRank teacherRank, Long id) {
        if (teacherRank.getIsDefault()==null) {
            teacherRank.setIsDefault(false);
        }
        if (teacherRank.getIsEnabled()==null) {
            teacherRank.setIsEnabled(false);
        }
        if (!isValid(teacherRank)) {
            return Message.error("参数错误");
        }
        TeacherRank pTeacherRank = teacherRankService.find(id);
        if (pTeacherRank == null) {
            return Message.error("参数错误");
        }
        if (pTeacherRank.getIsDefault()==null) {
            pTeacherRank.setIsDefault(false);
        }
        teacherRankService.update(teacherRank, "members");
        return Message.success("操作成功");
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @JsonView(TeacherRank.ListView.class)
    public Page<TeacherRank> list(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate) {
        return teacherRankService.findPage(pageable,name,isEnabled,beginDate,endDate);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Message delete(Long[] ids) {
        if (ids.length >= teacherRankService.count()) {
            return Message.error("admin.common.deleteAllNotAllowed");
        }
        teacherRankService.delete(ids);
        return SUCCESS_MESSAGE;
    }

    @PostMapping("/all")
    @JsonView(TeacherRank.AllView.class)
    public List<TeacherRank> all() {
        return teacherRankService.findAll();
    }
}

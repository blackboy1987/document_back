package com.igomall.controller.admin.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.Course;
import com.igomall.service.course.CourseService;
import com.igomall.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/save")
    public Message save(Course course,Long teacherId){
        course.setTeacher(teacherService.find(teacherId));
        if(!isValid(course)){
            return Message.error("参数错误");
        }
        if(course.isNew()){
            courseService.save(course);
        }else {
            courseService.update(course,"chapters","parts","teacher");
        }
        return Message.success("操作成功");
    }

    @PostMapping("/list")
    @JsonView(Course.ListView.class)
    public Page<Course> list(Pageable pageable, String title, String description, Date beginDate,Date endDate){
        return courseService.findPage(pageable,title,description,beginDate,endDate);
    }


    @PostMapping("/edit")
    @JsonView(Course.EditView.class)
    public Course edit(Long id){
        return courseService.find(id);
    }

    @PostMapping("/delete")
    public Message delete(Long id){
        Course course = courseService.find(id);
        if(course!=null&&course.getParts().size()>0){
            return Message.error("存在章节数据，删除失败");
        }
        courseService.delete(course);
        return SUCCESS_MESSAGE;
    }


    /**
     * 所有课程列表
     * @return
     */
    @PostMapping("/allList")
    @JsonView(Course.AllListView.class)
    public List<Course> allList(){
        return courseService.findAll();
    }

}

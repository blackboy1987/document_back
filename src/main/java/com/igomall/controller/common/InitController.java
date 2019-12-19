
package com.igomall.controller.common;

import com.igomall.entity.course.Chapter;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.teacher.Teacher;
import com.igomall.entity.teacher.TeacherRank;
import com.igomall.service.course.CourseService;
import com.igomall.service.teacher.TeacherRankService;
import com.igomall.service.teacher.TeacherService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Controller - 错误
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("commonInitController")
@RequestMapping("/common/error")
public class InitController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRankService teacherRankService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/init")
    public String main(String[] args) throws Exception{
        //initTeacher();
        initCourse(teacherService.findAll());
        return "ok";
    }


    private void initTeacher() throws Exception{
        Document document = Jsoup.parse(new URL("http://www.ujiuye.com/jiaoxue/pxsz/"),3000);
        Elements elements = document.getElementById("div20").getElementsByClass("offcn_puBLoop");

        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()){
            Element element = iterator.next();
            Element node = element.getElementsByClass("offcn_teainTrud").first();
            // 头像
            String img = element.getElementsByTag("img").first().attr("src");
            //姓名
            String name = node.getElementsByTag("h3").first().text();
            // 职位
            String teacherRankName = node.getElementsByClass("offcn_teazhiwei").first().text();
            // 简介
            String intrudction = node.getElementsByClass("offcn_abotst").first().text();
            System.out.println(img+":"+name+":"+teacherRankName+":"+intrudction);

            Teacher teacher = new Teacher();
            teacher.setIsEnabled(true);
            teacher.setAvatar(img);
            teacher.setCourses(new HashSet<>());
            teacher.setIntroduction(intrudction);
            teacher.setName(name);
            teacher.setTeacherRank(createTeacherRank(teacherRankName));
            teacherService.save(teacher);

        }

        System.out.println("====================================");
    }

    private TeacherRank createTeacherRank(String teacherRankName){
        TeacherRank teacherRank = teacherRankService.findByName(teacherRankName);
        if(teacherRank==null){
            teacherRank = new TeacherRank();
            teacherRank.setIsDefault(false);
            teacherRank.setIsEnabled(true);
            teacherRank.setTeachers(new HashSet<>());
            teacherRank.setName(teacherRankName);
            teacherRankService.save(teacherRank);
        }
        return teacherRank;
    }

    private void initCourse(List<Teacher> teachers){
        List<Course> courses = courseService.findAll();
        for (Course course:courses) {
            course.setTeacher(teachers.get(new Long(course.getId()%teachers.size()).intValue()));
            for (Chapter chapter:course.getChapters()) {
                chapter.setTeacher(course.getTeacher());
                for (Lesson lesson:chapter.getLessons()) {
                    lesson.setTeacher(chapter.getTeacher());
                }
            }
            System.out.println("++++++++++++++++++++++++++++++++++++++++"+course.getId());
            courseService.update(course);
        }
    }
}
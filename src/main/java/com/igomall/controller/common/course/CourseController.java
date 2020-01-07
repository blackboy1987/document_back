package com.igomall.controller.common.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.FileType;
import com.igomall.common.Message;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.Sn;
import com.igomall.entity.course.*;
import com.igomall.entity.member.Member;
import com.igomall.security.CurrentUser;
import com.igomall.service.FileService;
import com.igomall.service.SnService;
import com.igomall.service.course.*;
import com.igomall.service.member.CourseFavoriteService;
import com.igomall.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

@RestController("apiCourseController")
@RequestMapping("/api/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseCommentService courseCommentService;
    @Autowired
    private CourseCategoryService courseCategoryService;
    @Autowired
    private SnService snService;

    @Autowired
    private FileService fileService;

    @Autowired
    private CourseFavoriteService courseFavoriteService;
    @Autowired
    private ChapterService chapterService;

    @PostMapping("/info")
    public Map<String,Object> info(String sn, @CurrentUser Member member){
        Map<String,Object> data = new HashMap<>();
        Course course = courseService.findBySn(sn);
        Map<String,Object> courseMap = new HashMap<>();
        if(courseFavoriteService.exists(member, course)){
            courseMap.put("collection",true);
        }else {
            courseMap.put("collection",false);
        }
        courseMap.put("title",course.getTitle());
        courseMap.put("sn",course.getSn());
        courseMap.put("description",course.getDescription());
        courseMap.put("price",course.getPrice());
        courseMap.put("teacher",course.getTeacher().getName());
        Set<CourseTag> courseTags = course.getCourseTags();
        List<String> tags = new ArrayList<>();
        for (CourseTag courseTag:courseTags) {
            tags.add(courseTag.getName());
        }
        courseMap.put("tags",tags);
        courseMap.put("image",course.getImage());
        // 课程信息
        data.put("course",courseMap);
        // 视频列表
        data.put("lessons",lessonService.findListByCourseSQL(course));
        // 老师信息
        data.put("teacher",teacherService.findBySQL(course.getTeacher()));
        // 评论信息
        data.put("courseComments",courseCommentService.findListBySQL(course));
        return data;
    }

    /**
     * 课程列表
     * @param pageable
     *      分页属性
     * @return
     *      课程列表
     */
    @PostMapping("/list")
    @JsonView(Course.WebView.class)
    public Message list(Pageable pageable,Long courseCategoryId,Boolean isVip){
        CourseCategory courseCategory = courseCategoryService.find(courseCategoryId);


       return Message.success1("操作成功",courseService.findPage(courseCategory,isVip,pageable));
    }

    @PostMapping("/create")
    public Message create(){
        String courseTitle = "李南江亲授-jQuery+Ajax从放弃到知根知底";
        String path = "D:\\Program Files\\JiJiDown\\Download\\jquery\\李南江亲授-jQuery+Ajax从放弃到知根知底";
        File parent = new File(path);
        File[] files = parent.listFiles();
        System.out.println(files.length);
        Course course = new Course();
        course.setTitle(courseTitle);
        course.setMemo(courseTitle);
        course.setDescription(courseTitle);
        course.setOrder(0);
        course.setSn(snService.generate(Sn.Type.course));
        course.setCourseCategory(courseCategoryService.find(12L));
        course.setScoreCount(5L);
        course.setScore(3.5f);
        course.setTotalScore(9L);
        course.setPrice(new BigDecimal(9.99));
        course.setHits(0L);
        course.setIsActive(true);
        course.setIsList(true);
        course.setIsMarketable(true);
        course.setIsTop(true);
        course.setLessons(new HashSet<>());
        course.setTeacher(teacherService.find(1L));
        course.setChapters(new HashSet<>());
        course.setImage("image");
        course.setMonthHits(0L);
        course.setMonthHitsDate(new Date());
        course.setWeekHits(0L);
        course.setWeekHitsDate(new Date());
        course.setMonthSales(0L);
        course.setMonthSalesDate(new Date());
        course.setWeekSales(0L);
        course.setWeekSalesDate(new Date());
        course.setSales(0L);
        courseService.save(course);
        Chapter chapter = new Chapter();
        chapter.setTeacher(course.getTeacher());
        chapter.setOrder(1);
        chapter.setTitle(courseTitle);
        chapter.setLessons(new HashSet<>());
        chapter.setCourse(course);
        chapter.setSn(snService.generate(Sn.Type.chapter));
        chapter.setDescription(courseTitle);
        chapterService.save(chapter);
        for (Integer i=1;i<=files.length;i++) {
            String path1 = fileService.upload(FileType.media,files[i-1],i);
            Lesson lesson = new Lesson();
            lesson.setOrder(i);
            lesson.setTeacher(course.getTeacher());
            lesson.setVideoUrl(path1);
            lesson.setVideoImage(course.getImage());
            lesson.setCourse(course);
            lesson.setChapter(chapter);
            String[] titles = files[i-1].getName().replaceAll("\\(Av22807707,P"+i+"\\)","").split("\\.");
            String title = titles[1].split("-")[1];
            lesson.setTitle(title);
            lesson.setSn(snService.generate(Sn.Type.lesson));
            lesson.setDescription(lesson.getTitle());
            lessonService.save(lesson);
        }

        return Message.success("aa");
    }

    public static void main(String[] args) {
        new CourseController().create();
    }
}

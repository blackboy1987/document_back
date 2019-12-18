package com.igomall.job;

import com.igomall.entity.Sn;
import com.igomall.entity.course.Chapter;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseCategory;
import com.igomall.entity.course.Lesson;
import com.igomall.service.SnService;
import com.igomall.service.course.CourseCategoryService;
import com.igomall.service.course.CourseService;
import com.igomall.service.teacher.TeacherService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

// @Component
// @EnableScheduling
public class Demo {

    public static List<String> imageUrls = new ArrayList<>();

    @Autowired
    private CourseCategoryService courseCategoryService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SnService snService;

    static {
        try {
            getImageUrls();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 每隔1钟创建一个视频
     */
    // @Scheduled(fixedRate =1000*60*2)
    public void task() throws Exception{
        List<CourseCategory> courseCategories =  courseCategoryService.findAll();
        Integer index = 0;
        for (CourseCategory courseCategory:courseCategories) {
            if(courseCategory.getGrade()==2){// 根菜单
                Course course = new Course();
                course.setCourseCategory(courseCategory);
                course.setSn(snService.generate(Sn.Type.course));
                course.setTeacher(teacherService.find(1L));
                course.setChapters(new HashSet<>());
                course.setLessons(new HashSet<>());
                course.setOrder(index++);
                course.setDescription(courseCategory.getName()+"_第"+course.getOrder()+"套课程描述");
                course.setImage(imageUrls.get(new Random().nextInt(imageUrls.size())));
                course.setMemo(courseCategory.getName()+"_第"+course.getOrder()+"套课程简介");
                course.setTitle(courseCategory.getName()+"_第"+course.getOrder()+"套课程标题");
                createChapter(course);
                courseService.save(course);
                Thread.sleep(1000);
            }
        }
    }

    public void createChapter(Course course){
        Integer count = new Random().nextInt(10);
        if(count>0){
            for (Integer i=1;i<count;i++) {
                Chapter chapter = new Chapter();
                chapter.setSn(snService.generate(Sn.Type.chapter));
                chapter.setCourse(course);
                chapter.setTeacher(course.getTeacher());
                chapter.setLessons(new HashSet<>());
                chapter.setTitle("第"+i+"章节标题");
                chapter.setOrder(i);
                createLesson(chapter,course);
                course.getChapters().add(chapter);
            }
        }
    }

    public void createLesson(Chapter chapter, Course course){
        Integer count = new Random().nextInt(10);
        if(count>0){
            for (Integer i=1;i<count;i++) {
                Lesson lesson = new Lesson();
                lesson.setSn(snService.generate(Sn.Type.chapter));
                lesson.setTeacher(course.getTeacher());
                lesson.setChapter(chapter);
                lesson.setCourse(course);
                lesson.setOrder(i);
                lesson.setTitle(chapter.getTitle()+"  第"+i+" 节视频");
                lesson.setVideoImage(imageUrls.get(new Random().nextInt(imageUrls.size())));
                lesson.setVideoUrl(chapter.getTitle()+"  第"+i+" 节视频地址");
                chapter.getLessons().add(lesson);
                course.getLessons().add(lesson);
            }
        }
    }


    private static void getImageUrls() throws Exception{
        for (int i=1;i<=101;i++){
            Document document = Jsoup.parse(new URL("https://www.jikexueyuan.com/course/?pageNum="+i),2000);
            Element element = document.getElementById("changeid");
            Elements elements = element.getElementsByTag("img");
            Iterator<Element> iterator = elements.iterator();
            while (iterator.hasNext()){
                Element elements1 = iterator.next();
                imageUrls.add(elements1.attr("src"));
            }
        }
    }
}

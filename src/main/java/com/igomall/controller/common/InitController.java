
package com.igomall.controller.common;

import com.igomall.service.course.CourseService;
import com.igomall.service.course.CourseTagService;
import com.igomall.service.teacher.TeacherRankService;
import com.igomall.service.teacher.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.Iterator;

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
    @Autowired
    private CourseTagService courseTagService;

    @GetMapping("/init")
    public String init() throws Exception{
        initCourseQQ();
        return "ok";
    }


    public static void initCourseQQ() throws Exception{
        for (int i=1;i<=1;i++){
            Document document = Jsoup.parse(new URL("https://ke.qq.com/course/list?page="+i),2000);
            Element element = document.getElementsByClass("course-card-list").first();
            Iterator<Element> iterator = element.getElementsByClass("js-course-card-item").iterator();
            while (iterator.hasNext()){
                Element node = iterator.next();
                Element img = node.getElementsByTag("img").first();
                //封面图
                String image = "https:"+img.attr("src");
                // 课程标题
                String title = img.attr("alt");
                //课程标签
                Iterator<Element> iterator1 =  node.getElementsByClass("item-taglist-tag-link").iterator();
                while (iterator1.hasNext()){
                    Element tag = iterator.next();
                    String tagName = tag.text();
                    if(StringUtils.isNotEmpty(tagName)){

                    }
                }

                System.out.println(image);
            }

        }
    }

    public static void main(String[] args) throws Exception{
        initCourseQQ();
    }
}
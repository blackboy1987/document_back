package com.igomall.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.igomall.entity.course.CourseTag;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import com.igomall.util.bilibili.Data;
import com.igomall.util.bilibili.PageListResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public final class BilibiliUtils {
    public static void main(String[] args) {
        Course course = getCourseInfo("BV1Rx411876f");
        System.out.println(course);
    }


    public static Course getCourseInfo(String bid){
        String url = "https://www.bilibili.com/video/"+bid;
        Course course = new Course();
        try {
            Document document = Jsoup.parse(new URL(url),2000);
            String title = document.getElementById("viewbox_report").getElementsByTag("h1").first().text();
            course.setTitle(title);
            course.setStatus(2);
            course.setProps(new HashMap<>());
            course.getProps().put("bid",bid);
            Elements elements = document.getElementById("v_tag").getElementsByTag("li");
            String tagName = elements.text();
            String[] tagNames = tagName.split(" ");
            for (String tag:tagNames) {
                CourseTag courseTag = new CourseTag();
                courseTag.setName(tag);
                courseTag.setMemo(tag);
                course.getCourseTags().add(courseTag);
            }
            System.out.println(title);
        }catch (Exception e){
            e.printStackTrace();
        }
        course.setLessons(new HashSet<>(getCourseLesson(course,bid)));
        return course;
    }


    public static List<Lesson> getCourseLesson(Course course,String bid){
        String url = "https://api.bilibili.com/x/player/pagelist?bvid="+bid;
        List<Lesson> lessons = new ArrayList<>();
        String result = WebUtils.get(url,null);
        PageListResponse pageListResponse = JsonUtils.toObject(result, new TypeReference<PageListResponse>() {});
        for (Data data:pageListResponse.getData()) {
            Lesson lesson = new Lesson();
            lesson.setTitle(data.getPart());
            Lesson.PlayUrl playUrl = new Lesson.PlayUrl();
            playUrl.setName("哔哩哔哩");
            playUrl.setOrder(1);;
            playUrl.setUrl("https://www.bilibili.com/video/"+bid+"?p="+data.getPage());
            lesson.getPlayUrls().add(playUrl);
            lesson.getProps().put("duration",data.getDuration()+"");
            lesson.getProps().put("cid",data.getCid()+"");
            lesson.setCourse(course);
            lessons.add(lesson);
        }
        return lessons;


    }
}

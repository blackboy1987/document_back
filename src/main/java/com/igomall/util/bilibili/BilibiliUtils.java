package com.igomall.util.bilibili;

import com.fasterxml.jackson.core.type.TypeReference;
import com.igomall.entity.course.CourseTag;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import com.igomall.util.JsonUtils;
import com.igomall.util.WebUtils;
import com.igomall.util.bilibili.AidResponse;
import com.igomall.util.bilibili.Page;
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
        Course course = getCourseInfo("BV1xx411b7WY");
        System.out.println(course);
    }

    public static void getTags(Course course,String bid){
        String url = "https://www.bilibili.com/video/"+bid;
        try {
            Document document = Jsoup.parse(new URL(url),2000);
            String title = document.getElementById("viewbox_report").getElementsByTag("h1").first().text();
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
    }

    public static Course getCourseInfo(String bid){
        String url = "https://api.bilibili.com/x/web-interface/view?bvid="+bid;
        String result = WebUtils.get(url,null);
        AidResponse aidResponse = JsonUtils.toObject(result, new TypeReference<AidResponse>() {});
        AidResponse.Data data = aidResponse.getData();
        Course course = new Course();
        course.setTitle(data.getTitle());
        course.setStatus(2);
        course.setProps(new HashMap<>());
        course.getProps().put("bid",bid);
        course.getProps().put("aid",data.getAid()+"");
        List<Lesson> lessons = new ArrayList<>();
        for (Page page :data.getPages()) {
            Lesson lesson = new Lesson();
            lesson.setTitle(page.getPart());
            lesson.setOrder(page.getPage());
            Lesson.PlayUrl playUrl = new Lesson.PlayUrl();
            playUrl.setName("哔哩哔哩");
            playUrl.setOrder(1);;
            playUrl.setUrl("https://www.bilibili.com/video/"+bid+"?p="+ page.getPage());
            lesson.getPlayUrls().add(playUrl);
            lesson.getProps().put("duration", page.getDuration()+"");
            lesson.getProps().put("cid", page.getCid()+"");
            lesson.setDuration(page.getDuration());
            lesson.setCourse(course);
            lessons.add(lesson);
        }
        course.setLessons(new HashSet<>(lessons));
        getTags(course,bid);
        return course;


    }
}

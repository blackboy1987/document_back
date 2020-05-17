
package com.igomall.service.course.impl;

import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.FolderService;
import com.igomall.service.course.LessonService;
import com.igomall.service.impl.BaseServiceImpl;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service - 管理员
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class CourseServiceImpl extends BaseServiceImpl<Course, Long> implements CourseService {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private FolderService folderService;
    @Autowired
    private LessonService lessonService;


    @Override
    @Transactional
    @CacheEvict(value = "course", allEntries = true)
    public Course save(Course course) {
        Assert.notNull(course,"");
        return super.save(course);
    }

    @Override
    @Transactional
    @CacheEvict(value = "folder", allEntries = true)
    public Course update(Course course) {
        Assert.notNull(course,"");
        return super.update(course);
    }

    @Override
    @Transactional
    @CacheEvict(value = "course", allEntries = true)
    public Course update(Course course, String... ignoreProperties) {
        return super.update(course, ignoreProperties);
    }

    @Override
    @Transactional
    @CacheEvict(value = "course", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "course", allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @Transactional
    @CacheEvict(value = "course", allEntries = true)
    public void delete(Course course) {
        super.delete(course);
    }


    @Override
    public List<Map<String,Object>> findAllBySql(){
        List<Map<String,Object>> courses = new ArrayList<>();
        Ehcache cache = cacheManager.getEhcache("course");
        try {
            Element element = cache.get("courseList");
            if (element != null) {
                courses = (List<Map<String,Object>>) element.getObjectValue();
            } else {
                courses = jdbcTemplate.queryForList(Course.QUERY_ALL);
            }
            cache.put(new Element("courseList", courses));
        }catch (Exception e){
            e.printStackTrace();
        }
        return courses;
    }


    @Override
    @Async
    public void addCache(){
        Ehcache cache = cacheManager.getEhcache("course");
        List<Map<String,Object>> courses = findAllBySql();
        cache.put(new Element("courseList",courses));
        for (Map<String,Object> map:courses) {
            List<Map<String,Object>> folders = folderService.findAllBySql(Long.valueOf(map.get("id").toString()));
            if(folders!=null && !folders.isEmpty()){
                cache.put(new Element("folder_courseId_"+map.get("id"),folders));
                for (Map<String,Object> folder:folders) {
                    List<Map<String,Object>> lessons = lessonService.findAllBySql(Long.valueOf(map.get("id").toString()),Long.valueOf(folder.get("id").toString()));
                    cache.put(new Element("lesson_courseId_"+map.get("id")+"_folderId_"+folder.get("id"),lessons));
                    for (Map<String,Object> lesson:lessons) {
                        Lesson lesson1 = lessonService.find(Long.parseLong(lesson.get("id").toString()));
                        if(lesson1!=null){
                            cache.put(new Element("playUrl_"+lesson1.getId(),lesson1.getPlayUrls()));
                        }
                    }
                }
            }else{
                List<Map<String,Object>> lessons = lessonService.findAllBySql(Long.valueOf(map.get("id").toString()),null);
                cache.put(new Element("lesson_courseId_"+map.get("id"),lessons));
                for (Map<String,Object> lesson:lessons) {
                    Lesson lesson1 = lessonService.find(Long.parseLong(lesson.get("id").toString()));
                    if(lesson1!=null){
                        cache.put(new Element("playUrl_"+lesson1.getId(),lesson1.getPlayUrls()));
                    }
                }
            }
        }
    }

    @Override
    public void removeCache(){
        cacheManager.removeCache("course");
    }

}
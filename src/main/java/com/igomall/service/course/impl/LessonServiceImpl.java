
package com.igomall.service.course.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.course.LessonDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Folder;
import com.igomall.entity.course.Lesson;
import com.igomall.service.course.LessonService;
import com.igomall.service.impl.BaseServiceImpl;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class LessonServiceImpl extends BaseServiceImpl<Lesson, Long> implements LessonService {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private LessonDao lessonDao;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "lesson")
    public List<Lesson> findList(Course course, Folder folder, Integer count, List<Filter> filters, List<Order> orders) {
        return lessonDao.findList(course,folder,count,filters,orders);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lesson", allEntries = true)
    public Lesson save(Lesson lesson) {
        Assert.notNull(lesson,"");
        return super.save(lesson);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lesson", allEntries = true)
    public Lesson update(Lesson lesson) {
        Assert.notNull(lesson,"");
        return super.update(lesson);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lesson", allEntries = true)
    public Lesson update(Lesson lesson, String... ignoreProperties) {
        return super.update(lesson, ignoreProperties);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lesson", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lesson", allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lesson", allEntries = true)
    public void delete(Lesson lesson) {
        super.delete(lesson);
    }

    @Override
    public List<Map<String,Object>> findAllBySql(){
        return jdbcTemplate.queryForList(Lesson.QUERY_ALL);
    }

    @Override
    public List<Map<String,Object>> findAllBySql(Long courseId,Long folderId){

        List<Map<String,Object>> lessons = new ArrayList<>();
        Ehcache cache = cacheManager.getEhcache("course");
        if(folderId!=null&&folderId==0){
            folderId = null;
        }
        if(courseId!=null&&courseId==0){
            courseId = null;
        }

        if(courseId!=null&&folderId!=null){
            try {
                Element element = cache.get("lesson_courseId_"+courseId+"_folderId_"+folderId);
                if (element != null) {
                    lessons = (List<Map<String,Object>>) element.getObjectValue();
                } else {
                    lessons = jdbcTemplate.queryForList(Lesson.QUERY_ALL_COURSE.replace("courseId",courseId+"").replace("folderId",folderId+""));
                }
                cache.put(new Element("lesson_courseId_"+courseId+"_folderId_"+folderId, lessons));
            }catch (Exception e){
                e.printStackTrace();
            }
            return lessons;
        }
        if(courseId==null){
            try {
                Element element = cache.get("lesson_folderId_"+folderId);
                if (element != null) {
                    lessons = (List<Map<String,Object>>) element.getObjectValue();
                } else {
                    lessons = jdbcTemplate.queryForList(Lesson.QUERY_ALL_COURSE.replace("folderId",folderId+""));
                }
                cache.put(new Element("lesson_folderId_"+folderId, lessons));
            }catch (Exception e){
                e.printStackTrace();
            }
            return lessons;
        }
        try {
            Element element = cache.get("lesson_courseId_"+courseId);
            if (element != null) {
                lessons = (List<Map<String,Object>>) element.getObjectValue();
            } else {
                lessons = jdbcTemplate.queryForList(Lesson.QUERY_ALL_COURSE.replace("courseId",courseId+""));
            }
            cache.put(new Element("lesson_courseId_"+courseId, lessons));
        }catch (Exception e){
            e.printStackTrace();
        }
        return lessons;

    }
}
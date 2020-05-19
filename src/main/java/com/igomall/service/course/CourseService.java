
package com.igomall.service.course;

import com.igomall.entity.course.Course;
import com.igomall.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Service - 管理员
 * 
 * @author blackboy
 * @version 1.0
 */
public interface CourseService extends BaseService<Course, Long> {

    void addCache();

    void removeCache();

    List<Map<String,Object>> findAllBySql();

    /**
     * 查看点击数
     *
     * @param id
     *            ID
     * @return 点击数
     */
    long viewHits(Long id);
}
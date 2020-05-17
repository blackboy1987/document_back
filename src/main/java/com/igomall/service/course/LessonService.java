
package com.igomall.service.course;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Folder;
import com.igomall.entity.course.Lesson;
import com.igomall.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Service - 管理员
 * 
 * @author blackboy
 * @version 1.0
 */
public interface LessonService extends BaseService<Lesson, Long> {

    List<Lesson> findList(Course course, Folder folder, Integer count, List<Filter> filters, List<Order> orders);

    List<Map<String,Object>> findAllBySql();
    List<Map<String,Object>> findAllBySql(Long courseId,Long folderId);
}
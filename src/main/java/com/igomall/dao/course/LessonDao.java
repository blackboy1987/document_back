package com.igomall.dao.course;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.BaseDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Folder;
import com.igomall.entity.course.Lesson;

import java.util.List;

public interface LessonDao extends BaseDao<Lesson,Long> {

    List<Lesson> findList(Course course, Folder folder, Integer count, List<Filter> filters, List<Order> orders);

}

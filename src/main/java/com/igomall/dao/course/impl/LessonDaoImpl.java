
package com.igomall.dao.course.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.course.LessonDao;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Folder;
import com.igomall.entity.course.Lesson;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Dao - 管理员
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class LessonDaoImpl extends BaseDaoImpl<Lesson, Long> implements LessonDao {

    @Override
    public List<Lesson> findList(Course course, Folder folder, Integer count, List<Filter> filters, List<Order> orders) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Lesson> criteriaQuery = criteriaBuilder.createQuery(Lesson.class);
        Root<Lesson> root = criteriaQuery.from(Lesson.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (course!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("course"), course));
        }
        if (folder!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("folder"), folder));
        }

        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery,null,count,filters,orders);
    }
}
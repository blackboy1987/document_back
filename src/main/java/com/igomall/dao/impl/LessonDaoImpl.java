package com.igomall.dao.impl;

import com.igomall.dao.LessonDao;
import com.igomall.entity.Chapter;
import com.igomall.entity.Course;
import com.igomall.entity.Lesson;
import com.igomall.entity.Part;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class LessonDaoImpl extends BaseDaoImpl<Lesson,Long> implements LessonDao {

    @Override
    public List<Lesson> findList(Course course, Part part,Chapter chapter){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Lesson> criteriaQuery = criteriaBuilder.createQuery(Lesson.class);
        Root<Lesson> root = criteriaQuery.from(Lesson.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (course!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("course"), course));
        }
        if (part!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("part"), part));
        }
        if (chapter!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("chapter"), chapter));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery);
    }
}

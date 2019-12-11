package com.igomall.dao.course.impl;

import com.igomall.dao.course.ChapterDao;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.entity.course.Chapter;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Part;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ChapterDaoImpl extends BaseDaoImpl<Chapter,Long> implements ChapterDao {

    public List<Chapter> findList(Course course,Part part){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Chapter> criteriaQuery = criteriaBuilder.createQuery(Chapter.class);
        Root<Chapter> root = criteriaQuery.from(Chapter.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (course!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("course"), course));
        }
        if (part!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("part"), part));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery);
    }
}

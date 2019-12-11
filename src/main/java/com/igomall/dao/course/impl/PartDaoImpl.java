package com.igomall.dao.course.impl;

import com.igomall.dao.course.PartDao;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Part;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PartDaoImpl extends BaseDaoImpl<Part,Long> implements PartDao {

    public List<Part> findList(Course course){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Part> criteriaQuery = criteriaBuilder.createQuery(Part.class);
        Root<Part> root = criteriaQuery.from(Part.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (course!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("course"), course));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery);
    }
}

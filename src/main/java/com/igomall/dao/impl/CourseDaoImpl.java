package com.igomall.dao.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.CourseDao;
import com.igomall.entity.Course;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

@Repository
public class CourseDaoImpl extends BaseDaoImpl<Course,Long> implements CourseDao {

    public Page<Course> findPage(Pageable pageable, String title, String description, Date beginDate, Date endDate){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> root = criteriaQuery.from(Course.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (StringUtils.isNotEmpty(title)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("title"), "%"+title+"%"));
        }
        if (StringUtils.isNotEmpty(description)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("username"), "%"+description+"%"));
        }
        if (beginDate!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), beginDate));
        }
        if (endDate!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDate));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery,pageable);
    }
}

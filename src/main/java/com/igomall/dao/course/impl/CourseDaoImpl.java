package com.igomall.dao.course.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.course.CourseDao;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseCategory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    @Override
    public Page<Course> findPage(CourseCategory courseCategory, Boolean isVip, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> root = criteriaQuery.from(Course.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (courseCategory != null) {
            Subquery<CourseCategory> subquery = criteriaQuery.subquery(CourseCategory.class);
            Root<CourseCategory> subqueryRoot = subquery.from(CourseCategory.class);
            subquery.select(subqueryRoot);
            subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, courseCategory), criteriaBuilder.like(subqueryRoot.get("treePath"), "%" + CourseCategory.TREE_PATH_SEPARATOR + courseCategory.getId() + CourseCategory.TREE_PATH_SEPARATOR + "%")));
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("courseCategory")).value(subquery));
        }
        if (isVip!=null) {
            if(isVip){
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.get("price"), BigDecimal.ZERO));
            }else{
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("price"), BigDecimal.ZERO));
            }
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    @Override
    public List<Course> findList(CourseCategory courseCategory, Integer first, Integer count, List<Filter> filters, List<Order> orders) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> root = criteriaQuery.from(Course.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (courseCategory != null) {
            Subquery<CourseCategory> subquery = criteriaQuery.subquery(CourseCategory.class);
            Root<CourseCategory> subqueryRoot = subquery.from(CourseCategory.class);
            subquery.select(subqueryRoot);
            subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, courseCategory), criteriaBuilder.like(subqueryRoot.get("treePath"), "%" + CourseCategory.TREE_PATH_SEPARATOR + courseCategory.getId() + CourseCategory.TREE_PATH_SEPARATOR + "%")));
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("courseCategory")).value(subquery));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery,first,count,filters,orders);
    }
}

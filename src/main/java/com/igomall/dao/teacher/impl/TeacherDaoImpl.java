package com.igomall.dao.teacher.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.teacher.TeacherDao;
import com.igomall.entity.teacher.Teacher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TeacherDaoImpl extends BaseDaoImpl<Teacher,Long> implements TeacherDao {

    @Override
    public Page<Teacher> findPage(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Teacher> criteriaQuery = criteriaBuilder.createQuery(Teacher.class);
        Root<Teacher> root = criteriaQuery.from(Teacher.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (StringUtils.isNotEmpty(name)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("name"), "%"+name+"%"));
        }
        if (isEnabled!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"), isEnabled));
        }
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDate));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    @Override
    public Map<String, Object> findBySQL(Teacher teacher) {
        if(teacher==null){
            return new HashMap<>();
        }
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select");
            // 教师姓名
            sb.append(" teacher.name,");
            // 教师简介
            sb.append(" teacher.introduction,");
            // 教师头像
            sb.append(" teacher.avatar,");
            // 教师等级名称
            sb.append("  teacherRank.`name` teacherRankName,");
            // 教师发布课程数
            sb.append(" (SELECT count(1) from edu_course where teacher_id=teacher.id) courseCount");
            sb.append(" from");
            sb.append(" edu_teacher as teacher,");
            sb.append(" edu_teacher_rank as teacherRank");
            sb.append(" where 1=1");
            sb.append(" and teacher.id="+teacher.getId());
            sb.append(" and teacherRank.id=teacher.teacher_rank_id");

            return jdbcTemplate.queryForMap(sb.toString());
        }catch (Exception e){
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}

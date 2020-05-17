
package com.igomall.dao.wechat.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.wechat.ProjectItemErrorDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.wechat.ProjectItemError;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Dao - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class ProjectItemErrorDaoImpl extends BaseDaoImpl<ProjectItemError, Long> implements ProjectItemErrorDao {
    public Page<ProjectItemError> findPage(Member member, Pageable pageable) {
        if (member == null) {
            return Page.emptyPage(pageable);
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProjectItemError> criteriaQuery = criteriaBuilder.createQuery(ProjectItemError.class);
        Root<ProjectItemError> root = criteriaQuery.from(ProjectItemError.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
        return super.findPage(criteriaQuery, pageable);
    }

}
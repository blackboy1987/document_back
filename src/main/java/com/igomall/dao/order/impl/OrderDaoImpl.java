
package com.igomall.dao.order.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.AdminDao;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.order.OrderDao;
import com.igomall.entity.Admin;
import com.igomall.entity.Department;
import com.igomall.entity.member.Member;
import com.igomall.entity.order.Order;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Dao - 管理员
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order, Long> implements OrderDao {

    public Page<Order> findPage(Pageable pageable, Member member, Date beginDate, Date endDate){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (member!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
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
}
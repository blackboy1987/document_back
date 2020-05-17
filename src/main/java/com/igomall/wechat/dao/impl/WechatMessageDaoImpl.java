
package com.igomall.wechat.dao.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.wechat.dao.WechatMessageDao;
import com.igomall.wechat.entity.WeChatMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Dao - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Repository
public class WechatMessageDaoImpl extends BaseDaoImpl<WeChatMessage, Long> implements WechatMessageDao {

    @Override
    public Page<WeChatMessage> findPage(Pageable pageable, String content, String toUserName, String fromUserName, String msgType, Date beginDate, Date endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WeChatMessage> criteriaQuery = criteriaBuilder.createQuery(WeChatMessage.class);
        Root<WeChatMessage> root = criteriaQuery.from(WeChatMessage.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (StringUtils.isNotEmpty(content)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("content"), "%"+content+"%"));
        }
        if (StringUtils.isNotEmpty(toUserName)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("toUserName"), toUserName));
        }
        if (StringUtils.isNotEmpty(fromUserName)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("fromUserName"), fromUserName));
        }
        if (StringUtils.isNotEmpty(msgType)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("msgType"), msgType));
        }
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery,pageable);
    }
}
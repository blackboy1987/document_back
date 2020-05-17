
package com.igomall.dao.member.impl;

import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.member.LessonReadRecordDao;
import com.igomall.entity.member.LessonReadRecord;
import com.igomall.util.Date8Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import java.util.Date;

/**
 * Dao - 会员注册项
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Repository
public class LessonReadRecordDaoImpl extends BaseDaoImpl<LessonReadRecord, Long> implements LessonReadRecordDao {

    public boolean existToday(Long lessonId,Long memberId,String playUrlName,String playUrlUrl){
        if(lessonId==null || memberId==null || StringUtils.isEmpty(playUrlName) || StringUtils.isEmpty(playUrlUrl)){
            return true;
        }

        String jpql = "select count(*) from LessonReadRecord lessonReadRecord where lessonReadRecord.memberId = :memberId and lessonReadRecord.lessonId = :lessonId and lessonReadRecord.createdDate >= :beginDate and lessonReadRecord.createdDate <= :endDate and lower(lessonReadRecord.playUrlName) = lower(:playUrlName) and  lower(lessonReadRecord.playUrlUrl) = lower(:playUrlUrl)";
        Date beginDate = Date8Utils.getBeginDay(new Date());
        Date endDate = Date8Utils.getEndDay(new Date());
        Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
                .setParameter("playUrlName", playUrlName)
                .setParameter("playUrlUrl", playUrlUrl)
                .setParameter("memberId", memberId)
                .setParameter("lessonId", lessonId)
                .setParameter("beginDate", beginDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
        return count > 0;
    }


}
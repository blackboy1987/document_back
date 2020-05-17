
package com.igomall.dao.member;

import com.igomall.dao.BaseDao;
import com.igomall.entity.member.LessonReadRecord;

/**
 * Dao - 消息
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface LessonReadRecordDao extends BaseDao<LessonReadRecord, Long> {

    boolean existToday(Long lessonId,Long memberId,String playUrlName,String playUrlUrl);

}

package com.igomall.service.member;

import com.igomall.entity.member.LessonReadRecord;
import com.igomall.service.BaseService;

/**
 * Service - 会员注册项
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface LessonReadRecordService extends BaseService<LessonReadRecord, Long> {

    boolean existToday(Long lessonId,Long memberId,String playUrlName,String playUrlUrl);

    LessonReadRecord save (Long lessonId,Long memberId,String playUrlName,String playUrlUrl);
}
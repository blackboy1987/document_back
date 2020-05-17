
package com.igomall.service.member.impl;

import com.igomall.dao.member.LessonReadRecordDao;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.member.LessonReadRecord;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.member.LessonReadRecordService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service - 会员注册项
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class LessonReadRecordServiceImpl extends BaseServiceImpl<LessonReadRecord, Long> implements LessonReadRecordService {

	@Autowired
	private LessonReadRecordDao lessonReadRecordDao;

	@Override
	public boolean existToday(Long lessonId, Long memberId, String playUrlName, String playUrlUrl) {
		return lessonReadRecordDao.existToday(lessonId,memberId,playUrlName,playUrlUrl);
	}

	@Override
	public LessonReadRecord save(Long lessonId, Long memberId, String playUrlName, String playUrlUrl) {
		if(!existToday(lessonId,memberId,playUrlName,playUrlUrl)){
			LessonReadRecord lessonReadRecord = new LessonReadRecord();
			lessonReadRecord.setLessonId(lessonId);
			lessonReadRecord.setMemberId(memberId);
			lessonReadRecord.setPlayUrlName(playUrlName);
			lessonReadRecord.setPlayUrlUrl(playUrlUrl);
			return super.save(lessonReadRecord);
		}
		return null;
	}
}
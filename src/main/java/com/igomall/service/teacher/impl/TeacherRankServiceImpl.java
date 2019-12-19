
package com.igomall.service.teacher.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.teacher.TeacherRankDao;
import com.igomall.entity.teacher.TeacherRank;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.teacher.TeacherRankService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * Service - 会员等级
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class TeacherRankServiceImpl extends BaseServiceImpl<TeacherRank, Long> implements TeacherRankService {

	@Autowired
	private TeacherRankDao teacherRankDao;

	@Transactional(readOnly = true)
	public TeacherRank findDefault() {
		return teacherRankDao.findDefault();
	}

	@Override
	@Transactional
	public TeacherRank save(TeacherRank teacherRank) {
		Assert.notNull(teacherRank,"");

		if (BooleanUtils.isTrue(teacherRank.getIsDefault())) {
			teacherRankDao.clearDefault();
		}
		return super.save(teacherRank);
	}

	@Override
	@Transactional
	public TeacherRank update(TeacherRank teacherRank) {
		Assert.notNull(teacherRank,"");

		TeacherRank pMemberRank = super.update(teacherRank);
		if (BooleanUtils.isTrue(pMemberRank.getIsDefault())) {
			teacherRankDao.clearDefault(pMemberRank);
		}
		return pMemberRank;
	}

	@Override
	public Page<TeacherRank> findPage(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate) {
		return teacherRankDao.findPage(pageable,name,isEnabled,beginDate,endDate);
	}

	@Override
	public TeacherRank findByName(String name) {
		return teacherRankDao.findByName(name);
	}
}
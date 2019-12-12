
package com.igomall.service.teacher;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.teacher.TeacherRank;
import com.igomall.service.BaseService;

import java.util.Date;

/**
 * Service - 会员等级
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface TeacherRankService extends BaseService<TeacherRank, Long> {

	/**
	 * 查找默认会员等级
	 * 
	 * @return 默认会员等级，若不存在则返回null
	 */
	TeacherRank findDefault();

	Page<TeacherRank> findPage(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate);
}
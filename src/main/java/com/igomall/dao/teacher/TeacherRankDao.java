
package com.igomall.dao.teacher;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.teacher.TeacherRank;

import java.util.Date;

/**
 * Dao - 会员等级
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface TeacherRankDao extends BaseDao<TeacherRank, Long> {

	/**
	 * 查找默认会员等级
	 * 
	 * @return 默认会员等级，若不存在则返回null
	 */
	TeacherRank findDefault();

	/**
	 * 清除默认
	 */
	void clearDefault();

	/**
	 * 清除默认
	 * 
	 * @param exclude
	 *            排除会员等级
	 */
	void clearDefault(TeacherRank exclude);

	Page<TeacherRank> findPage(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate);
}
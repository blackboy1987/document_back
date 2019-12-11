
package com.igomall.service.member;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.MemberDepositLog;
import com.igomall.service.BaseService;

/**
 * Service - 会员预存款记录
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface MemberDepositLogService extends BaseService<MemberDepositLog, Long> {

	/**
	 * 查找会员预存款记录分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 会员预存款记录分页
	 */
	Page<MemberDepositLog> findPage(Member member, Pageable pageable);

}
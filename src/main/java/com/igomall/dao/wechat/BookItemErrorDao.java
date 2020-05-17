
package com.igomall.dao.wechat;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.wechat.BookItemError;

/**
 * Dao - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
public interface BookItemErrorDao extends BaseDao<BookItemError, Long> {
    /**
     * 查找积分记录分页
     *
     * @param member
     *            会员
     * @param pageable
     *            分页信息
     * @return 积分记录分页
     */
    Page<BookItemError> findPage(Member member, Pageable pageable);
}
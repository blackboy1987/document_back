
package com.igomall.service.wechat;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.member.Member;
import com.igomall.entity.wechat.ToolItemError;
import com.igomall.service.BaseService;

/**
 * Service - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
public interface ToolItemErrorService extends BaseService<ToolItemError, Long> {

    /**
     * 查找积分记录分页
     *
     * @param member
     *            会员
     * @param pageable
     *            分页信息
     * @return 积分记录分页
     */
    Page<ToolItemError> findPage(Member member, Pageable pageable);
}
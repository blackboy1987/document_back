
package com.igomall.dao.order;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.order.Order;

import java.util.Date;

/**
 * Dao - 管理员
 * 
 * @author blackboy
 * @version 1.0
 */
public interface OrderDao extends BaseDao<Order, Long> {

    Page<Order> findPage(Pageable pageable, Member member, Date beginDate, Date endDate);
}
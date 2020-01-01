
package com.igomall.service.order;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.member.Member;
import com.igomall.entity.order.Order;
import com.igomall.service.BaseService;

import java.util.Date;

/**
 * Service - 管理员
 * 
 * @author blackboy
 * @version 1.0
 */
public interface OrderService extends BaseService<Order, Long> {

	Page<Order> findPage(Pageable pageable, Member member, Date beginDate, Date endDate);

}
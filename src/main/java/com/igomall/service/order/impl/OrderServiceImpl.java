
package com.igomall.service.order.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.order.OrderDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.order.Order;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service - 管理员
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Override
	public Page<Order> findPage(Pageable pageable, Member member, Date beginDate, Date endDate) {
		return orderDao.findPage(pageable,member,beginDate,endDate);
	}
}
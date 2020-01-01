
package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.member.Member;
import com.igomall.entity.order.Order;
import com.igomall.security.CurrentUser;
import com.igomall.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Controller - 会员登录
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("memberOrderController")
@RequestMapping("/member/api/order")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	/**
	 * 登录页面
	 */
	@PostMapping("/list")
	@JsonView(Order.ListView.class)
	public Page<Order> list(Pageable pageable, @CurrentUser Member member, Date beginDate, Date endDate) {
		return orderService.findPage(pageable,member,beginDate,endDate);
	}

}

package com.igomall.entity.order;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - 订单
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
@Table(name = "Orders")
public class Order extends BaseEntity<Long> {

	private static final long serialVersionUID = 8370942500343156156L;


	/**
	 * 编号
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false, unique = true)
	private String sn;


	/**
	 * 会员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Member member;

	/**
	 * 订单项
	 */
	@JsonView(BaseView.class)
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OrderBy("type asc")
	private List<OrderItem> orderItems = new ArrayList<>();

	/**
	 * 获取编号
	 * 
	 * @return 编号
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * 设置编号
	 * 
	 * @param sn
	 *            编号
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员
	 * 
	 * @param member
	 *            会员
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 获取订单项
	 * 
	 * @return 订单项
	 */
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * 设置订单项
	 * 
	 * @param orderItems
	 *            订单项
	 */
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	/**
	 * 获取订单项
	 * 
	 * @param sn
	 *            SKU编号
	 * @return 订单项
	 */
	@Transient
	public OrderItem getOrderItem(String sn) {
		if (StringUtils.isEmpty(sn) || CollectionUtils.isEmpty(getOrderItems())) {
			return null;
		}
		for (OrderItem orderItem : getOrderItems()) {
			if (orderItem != null && StringUtils.equalsIgnoreCase(orderItem.getSn(), sn)) {
				return orderItem;
			}
		}
		return null;
	}
}
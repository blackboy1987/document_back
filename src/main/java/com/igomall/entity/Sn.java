
package com.igomall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity - 序列号
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
@Table(name = "edu_sn")
public class Sn extends BaseEntity<Long> {

	private static final long serialVersionUID = -2330598144835706164L;

	/**
	 * 类型
	 */
	public enum Type {

		/**
		 * 商品
		 */
		course,

		/**
		 * 订单
		 */
		part,

		/**
		 * 订单支付
		 */
		chapter,

		/**
		 * 订单退款
		 */
		lesson,

		/**
		 * 订单发货
		 */
		answer,

		/**
		 * 订单退货
		 */
		orderReturns,

		/**
		 * 支付事务
		 */
		paymentTransaction,

		/**
		 * 平台服务
		 */
		platformService
	}

	/**
	 * 类型
	 */
	@Column(nullable = false, updatable = false, unique = true)
	private Type type;

	/**
	 * 末值
	 */
	@Column(nullable = false)
	private Long lastValue;

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取末值
	 * 
	 * @return 末值
	 */
	public Long getLastValue() {
		return lastValue;
	}

	/**
	 * 设置末值
	 * 
	 * @param lastValue
	 *            末值
	 */
	public void setLastValue(Long lastValue) {
		this.lastValue = lastValue;
	}

}
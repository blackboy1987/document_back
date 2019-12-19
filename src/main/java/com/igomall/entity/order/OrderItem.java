
package com.igomall.entity.order;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.Course;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Entity - 订单项
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
public class OrderItem extends BaseEntity<Long> {

	private static final long serialVersionUID = -4999926022604479334L;

	/**
	 * 编号
	 */
	@Column(nullable = false, updatable = false)
	private String sn;

	/**
	 * 名称
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false)
	private String name;

	/**
	 * 价格
	 */
	@Column(nullable = false, updatable = false, precision = 21, scale = 6)
	private BigDecimal price;

	/**
	 * 缩略图
	 */
	@JsonView(BaseView.class)
	@Column(updatable = false)
	private String thumbnail;

	/**
	 * 数量
	 */
	@Column(nullable = false, updatable = false)
	private Integer quantity;

	/**
	 * Course
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Course course;

	/**
	 * 订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders", nullable = false, updatable = false)
	private Order order;

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
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * 获取价格
	 * 
	 * @return 价格
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置价格
	 * 
	 * @param price
	 *            价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 获取缩略图
	 * 
	 * @return 缩略图
	 */
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * 设置缩略图
	 * 
	 * @param thumbnail
	 *            缩略图
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * 获取数量
	 * 
	 * @return 数量
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置数量
	 * 
	 * @param quantity
	 *            数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 获取Course
	 * 
	 * @return Course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * 设置SKU
	 * 
	 * @param course
	 *            Course
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * 设置订单
	 * 
	 * @param order
	 *            订单
	 */
	public void setOrder(Order order) {
		this.order = order;
	}


	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@Transient
	public BigDecimal getSubtotal() {
		if (getPrice() != null && getQuantity() != null) {
			return getPrice().multiply(new BigDecimal(getQuantity()));
		} else {
			return BigDecimal.ZERO;
		}
	}
}
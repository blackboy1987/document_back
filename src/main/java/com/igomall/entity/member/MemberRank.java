
package com.igomall.entity.member;

import com.igomall.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity - 会员等级
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
@Table(name = "edu_member_rank")
public class MemberRank extends BaseEntity<Long> {

	private static final long serialVersionUID = 3599029355500655209L;

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/**
	 * 优惠比例
	 */
	@NotNull
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	@Column(nullable = false, precision = 12, scale = 6)
	private Double scale;

	/**
	 * 消费金额
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(precision = 21, scale = 6)
	private BigDecimal amount;

	/**
	 * 是否默认
	 */
	@NotNull
	@Column(nullable = false)
	private Boolean isDefault;

	@NotNull
	@Column(nullable = false)
	private Boolean isEnabled;

	/**
	 * 是否特殊
	 */
	@NotNull
	@Column(nullable = false)
	private Boolean isSpecial;

	/**
	 * 会员
	 */
	@OneToMany(mappedBy = "memberRank", fetch = FetchType.LAZY)
	private Set<Member> members = new HashSet<>();

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
	 * 获取优惠比例
	 * 
	 * @return 优惠比例
	 */
	public Double getScale() {
		return scale;
	}

	/**
	 * 设置优惠比例
	 * 
	 * @param scale
	 *            优惠比例
	 */
	public void setScale(Double scale) {
		this.scale = scale;
	}

	/**
	 * 获取消费金额
	 * 
	 * @return 消费金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置消费金额
	 * 
	 * @param amount
	 *            消费金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 获取是否默认
	 * 
	 * @return 是否默认
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}

	/**
	 * 设置是否默认
	 * 
	 * @param isDefault
	 *            是否默认
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * 获取是否特殊
	 * 
	 * @return 是否特殊
	 */
	public Boolean getIsSpecial() {
		return isSpecial;
	}

	/**
	 * 设置是否特殊
	 * 
	 * @param isSpecial
	 *            是否特殊
	 */
	public void setIsSpecial(Boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	public Set<Member> getMembers() {
		return members;
	}

	/**
	 * 设置会员
	 * 
	 * @param members
	 *            会员
	 */
	public void setMembers(Set<Member> members) {
		this.members = members;
	}

}
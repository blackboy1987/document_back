
package com.igomall.entity.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.BaseAttributeConverter;
import com.igomall.entity.*;
import com.igomall.util.JsonUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Entity - 会员
 *
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
@Table(name = "edu_member")
public class Member extends User {

	private static final long serialVersionUID = 1533130686714725835L;

	/**
	 * 权限
	 */
	public static final Set<String> PERMISSIONS = new HashSet<>(Arrays.asList("member"));

	/**
	 * 性别
	 */
	public enum Gender {

		/**
		 * 未知
		 */
		unknown,

		/**
		 * 男
		 */
		male,

		/**
		 * 女
		 */
		female
	}

	/**
	 * 排名类型
	 */
	public enum RankingType {

		/**
		 * 积分
		 */
		point,

		/**
		 * 余额
		 */
		balance,

		/**
		 * 消费金额
		 */
		amount
	}

	/**
	 * "当前用户名"Cookie名称
	 */
	public static final String CURRENT_USERNAME_COOKIE_NAME = "currentMemberUsername";

	/**
	 * 会员注册项值属性个数
	 */
	public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 10;

	/**
	 * 会员注册项值属性名称前缀
	 */
	public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

	/**
	 * 用户名
	 */
	@JsonView(BaseView.class)
	@NotEmpty(groups = Save.class)
	@Length(min = 4, max = 20)
	@Pattern.List({ @Pattern(regexp = "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$"), @Pattern(regexp = "^.*[^\\d].*$") })
	@Column(nullable = false, updatable = false, unique = true)
	private String username;

	/**
	 * 密码
	 */
	@NotEmpty(groups = Save.class)
	@Length(min = 4, max = 20)
	@Transient
	private String password;

	/**
	 * 加密密码
	 */
	@Column(nullable = false)
	private String encodedPassword;

	/**
	 * E-mail
	 */
	@NotEmpty
	@Email
	@Length(max = 200)
	@Column(nullable = false, unique = true)
	private String email;

	/**
	 * 手机
	 */
	@Length(max = 200)
	@Pattern(regexp = "^1[3|4|5|7|8]\\d{9}$")
	@Column(unique = true)
	private String mobile;

	/**
	 * 积分
	 */
	@Column(nullable = false)
	private Long point;

	/**
	 * 积分
	 */
	@Column(nullable = false)
	private Long point1;

	/**
	 * 积分
	 */
	@Column(nullable = false)
	private Long point2;

	/**
	 * 积分
	 */
	@Column(nullable = false)
	private Long point3;

	/**
	 * 余额
	 */
	@Column(nullable = false, precision = 27, scale = 12)
	private BigDecimal balance;

	/**
	 * 余额1
	 */
	@Column(nullable = false, precision = 27, scale = 12)
	private BigDecimal balance1;

	/**
	 * 余额2
	 */
	@Column(nullable = false, precision = 27, scale = 12)
	private BigDecimal balance2;

	/**
	 * 余额3
	 */
	@Column(nullable = false, precision = 27, scale = 12)
	private BigDecimal balance3;

	/**
	 * 消费金额
	 */
	@Column(nullable = false, precision = 27, scale = 12)
	private BigDecimal amount;

	/**
	 * 姓名
	 */
	@Length(max = 200)
	private String name;

	/**
	 * 性别
	 */
	private Gender gender;

	/**
	 * 出生日期
	 */
	private Date birth;

	/**
	 * 地址
	 */
	@Length(max = 200)
	private String address;

	/**
	 * 邮编
	 */
	@Length(max = 200)
	private String zipCode;

	/**
	 * 电话
	 */
	@Length(max = 200)
	private String phone;

	/**
	 * 会员注册项值0
	 */
	@Length(max = 200)
	private String attributeValue0;

	/**
	 * 会员注册项值1
	 */
	@Length(max = 200)
	private String attributeValue1;

	/**
	 * 会员注册项值2
	 */
	@Length(max = 200)
	private String attributeValue2;

	/**
	 * 会员注册项值3
	 */
	@Length(max = 200)
	private String attributeValue3;

	/**
	 * 会员注册项值4
	 */
	@Length(max = 200)
	private String attributeValue4;

	/**
	 * 会员注册项值5
	 */
	@Length(max = 200)
	private String attributeValue5;

	/**
	 * 会员注册项值6
	 */
	@Length(max = 200)
	private String attributeValue6;

	/**
	 * 会员注册项值7
	 */
	@Length(max = 200)
	private String attributeValue7;

	/**
	 * 会员注册项值8
	 */
	@Length(max = 200)
	private String attributeValue8;

	/**
	 * 会员注册项值9
	 */
	@Length(max = 200)
	private String attributeValue9;

	/**
	 * 安全密匙
	 */
	@Embedded
	private SafeKey safeKey;

	/**
	 * 会员等级
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private MemberRank memberRank;

	/**
	 * 头像
	 */
	private String avatar;


	private String signature;

	private String job;

	private String school;

	private String major;


	@Column(nullable = false, length = 4000)
	@Convert(converter = TagConverter.class)
	private List<String> tags = new ArrayList<>();

	/**
	 * 会员预存款记录
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<MemberDepositLog> memberDepositLogs = new HashSet<>();

	/**
	 * 接收的消息
	 */
	@OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<Message> inMessages = new HashSet<>();

	/**
	 * 发送的消息
	 */
	@OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<Message> outMessages = new HashSet<>();

	/**
	 * 积分记录
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<PointLog> pointLogs = new HashSet<>();

	/**
	 * 获取用户名
	 *
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 *
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取密码
	 *
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 *
	 * @param password
	 *            密码
	 */
	public void setPassword(String password) {
		this.password = password;
		if (password != null) {
			setEncodedPassword(DigestUtils.md5Hex(password));
		}
	}

	/**
	 * 获取加密密码
	 *
	 * @return 加密密码
	 */
	public String getEncodedPassword() {
		return encodedPassword;
	}

	/**
	 * 设置加密密码
	 *
	 * @param encodedPassword
	 *            加密密码
	 */
	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}

	/**
	 * 获取E-mail
	 *
	 * @return E-mail
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置E-mail
	 *
	 * @param email
	 *            E-mail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取手机
	 *
	 * @return 手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 *
	 * @param mobile
	 *            手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取积分
	 *
	 * @return 积分
	 */
	public Long getPoint() {
		return point;
	}

	/**
	 * 设置积分
	 *
	 * @param point
	 *            积分
	 */
	public void setPoint(Long point) {
		this.point = point;
	}

	public Long getPoint1() {
		return point1;
	}

	public void setPoint1(Long point1) {
		this.point1 = point1;
	}

	public Long getPoint2() {
		return point2;
	}

	public void setPoint2(Long point2) {
		this.point2 = point2;
	}

	public Long getPoint3() {
		return point3;
	}

	public void setPoint3(Long point3) {
		this.point3 = point3;
	}

	/**
	 * 获取余额
	 *
	 * @return 余额
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * 设置余额
	 *
	 * @param balance
	 *            余额
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}


	public BigDecimal getBalance1() {
		return balance1;
	}

	public void setBalance1(BigDecimal balance1) {
		this.balance1 = balance1;
	}

	public BigDecimal getBalance2() {
		return balance2;
	}

	public void setBalance2(BigDecimal balance2) {
		this.balance2 = balance2;
	}

	public BigDecimal getBalance3() {
		return balance3;
	}

	public void setBalance3(BigDecimal balance3) {
		this.balance3 = balance3;
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
	 * 获取姓名
	 *
	 * @return 姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置姓名
	 *
	 * @param name
	 *            姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取性别
	 *
	 * @return 性别
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * 设置性别
	 *
	 * @param gender
	 *            性别
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * 获取出生日期
	 *
	 * @return 出生日期
	 */
	public Date getBirth() {
		return birth;
	}

	/**
	 * 设置出生日期
	 *
	 * @param birth
	 *            出生日期
	 */
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	/**
	 * 获取地址
	 *
	 * @return 地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置地址
	 *
	 * @param address
	 *            地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取邮编
	 *
	 * @return 邮编
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 设置邮编
	 *
	 * @param zipCode
	 *            邮编
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * 获取电话
	 *
	 * @return 电话
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置电话
	 *
	 * @param phone
	 *            电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取会员注册项值0
	 *
	 * @return 会员注册项值0
	 */
	public String getAttributeValue0() {
		return attributeValue0;
	}

	/**
	 * 设置会员注册项值0
	 *
	 * @param attributeValue0
	 *            会员注册项值0
	 */
	public void setAttributeValue0(String attributeValue0) {
		this.attributeValue0 = attributeValue0;
	}

	/**
	 * 获取会员注册项值1
	 *
	 * @return 会员注册项值1
	 */
	public String getAttributeValue1() {
		return attributeValue1;
	}

	/**
	 * 设置会员注册项值1
	 *
	 * @param attributeValue1
	 *            会员注册项值1
	 */
	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	/**
	 * 获取会员注册项值2
	 *
	 * @return 会员注册项值2
	 */
	public String getAttributeValue2() {
		return attributeValue2;
	}

	/**
	 * 设置会员注册项值2
	 *
	 * @param attributeValue2
	 *            会员注册项值2
	 */
	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	/**
	 * 获取会员注册项值3
	 *
	 * @return 会员注册项值3
	 */
	public String getAttributeValue3() {
		return attributeValue3;
	}

	/**
	 * 设置会员注册项值3
	 *
	 * @param attributeValue3
	 *            会员注册项值3
	 */
	public void setAttributeValue3(String attributeValue3) {
		this.attributeValue3 = attributeValue3;
	}

	/**
	 * 获取会员注册项值4
	 *
	 * @return 会员注册项值4
	 */
	public String getAttributeValue4() {
		return attributeValue4;
	}

	/**
	 * 设置会员注册项值4
	 *
	 * @param attributeValue4
	 *            会员注册项值4
	 */
	public void setAttributeValue4(String attributeValue4) {
		this.attributeValue4 = attributeValue4;
	}

	/**
	 * 获取会员注册项值5
	 *
	 * @return 会员注册项值5
	 */
	public String getAttributeValue5() {
		return attributeValue5;
	}

	/**
	 * 设置会员注册项值5
	 *
	 * @param attributeValue5
	 *            会员注册项值5
	 */
	public void setAttributeValue5(String attributeValue5) {
		this.attributeValue5 = attributeValue5;
	}

	/**
	 * 获取会员注册项值6
	 *
	 * @return 会员注册项值6
	 */
	public String getAttributeValue6() {
		return attributeValue6;
	}

	/**
	 * 设置会员注册项值6
	 *
	 * @param attributeValue6
	 *            会员注册项值6
	 */
	public void setAttributeValue6(String attributeValue6) {
		this.attributeValue6 = attributeValue6;
	}

	/**
	 * 获取会员注册项值7
	 *
	 * @return 会员注册项值7
	 */
	public String getAttributeValue7() {
		return attributeValue7;
	}

	/**
	 * 设置会员注册项值7
	 *
	 * @param attributeValue7
	 *            会员注册项值7
	 */
	public void setAttributeValue7(String attributeValue7) {
		this.attributeValue7 = attributeValue7;
	}

	/**
	 * 获取会员注册项值8
	 *
	 * @return 会员注册项值8
	 */
	public String getAttributeValue8() {
		return attributeValue8;
	}

	/**
	 * 设置会员注册项值8
	 *
	 * @param attributeValue8
	 *            会员注册项值8
	 */
	public void setAttributeValue8(String attributeValue8) {
		this.attributeValue8 = attributeValue8;
	}

	/**
	 * 获取会员注册项值9
	 *
	 * @return 会员注册项值9
	 */
	public String getAttributeValue9() {
		return attributeValue9;
	}

	/**
	 * 设置会员注册项值9
	 *
	 * @param attributeValue9
	 *            会员注册项值9
	 */
	public void setAttributeValue9(String attributeValue9) {
		this.attributeValue9 = attributeValue9;
	}

	/**
	 * 获取安全密匙
	 *
	 * @return 安全密匙
	 */
	public SafeKey getSafeKey() {
		return safeKey;
	}

	/**
	 * 设置安全密匙
	 *
	 * @param safeKey
	 *            安全密匙
	 */
	public void setSafeKey(SafeKey safeKey) {
		this.safeKey = safeKey;
	}

	/**
	 * 获取会员等级
	 *
	 * @return 会员等级
	 */
	public MemberRank getMemberRank() {
		return memberRank;
	}

	/**
	 * 设置会员等级
	 *
	 * @param memberRank
	 *            会员等级
	 */
	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * 获取会员预存款记录
	 *
	 * @return 会员预存款记录
	 */
	public Set<MemberDepositLog> getMemberDepositLogs() {
		return memberDepositLogs;
	}

	/**
	 * 设置会员预存款记录
	 *
	 * @param memberDepositLogs
	 *            会员预存款记录
	 */
	public void setMemberDepositLogs(Set<MemberDepositLog> memberDepositLogs) {
		this.memberDepositLogs = memberDepositLogs;
	}

	/**
	 * 获取接收的消息
	 *
	 * @return 接收的消息
	 */
	public Set<Message> getInMessages() {
		return inMessages;
	}

	/**
	 * 设置接收的消息
	 *
	 * @param inMessages
	 *            接收的消息
	 */
	public void setInMessages(Set<Message> inMessages) {
		this.inMessages = inMessages;
	}

	/**
	 * 获取发送的消息
	 *
	 * @return 发送的消息
	 */
	public Set<Message> getOutMessages() {
		return outMessages;
	}

	/**
	 * 设置发送的消息
	 *
	 * @param outMessages
	 *            发送的消息
	 */
	public void setOutMessages(Set<Message> outMessages) {
		this.outMessages = outMessages;
	}

	/**
	 * 获取积分记录
	 *
	 * @return 积分记录
	 */
	public Set<PointLog> getPointLogs() {
		return pointLogs;
	}

	/**
	 * 设置积分记录
	 *
	 * @param pointLogs
	 *            积分记录
	 */
	public void setPointLogs(Set<PointLog> pointLogs) {
		this.pointLogs = pointLogs;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * 获取会员注册项值
	 *
	 * @param memberAttribute
	 *            会员注册项
	 * @return 会员注册项值
	 */
	@Transient
	public Object getAttributeValue(MemberAttribute memberAttribute) {
		if (memberAttribute == null || memberAttribute.getType() == null) {
			return null;
		}
		switch (memberAttribute.getType()) {
		case name:
			return getName();
		case gender:
			return getGender();
		case birth:
			return getBirth();
		case address:
			return getAddress();
		case zipCode:
			return getZipCode();
		case phone:
			return getPhone();
		case text:
		case select:
			if (memberAttribute.getPropertyIndex() != null) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
					return PropertyUtils.getProperty(this, propertyName);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		case checkbox:
			if (memberAttribute.getPropertyIndex() != null) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
					String propertyValue = (String) PropertyUtils.getProperty(this, propertyName);
					if (StringUtils.isNotEmpty(propertyValue)) {
						return JsonUtils.toObject(propertyValue, List.class);
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		}
		return null;
	}

	/**
	 * 设置会员注册项值
	 *
	 * @param memberAttribute
	 *            会员注册项
	 * @param memberAttributeValue
	 *            会员注册项值
	 */
	@Transient
	public void setAttributeValue(MemberAttribute memberAttribute, Object memberAttributeValue) {
		if (memberAttribute == null || memberAttribute.getType() == null) {
			return;
		}
		switch (memberAttribute.getType()) {
		case name:
			if (memberAttributeValue instanceof String || memberAttributeValue == null) {
				setName((String) memberAttributeValue);
			}
			break;
		case gender:
			if (memberAttributeValue instanceof Gender || memberAttributeValue == null) {
				setGender((Gender) memberAttributeValue);
			}
			break;
		case birth:
			if (memberAttributeValue instanceof Date || memberAttributeValue == null) {
				setBirth((Date) memberAttributeValue);
			}
			break;
		case area:
			break;
		case address:
			if (memberAttributeValue instanceof String || memberAttributeValue == null) {
				setAddress((String) memberAttributeValue);
			}
			break;
		case zipCode:
			if (memberAttributeValue instanceof String || memberAttributeValue == null) {
				setZipCode((String) memberAttributeValue);
			}
			break;
		case phone:
			if (memberAttributeValue instanceof String || memberAttributeValue == null) {
				setPhone((String) memberAttributeValue);
			}
			break;
		case text:
		case select:
			if ((memberAttributeValue instanceof String || memberAttributeValue == null) && memberAttribute.getPropertyIndex() != null) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
					PropertyUtils.setProperty(this, propertyName, memberAttributeValue);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		case checkbox:
			if ((memberAttributeValue instanceof Collection || memberAttributeValue == null) && memberAttribute.getPropertyIndex() != null) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
					PropertyUtils.setProperty(this, propertyName, memberAttributeValue != null ? JsonUtils.toJson(memberAttributeValue) : null);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		}
	}

	/**
	 * 移除所有会员注册项值
	 */
	@Transient
	public void removeAttributeValue() {
		setName(null);
		setGender(null);
		setBirth(null);
		setAddress(null);
		setZipCode(null);
		setPhone(null);
		for (int i = 0; i < ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
			try {
				PropertyUtils.setProperty(this, propertyName, null);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	@Override
	@Transient
	public String getDisplayName() {
		return getUsername();
	}

	@Override
	@Transient
	public Object getPrincipal() {
		return getUsername();
	}

	@Override
	@Transient
	public Object getCredentials() {
		return getEncodedPassword();
	}

	@Override
	@Transient
	public boolean isValidCredentials(Object credentials) {
		return credentials != null && DigestUtils.md5Hex(credentials instanceof char[] ? new String((char[]) credentials) : credentials.toString()).equals(getEncodedPassword());
	}


	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		setUsername(StringUtils.lowerCase(getUsername()));
		setEmail(StringUtils.lowerCase(getEmail()));
		setMobile(StringUtils.lowerCase(getMobile()));
	}

	/**
	 * 更新前处理
	 */
	@PreUpdate
	public void preUpdate() {
		setEmail(StringUtils.lowerCase(getEmail()));
		setMobile(StringUtils.lowerCase(getMobile()));
	}

	/**
	 * 类型转换 - 可选项
	 *
	 * @author IGOMALL  Team
	 * @version 1.0
	 */
	@Converter
	public static class TagConverter extends BaseAttributeConverter<List<String>> {
	}

	public void init(){
		setPoint(0L);
		setPoint1(0L);
		setPoint2(0L);
		setPoint3(0L);
		setBalance(BigDecimal.ZERO);
		setBalance1(BigDecimal.ZERO);
		setBalance2(BigDecimal.ZERO);
		setBalance3(BigDecimal.ZERO);
		setAmount(BigDecimal.ZERO);
		setIsEnabled(true);
		setIsLocked(false);
		setLockDate(null);
		setLastLoginDate(new Date());
		setSafeKey(null);
		setMemberDepositLogs(null);
		setInMessages(null);
		setOutMessages(null);
		setPointLogs(null);
	}
}

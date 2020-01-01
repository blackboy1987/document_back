
package com.igomall.entity.order;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.Course;
import com.igomall.entity.member.Member;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - 订单
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
@Table(name = "edu_order")
public class Order extends BaseEntity<Long> {

	private static final long serialVersionUID = 8370942500343156156L;


	/**
	 * 编号
	 */
	@Column(nullable = false, updatable = false, unique = true)
	@JsonView({ListView.class})
	private String sn;


	/**
	 * 会员
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Member member;

	/**
	 * 会员
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Course course;

	@NotEmpty
	@Column(nullable = false,updatable = false)
	private String courseSn;

	@NotEmpty
	@Column(nullable = false,updatable = false)
	private String courseTitle;

	@NotNull
	@Column(nullable = false,updatable = false,precision = 27, scale = 12)
	private BigDecimal coursePrice;

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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getCourseSn() {
		return courseSn;
	}

	public void setCourseSn(String courseSn) {
		this.courseSn = courseSn;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public BigDecimal getCoursePrice() {
		return coursePrice;
	}

	public void setCoursePrice(BigDecimal coursePrice) {
		this.coursePrice = coursePrice;
	}

	@Transient
	@JsonView({ListView.class})
	public String getUsername(){
		if(member!=null){
			return member.getUsername();
		}
		return null;
	}

	public interface ListView extends IdView{

	}
}
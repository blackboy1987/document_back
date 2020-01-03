package com.igomall.entity.course;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Entity - 咨询
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
@Table(name = "edu_course_consultation")
public class CourseConsultation extends BaseEntity<Long> {

	/**
	 * 内容
	 */
	@JsonView(BaseView.class)
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	private String content;

	/**
	 * 是否显示
	 */
	@Column(nullable = false)
	private Boolean isShow;

	/**
	 * IP
	 */
	@Column(nullable = false, updatable = false)
	private String ip;

	/**
	 * 会员
	 */
	@JsonView(BaseView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	private Member member;

	/**
	 * 商品
	 */
	@JsonView(BaseView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Course course;

	/**
	 * 咨询
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	private CourseConsultation forConsultation;

	/**
	 * 回复
	 */
	@JsonView(BaseView.class)
	@OneToMany(mappedBy = "forConsultation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createdDate asc")
	private Set<CourseConsultation> replyConsultations = new HashSet<>();

	/**
	 * 店铺
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Lesson lesson;

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取是否显示
	 * 
	 * @return 是否显示
	 */
	public Boolean getIsShow() {
		return isShow;
	}

	/**
	 * 设置是否显示
	 * 
	 * @param isShow
	 *            是否显示
	 */
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	/**
	 * 获取IP
	 * 
	 * @return IP
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置IP
	 * 
	 * @param ip
	 *            IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
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
	 * 获取商品
	 * 
	 * @return 商品
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * 设置商品
	 * 
	 * @param course
	 *            商品
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * 获取咨询
	 * 
	 * @return 咨询
	 */
	public CourseConsultation getForConsultation() {
		return forConsultation;
	}

	/**
	 * 设置咨询
	 * 
	 * @param forConsultation
	 *            咨询
	 */
	public void setForConsultation(CourseConsultation forConsultation) {
		this.forConsultation = forConsultation;
	}

	/**
	 * 获取回复
	 * 
	 * @return 回复
	 */
	public Set<CourseConsultation> getReplyConsultations() {
		return replyConsultations;
	}

	/**
	 * 设置回复
	 * 
	 * @param replyConsultations
	 *            回复
	 */
	public void setReplyConsultations(Set<CourseConsultation> replyConsultations) {
		this.replyConsultations = replyConsultations;
	}

	/**
	 * 获取店铺
	 * 
	 * @return 店铺
	 */
	public Lesson getLesson() {
		return lesson;
	}

	/**
	 * 设置店铺
	 * 
	 * @param lesson
	 *            店铺
	 */
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}
}
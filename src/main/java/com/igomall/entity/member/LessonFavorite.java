
package com.igomall.entity.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;

import javax.persistence.*;

/**
 * Entity - 商品收藏
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
@Table(name = "edu_lesson_favorite", uniqueConstraints = @UniqueConstraint(columnNames = { "member_id", "lesson_id" }))
public class LessonFavorite extends BaseEntity<Long> {

	private static final long serialVersionUID = 2540556338075542780L;

	/**
	 * 会员
	 */
	@JsonView(BaseView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Member member;

	/**
	 * 商品
	 */
	@JsonView(BaseView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Lesson lesson;

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

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}
}
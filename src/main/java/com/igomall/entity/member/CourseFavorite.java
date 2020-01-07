
package com.igomall.entity.member;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.Course;

/**
 * Entity - 商品收藏
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
@Table(name = "edu_course_favorite", uniqueConstraints = @UniqueConstraint(columnNames = { "member_id", "course_id" }))
public class CourseFavorite extends BaseEntity<Long> {

	private static final long serialVersionUID = 2540556338075542780L;

	/**
	 * 最大商品收藏数量
	 */
	public static final Integer MAX_COURSE_FAVORITE_SIZE = 999999999;

	/**
	 * 会员
	 */
	@JsonView(BaseView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Member member;

	/**
	 * 课程
	 */
	@JsonView(BaseView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Course course;

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

	@JsonView({ListView.class})
	public String getCourseSn(){
		if(course!=null){
			course.getSn();
		}
		return null;
	}

	@JsonView({ListView.class})
	public String getCourseTitle(){
		if(course!=null){
			course.getTitle();
		}
		return null;
	}

	@JsonView({ListView.class})
	public String getCourseImage(){
		if(course!=null){
			course.getImage();
		}
		return null;
	}

	public interface ListView extends IdView {}
}

package com.igomall.entity.course;

import com.igomall.entity.OrderedEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity - 商品标签
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
@Table(name = "edu_course_tag")
public class CourseTag extends OrderedEntity<Long> {

	private static final long serialVersionUID = 4136507336496569742L;

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/**
	 * 图标
	 */
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	private String icon;

	/**
	 * 备注
	 */
	@Length(max = 200)
	private String memo;

	/**
	 * 商品
	 */
	@ManyToMany(mappedBy = "courseTags", fetch = FetchType.LAZY)
	private Set<Course> courses = new HashSet<>();

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
	 * 获取图标
	 * 
	 * @return 图标
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 设置图标
	 * 
	 * @param icon
	 *            图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 获取备注
	 * 
	 * @return 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Course> courses = getCourses();
		if (courses != null) {
			for (Course course : courses) {
				course.getCourseTags().remove(this);
			}
		}
	}

}

package com.igomall.entity.teacher;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity - 讲师等级
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
@Table(name = "edu_teacher_rank")
public class TeacherRank extends BaseEntity<Long> {

	private static final long serialVersionUID = 3599029355500655209L;

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	@JsonView({ListView.class,EditView.class,AllView.class})
	private String name;

	/**
	 * 是否默认
	 */
	@NotNull
	@Column(nullable = false)
	@JsonView({ListView.class,EditView.class})
	private Boolean isDefault;

	@NotNull
	@Column(nullable = false)
	@JsonView({ListView.class,EditView.class,AllView.class})
	private Boolean isEnabled;

	/**
	 * 会员
	 */
	@OneToMany(mappedBy = "teacherRank", fetch = FetchType.LAZY)
	private Set<Teacher> teachers = new HashSet<>();

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

	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}

	public interface ListView extends BaseView{}

	public interface EditView extends IdView{}
	public interface AllView extends IdView{}
}
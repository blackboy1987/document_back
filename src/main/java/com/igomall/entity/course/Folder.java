
package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity - 地区
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
@Table(name = "edu_folder")
public class Folder extends OrderedEntity<Long> {

	public final static String QUERY_ALL = "select id,name from edu_folder order by orders asc,created_date desc ";
	public final static String QUERY_ALL_BY_COURSE = "select id,name from edu_folder where course_id=courseId order by orders asc,created_date desc ";

	/**
	 * 树路径分隔符
	 */
	public static final String TREE_PATH_SEPARATOR = ",";

	/**
	 * 名称
	 */
	@JsonView({JsonApiView.class,EditView.class})
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/**
	 * 全称
	 */
	@Column(nullable = false, length = 4000)
	private String fullName;

	/**
	 * 树路径
	 */
	@Column(nullable = false)
	private String treePath;

	@JsonView({EditView.class})
	private String path;

	/**
	 * 层级
	 */
	@Column(nullable = false)
	private Integer grade;

	/**
	 * 上级地区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Folder parent;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false,updatable = false)
	private Course course;

	/**
	 * 下级地区
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	private Set<Folder> children = new HashSet<>();

	/**
	 * 会员
	 */
	@JsonView(JsonApiView.class)
	@OneToMany(mappedBy = "folder", fetch = FetchType.LAZY)
	@OrderBy("order asc ")
	private Set<Lesson> lessons = new HashSet<>();

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
	 * 获取全称
	 * 
	 * @return 全称
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * 设置全称
	 * 
	 * @param fullName
	 *            全称
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * 获取树路径
	 * 
	 * @return 树路径
	 */
	public String getTreePath() {
		return treePath;
	}

	/**
	 * 设置树路径
	 * 
	 * @param treePath
	 *            树路径
	 */
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 获取层级
	 * 
	 * @return 层级
	 */
	public Integer getGrade() {
		return grade;
	}

	/**
	 * 设置层级
	 * 
	 * @param grade
	 *            层级
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	/**
	 * 获取上级地区
	 * 
	 * @return 上级地区
	 */
	public Folder getParent() {
		return parent;
	}

	/**
	 * 设置上级地区
	 * 
	 * @param parent
	 *            上级地区
	 */
	public void setParent(Folder parent) {
		this.parent = parent;
	}

	/**
	 * 获取下级地区
	 * 
	 * @return 下级地区
	 */
	public Set<Folder> getChildren() {
		return children;
	}

	/**
	 * 设置下级地区
	 * 
	 * @param children
	 *            下级地区
	 */
	public void setChildren(Set<Folder> children) {
		this.children = children;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	public Set<Lesson> getLessons() {
		return lessons;
	}

	/**
	 * 设置会员
	 * 
	 * @param lessons
	 *            会员
	 */
	public void setLessons(Set<Lesson> lessons) {
		this.lessons = lessons;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * 获取所有上级地区ID
	 * 
	 * @return 所有上级地区ID
	 */
	@Transient
	public Long[] getParentIds() {
		String[] parentIds = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
		Long[] result = new Long[parentIds.length];
		for (int i = 0; i < parentIds.length; i++) {
			result[i] = Long.valueOf(parentIds[i]);
		}
		return result;
	}

	/**
	 * 获取所有上级地区
	 * 
	 * @return 所有上级地区
	 */
	@Transient
	public List<Folder> getParents() {
		List<Folder> parents = new ArrayList<>();
		recursiveParents(parents, this);
		return parents;
	}

	/**
	 * 递归上级地区
	 * 
	 * @param parents
	 *            上级地区
	 * @param folder
	 *            地区
	 */
	private void recursiveParents(List<Folder> parents, Folder folder) {
		if (folder == null) {
			return;
		}
		Folder parent = folder.getParent();
		if (parent != null) {
			parents.add(0, parent);
			recursiveParents(parents, parent);
		}
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Lesson> lessons = getLessons();
		if (lessons != null) {
			for (Lesson lesson : lessons) {
				lesson.setFolder(null);
				lesson.setCourse(getCourse());
			}
		}
	}

	@Transient
	@JsonView({ListView.class,EditView.class})
	public Long getCourseId(){
		if(course!=null){
			return course.getId();
		}
		return null;
	}

	@Transient
	@JsonView({ListView.class,EditView.class})
	public String getCourseTitle(){
		if(course!=null){
			return course.getTitle();
		}
		return null;
	}
}
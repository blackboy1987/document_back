
package com.igomall.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.audit.AuditingEntityListener;
import com.igomall.audit.CreatedDate;
import com.igomall.audit.LastModifiedDate;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity - 基类
 *
 * @author blackboy
 * @version 1.0
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = -67188388306700736L;

	/**
	 * "ID"属性名称
	 */
	public static final String ID_PROPERTY_NAME = "id";

	/**
	 * "创建日期"属性名称
	 */
	public static final String CREATE_DATE_PROPERTY_NAME = "createDate";

	/**
	 * "最后修改日期"属性名称
	 */
	public static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";

	/**
	 * 保存验证组
	 */
	public interface Save extends Default {

	}

	/**
	 * 更新验证组
	 */
	public interface Update extends Default {

	}

	/**
	 * 基础视图
	 */
	public interface BaseView {

	}

	/**
	 * 基础视图
	 */
	public interface IdView {

	}

	/**
	 * 基础视图
	 */
	public interface ListView {

	}

	/**
	 * 基础视图
	 */
	public interface EditView {

	}



	/**
	 * 基础视图
	 */
	public interface ViewView {

	}

	public interface ApiListView{

	}

	/**
	 * ID
	 */
	@JsonView({BaseView.class,IdView.class,ListView.class,EditView.class,ViewView.class,ApiListView.class})
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private ID id;

	/**
	 * 创建日期
	 */
	@JsonView({BaseView.class,ListView.class,ViewView.class})
	@CreatedDate
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@DateBridge(resolution = Resolution.SECOND)
	@Column(nullable = false, updatable = false)
	private Date createDate;

	/**
	 * 最后修改日期
	 */
	@JsonView({BaseView.class,ListView.class,ViewView.class})
	@LastModifiedDate
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@DateBridge(resolution = Resolution.SECOND)
	@Column(nullable = false)
	private Date modifyDate;

	/**
	 * 获取ID
	 *
	 * @return ID
	 */
	public ID getId() {
		return id;
	}

	/**
	 * 设置ID
	 *
	 * @param id
	 *            ID
	 */
	public void setId(ID id) {
		this.id = id;
	}

	/**
	 * 获取创建日期
	 *
	 * @return 创建日期
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 设置创建日期
	 *
	 * @param createDate
	 *            创建日期
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取最后修改日期
	 *
	 * @return 最后修改日期
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * 设置最后修改日期
	 *
	 * @param modifyDate
	 *            最后修改日期
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * 判断是否为新建对象
	 *
	 * @return 是否为新建对象
	 */
	@Transient
	public boolean isNew() {
		return getId() == null;
	}

	/**
	 * 重写toString方法
	 *
	 * @return 字符串
	 */
	@Override
	public String toString() {
		return String.format("Entity of type %s with id: %s", getClass().getName(), getId());
	}

	/**
	 * 重写equals方法
	 *
	 * @param obj
	 *            对象
	 * @return 是否相等
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!BaseEntity.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		BaseEntity<?> other = (BaseEntity<?>) obj;
		return getId() != null ? getId().equals(other.getId()) : false;
	}

	/**
	 * 重写hashCode方法
	 *
	 * @return HashCode
	 */
	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += getId() != null ? getId().hashCode() * 31 : 0;
		return hashCode;
	}

}
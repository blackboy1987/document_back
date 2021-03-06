
package com.igomall.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.wechat.BookItem;
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
@Table(name = "document_resource_tag")
public class ResourceTag extends OrderedEntity<Long> {

	private static final long serialVersionUID = 4136507336496569742L;

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false,unique = true)
	@JsonView({ApiListView.class})
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
	@ManyToMany(mappedBy = "resourceTags", fetch = FetchType.LAZY)
	private Set<Resource> resources = new HashSet<>();

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

	/**
	 * 获取商品
	 *
	 * @return 商品
	 */
	public Set<Resource> getResources() {
		return resources;
	}

	/**
	 * 设置商品
	 *
	 * @param resources
	 *            商品
	 */
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Resource> resources = getResources();
		if (resources != null) {
			for (Resource resource : resources) {
				resource.getResourceTags().remove(this);
			}
		}
	}

	@Transient
	@JsonView({ApiListView.class})
	public Set<Resource> getItems(){
		return getResources();
	}
}

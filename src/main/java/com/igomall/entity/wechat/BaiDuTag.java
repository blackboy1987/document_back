
package com.igomall.entity.wechat;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity - 文章标签
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
@Table(name = "edu_baidu_tag")
public class BaiDuTag extends OrderedEntity<Long> {

	private static final long serialVersionUID = -2735037966597250149L;

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false,unique = true)
	@JsonView({ListView.class,EditView.class})
	private String name;

	@NotEmpty
	@Column(length = 3,updatable = false,unique = true,nullable = false)
	@JsonView({EditView.class})
	private String code;

	/**
	 * 文章
	 */
	@ManyToMany(mappedBy = "baiDuTags", fetch = FetchType.LAZY)
	private Set<BaiDuResource> baiDuResources = new HashSet<>();

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取文章
	 *
	 * @return 文章
	 */
	public Set<BaiDuResource> getBaiDuResources() {
		return baiDuResources;
	}

	/**
	 * 设置文章
	 *
	 * @param baiDuResources
	 *            文章
	 */
	public void setBaiDuResources(Set<BaiDuResource> baiDuResources) {
		this.baiDuResources = baiDuResources;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<BaiDuResource> baiDuResources = getBaiDuResources();
		if (baiDuResources != null) {
			for (BaiDuResource baiDuResource : baiDuResources) {
				baiDuResource.getBaiDuTags().remove(this);
			}
		}
	}

}
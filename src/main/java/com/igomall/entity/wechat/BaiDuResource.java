
package com.igomall.entity.wechat;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
@Table(name = "edu_baidu_resource")
public class BaiDuResource extends BaseEntity<Long> {

	/**
	 * 点击数缓存名称
	 */
	public static final String HITS_CACHE_NAME = "baiDuResourceHits";

	private static final long serialVersionUID = 1475773294701585482L;

	/**
	 * 标题
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	@JsonView({ListView.class,EditView.class})
	private String title;

	@Length(max = 200)
	@JsonView({ListView.class,EditView.class})
	private String baiDuUrl;

	@JsonView({ListView.class,EditView.class})
	private String baiDuCode;

	/**
	 * 点击数
	 */
	@Column(nullable = false)
	private Long hits;


	@NotEmpty
	@Column(length = 5,updatable = false,unique = true,nullable = false)
	@JsonView({ListView.class,EditView.class})
	private String code;

	/**
	 * 文章标签
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("order asc")
	private Set<BaiDuTag> baiDuTags = new HashSet<>();

	/**
	 * 获取标题
	 * 
	 * @return 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getBaiDuUrl() {
		return baiDuUrl;
	}

	public void setBaiDuUrl(String baiDuUrl) {
		this.baiDuUrl = baiDuUrl;
	}

	public String getBaiDuCode() {
		return baiDuCode;
	}

	public void setBaiDuCode(String baiDuCode) {
		this.baiDuCode = baiDuCode;
	}

	/**
	 * 获取点击数
	 * 
	 * @return 点击数
	 */
	public Long getHits() {
		return hits;
	}

	/**
	 * 设置点击数
	 * 
	 * @param hits
	 *            点击数
	 */
	public void setHits(Long hits) {
		this.hits = hits;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取文章标签
	 * 
	 * @return 文章标签
	 */
	public Set<BaiDuTag> getBaiDuTags() {
		return baiDuTags;
	}

	/**
	 * 设置文章标签
	 * 
	 * @param baiDuTags
	 *            文章标签
	 */
	public void setBaiDuTags(Set<BaiDuTag> baiDuTags) {
		this.baiDuTags = baiDuTags;
	}


	@JsonView({ListView.class})
	@Transient
	public List<String> getTagNames(){
		List<String> tagNames = new ArrayList<>();
		if(baiDuTags!=null){
			for (BaiDuTag baiDuTag:baiDuTags) {
				tagNames.add(baiDuTag.getName());
			}
		}

		return tagNames;
	}
	@JsonView({EditView.class})
	@Transient
	public List<Long> getTagIds(){
		List<Long> tagIds = new ArrayList<>();
		if(baiDuTags!=null){
			for (BaiDuTag baiDuTag:baiDuTags) {
				tagIds.add(baiDuTag.getId());
			}
		}
		return tagIds;
	}
}

package com.igomall.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Entity - 权限
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
public class Permission extends OrderedEntity<Long> {

	private static final long serialVersionUID = 5095521437302782717L;

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	private Boolean isEnabled;

	/**
	 * 菜单
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false,updatable = false)
	private Menu menu;

	/**
	 * 控制的方法
	 */
	@NotNull
	@Column(nullable = false)
	private String method;

	/**
	 * url
	 */
	@NotNull
	@Column(nullable = false)
	private String url;

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

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}


	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	@Transient
	public String getMenuName(){
		if(menu!=null){
			return menu.getName();
		}
		return null;
	}
	@Transient
	public Long getMenuId(){
		if(menu!=null){
			return menu.getId();
		}
		return null;
	}
}
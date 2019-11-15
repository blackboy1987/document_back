
package com.igomall.service;

import java.util.List;

import com.igomall.entity.Menu;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface MenuService extends BaseService<Menu, Long> {

	/**
	 * 查找顶级地区
	 * 
	 * @return 顶级地区
	 */
	List<Menu> findRoots();

	/**
	 * 查找顶级地区
	 * 
	 * @param count
	 *            数量
	 * @return 顶级地区
	 */
	List<Menu> findRoots(Integer count);

	/**
	 * 查找上级地区
	 * 
	 * @param menu
	 *            地区
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级地区
	 */
	List<Menu> findParents(Menu menu, boolean recursive, Integer count);

	/**
	 * 查找下级地区
	 * 
	 * @param menu
	 *            地区
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级地区
	 */
	List<Menu> findChildren(Menu menu, boolean recursive, Integer count);

}
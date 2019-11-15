
package com.igomall.service;

import com.igomall.entity.Menu;

import java.util.List;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface MenuService extends BaseService<Menu, Long> {

	/**
	 * 查找顶级商品分类
	 *
	 * @return 顶级商品分类
	 */
	List<Menu> findRoots();

	/**
	 * 查找顶级商品分类
	 *
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<Menu> findRoots(Integer count);

	/**
	 * 查找顶级商品分类
	 *
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 顶级商品分类
	 */
	List<Menu> findRoots(Integer count, boolean useCache);

	/**
	 * 查找上级商品分类
	 *
	 * @param menu
	 *            商品分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级商品分类
	 */
	List<Menu> findParents(Menu menu, boolean recursive, Integer count);

	/**
	 * 查找上级商品分类
	 *
	 * @param menuId
	 *            商品分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 上级商品分类
	 */
	List<Menu> findParents(Long menuId, boolean recursive, Integer count, boolean useCache);

	/**
	 * 查找商品分类树
	 *
	 * @return 商品分类树
	 */
	List<Menu> findTree();

	/**
	 * 查找下级商品分类
	 *
	 * @param menu
	 *            商品分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级商品分类
	 */
	List<Menu> findChildren(Menu menu, boolean recursive, Integer count);

	/**
	 * 查找下级商品分类
	 *
	 * @param menuId
	 *            商品分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 下级商品分类
	 */
	List<Menu> findChildren(Long menuId, boolean recursive, Integer count, boolean useCache);


}
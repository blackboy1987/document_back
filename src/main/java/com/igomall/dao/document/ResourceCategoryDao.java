
package com.igomall.dao.document;

import com.igomall.dao.BaseDao;
import com.igomall.entity.document.ResourceCategory;

import java.util.List;

/**
 * Dao - 商品分类
 * 
 * @author blackboy
 * @version 1.0
 */
public interface ResourceCategoryDao extends BaseDao<ResourceCategory, Long> {

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<ResourceCategory> findRoots(Integer count);

	/**
	 * 查找上级商品分类
	 * 
	 * @param resourceCategory
	 *            商品分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级商品分类
	 */
	List<ResourceCategory> findParents(ResourceCategory resourceCategory, boolean recursive, Integer count);

	/**
	 * 查找下级商品分类
	 * 
	 * @param resourceCategory
	 *            商品分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级商品分类
	 */
	List<ResourceCategory> findChildren(ResourceCategory resourceCategory, boolean recursive, Integer count);

}
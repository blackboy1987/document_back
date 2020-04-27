
package com.igomall.service.document;

import com.igomall.entity.document.ResourceCategory;
import com.igomall.service.BaseService;

import java.util.List;

/**
 * Service - 商品分类
 * 
 * @author blackboy
 * @version 1.0
 */
public interface ResourceCategoryService extends BaseService<ResourceCategory, Long> {

	ResourceCategory findByName(String name);


	/**
	 * 查找顶级商品分类
	 * 
	 * @return 顶级商品分类
	 */
	List<ResourceCategory> findRoots();

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<ResourceCategory> findRoots(Integer count);

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 顶级商品分类
	 */
	List<ResourceCategory> findRoots(Integer count, boolean useCache);

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
	 * 查找上级商品分类
	 * 
	 * @param productCategoryId
	 *            商品分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 上级商品分类
	 */
	List<ResourceCategory> findParents(Long productCategoryId, boolean recursive, Integer count, boolean useCache);

	/**
	 * 查找商品分类树
	 * 
	 * @return 商品分类树
	 */
	List<ResourceCategory> findTree();

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

	/**
	 * 查找下级商品分类
	 * 
	 * @param productCategoryId
	 *            商品分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 下级商品分类
	 */
	List<ResourceCategory> findChildren(Long productCategoryId, boolean recursive, Integer count, boolean useCache);

}
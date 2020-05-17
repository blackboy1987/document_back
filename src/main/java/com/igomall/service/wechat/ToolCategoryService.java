
package com.igomall.service.wechat;

import com.igomall.entity.wechat.ToolCategory;
import com.igomall.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Service - 文章分类
 * 
 * @author blackboy
 * @version 1.0
 */
public interface ToolCategoryService extends BaseService<ToolCategory, Long> {

	/**
	 * 查找顶级文章分类
	 * 
	 * @return 顶级文章分类
	 */
	List<ToolCategory> findRoots();
	/**
	 * 查找顶级文章分类
	 *
	 * @return 顶级文章分类
	 */
	List<Map<String,Object>> findRoots1();

	/**
	 * 查找顶级文章分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级文章分类
	 */
	List<ToolCategory> findRoots(Integer count);

	/**
	 * 查找顶级文章分类
	 * 
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 顶级文章分类
	 */
	List<ToolCategory> findRoots(Integer count, boolean useCache);

	/**
	 * 查找上级文章分类
	 * 
	 * @param toolCategory
	 *            文章分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级文章分类
	 */
	List<ToolCategory> findParents(ToolCategory toolCategory, boolean recursive, Integer count);

	/**
	 * 查找上级文章分类
	 * 
	 * @param toolCategoryId
	 *            文章分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 上级文章分类
	 */
	List<ToolCategory> findParents(Long toolCategoryId, boolean recursive, Integer count, boolean useCache);

	/**
	 * 查找文章分类树
	 * 
	 * @return 文章分类树
	 */
	List<ToolCategory> findTree();

	/**
	 * 查找下级文章分类
	 * 
	 * @param toolCategory
	 *            文章分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级文章分类
	 */
	List<ToolCategory> findChildren(ToolCategory toolCategory, boolean recursive, Integer count);

	/**
	 * 查找下级文章分类
	 * 
	 * @param toolCategoryId
	 *            文章分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 下级文章分类
	 */
	List<ToolCategory> findChildren(Long toolCategoryId, boolean recursive, Integer count, boolean useCache);

}
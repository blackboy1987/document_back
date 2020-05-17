
package com.igomall.service.wechat;

import java.util.List;
import java.util.Map;

import com.igomall.entity.wechat.ProjectCategory;
import com.igomall.service.BaseService;

/**
 * Service - 文章分类
 * 
 * @author blackboy
 * @version 1.0
 */
public interface ProjectCategoryService extends BaseService<ProjectCategory, Long> {

	/**
	 * 查找顶级文章分类
	 * 
	 * @return 顶级文章分类
	 */
	List<ProjectCategory> findRoots();
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
	List<ProjectCategory> findRoots(Integer count);

	/**
	 * 查找顶级文章分类
	 * 
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 顶级文章分类
	 */
	List<ProjectCategory> findRoots(Integer count, boolean useCache);

	/**
	 * 查找上级文章分类
	 * 
	 * @param projectCategory
	 *            文章分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级文章分类
	 */
	List<ProjectCategory> findParents(ProjectCategory projectCategory, boolean recursive, Integer count);

	/**
	 * 查找上级文章分类
	 * 
	 * @param projectCategoryId
	 *            文章分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 上级文章分类
	 */
	List<ProjectCategory> findParents(Long projectCategoryId, boolean recursive, Integer count, boolean useCache);

	/**
	 * 查找文章分类树
	 * 
	 * @return 文章分类树
	 */
	List<ProjectCategory> findTree();

	/**
	 * 查找下级文章分类
	 * 
	 * @param projectCategory
	 *            文章分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级文章分类
	 */
	List<ProjectCategory> findChildren(ProjectCategory projectCategory, boolean recursive, Integer count);

	/**
	 * 查找下级文章分类
	 * 
	 * @param projectCategoryId
	 *            文章分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 下级文章分类
	 */
	List<ProjectCategory> findChildren(Long projectCategoryId, boolean recursive, Integer count, boolean useCache);

}
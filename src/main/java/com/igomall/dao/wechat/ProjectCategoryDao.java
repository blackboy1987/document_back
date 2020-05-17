
package com.igomall.dao.wechat;

import com.igomall.dao.BaseDao;
import com.igomall.entity.wechat.ProjectCategory;

import java.util.List;

/**
 * Dao - 文章分类
 * 
 * @author blackboy
 * @version 1.0
 */
public interface ProjectCategoryDao extends BaseDao<ProjectCategory, Long> {

	/**
	 * 查找顶级文章分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级文章分类
	 */
	List<ProjectCategory> findRoots(Integer count);

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

}
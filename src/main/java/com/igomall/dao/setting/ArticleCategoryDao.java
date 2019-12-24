
package com.igomall.dao.setting;

import java.util.List;

import com.igomall.dao.BaseDao;
import com.igomall.entity.setting.ArticleCategory;

/**
 * Dao - 文章分类
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface ArticleCategoryDao extends BaseDao<ArticleCategory, Long> {

	/**
	 * 查找顶级文章分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级文章分类
	 */
	List<ArticleCategory> findRoots(Integer count);

	/**
	 * 查找上级文章分类
	 * 
	 * @param articleCategory
	 *            文章分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级文章分类
	 */
	List<ArticleCategory> findParents(ArticleCategory articleCategory, boolean recursive, Integer count);

	/**
	 * 查找下级文章分类
	 * 
	 * @param articleCategory
	 *            文章分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级文章分类
	 */
	List<ArticleCategory> findChildren(ArticleCategory articleCategory, boolean recursive, Integer count);

}
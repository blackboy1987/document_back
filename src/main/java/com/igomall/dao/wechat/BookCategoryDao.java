
package com.igomall.dao.wechat;

import com.igomall.dao.BaseDao;
import com.igomall.entity.wechat.BookCategory;

import java.util.List;

/**
 * Dao - 文章分类
 * 
 * @author blackboy
 * @version 1.0
 */
public interface BookCategoryDao extends BaseDao<BookCategory, Long> {

	/**
	 * 查找顶级文章分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级文章分类
	 */
	List<BookCategory> findRoots(Integer count);

	/**
	 * 查找上级文章分类
	 * 
	 * @param bookCategory
	 *            文章分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级文章分类
	 */
	List<BookCategory> findParents(BookCategory bookCategory, boolean recursive, Integer count);

	/**
	 * 查找下级文章分类
	 * 
	 * @param bookCategory
	 *            文章分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级文章分类
	 */
	List<BookCategory> findChildren(BookCategory bookCategory, boolean recursive, Integer count);

}

package com.igomall.service.course;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Folder;
import com.igomall.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Service - 地区
 * 
 * @author blackboy
 * @version 1.0
 */
public interface FolderService extends BaseService<Folder, Long> {

	/**
	 * 查找顶级地区
	 * 
	 * @return 顶级地区
	 */
	List<Folder> findRoots();

	/**
	 * 查找顶级地区
	 * 
	 * @param count
	 *            数量
	 * @return 顶级地区
	 */
	List<Folder> findRoots(Integer count);

	/**
	 * 查找上级地区
	 * 
	 * @param folder
	 *            地区
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级地区
	 */
	List<Folder> findParents(Folder folder, boolean recursive, Integer count);

	/**
	 * 查找下级地区
	 * 
	 * @param folder
	 *            地区
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级地区
	 */
	List<Folder> findChildren(Folder folder, boolean recursive, Integer count);


	List<Folder> findList(Course course, Integer count, List<Filter> filters, List<Order> orders);

	List<Map<String,Object>> findAllBySql();

	List<Map<String,Object>> findAllBySql(Long courseId);
}
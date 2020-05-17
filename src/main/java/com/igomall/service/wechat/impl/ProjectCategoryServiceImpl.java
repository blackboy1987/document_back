
package com.igomall.service.wechat.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.igomall.dao.wechat.ProjectCategoryDao;
import com.igomall.entity.wechat.ProjectCategory;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.ProjectCategoryService;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
/**
 * Service - 文章分类
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class ProjectCategoryServiceImpl extends BaseServiceImpl<ProjectCategory, Long> implements ProjectCategoryService {

	@Autowired
	private ProjectCategoryDao projectCategoryDao;
	@Autowired
	private CacheManager cacheManager;

	@Transactional(readOnly = true)
	public List<ProjectCategory> findRoots() {
		return projectCategoryDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	public List<Map<String,Object>> findRoots1() {
		Ehcache cache = cacheManager.getEhcache("projectCategory");
		List<Map<String,Object>> projectCategories = new ArrayList<>();
		try {
			Element element = cache.get("projectCategoryTree");
			if (element != null) {
				projectCategories = (List<Map<String,Object>>) element.getObjectValue();
			} else {
				// 一级
				projectCategories = jdbcTemplate.queryForList("select id,name from edu_project_category where parent_id is null order by orders asc ");
				// 循环二级
				for (Map<String,Object> projectCategory:projectCategories) {
					List<Map<String,Object>> children = jdbcTemplate.queryForList("select id,name from edu_project_category where parent_id=? order by orders asc ",projectCategory.get("id"));
					for (Map<String,Object> child:children) {
						child.put("projectItems",jdbcTemplate.queryForList("select id,name, memo,icon,download_url downloadUrl,site_url siteUrl from edu_project_item where project_category_id=?",child.get("id")));
					}
					projectCategory.put("children",children);
				}

			}
			cache.put(new Element("projectCategoryTree", projectCategories));
		}catch (Exception e){
			e.printStackTrace();
		}
		return projectCategories;
	}



	@Transactional(readOnly = true)
	public List<ProjectCategory> findRoots(Integer count) {
		return projectCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "projectCategory", condition = "#useCache")
	public List<ProjectCategory> findRoots(Integer count, boolean useCache) {
		return projectCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<ProjectCategory> findParents(ProjectCategory projectCategory, boolean recursive, Integer count) {
		return projectCategoryDao.findParents(projectCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "projectCategory", condition = "#useCache")
	public List<ProjectCategory> findParents(Long projectCategoryId, boolean recursive, Integer count, boolean useCache) {
		ProjectCategory projectCategory = projectCategoryDao.find(projectCategoryId);
		if (projectCategoryId != null && projectCategory == null) {
			return Collections.emptyList();
		}
		return projectCategoryDao.findParents(projectCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	public List<ProjectCategory> findTree() {
		return projectCategoryDao.findChildren(null, true, null);
	}

	@Transactional(readOnly = true)
	public List<ProjectCategory> findChildren(ProjectCategory projectCategory, boolean recursive, Integer count) {
		return projectCategoryDao.findChildren(projectCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "projectCategory", condition = "#useCache")
	public List<ProjectCategory> findChildren(Long projectCategoryId, boolean recursive, Integer count, boolean useCache) {
		ProjectCategory projectCategory = projectCategoryDao.find(projectCategoryId);
		if (projectCategoryId != null && projectCategory == null) {
			return Collections.emptyList();
		}
		return projectCategoryDao.findChildren(projectCategory, recursive, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "project", "projectCategory" }, allEntries = true)
	public ProjectCategory save(ProjectCategory projectCategory) {
		Assert.notNull(projectCategory);

		setValue(projectCategory);
		return super.save(projectCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "project", "projectCategory" }, allEntries = true)
	public ProjectCategory update(ProjectCategory projectCategory) {
		Assert.notNull(projectCategory);

		setValue(projectCategory);
		for (ProjectCategory children : projectCategoryDao.findChildren(projectCategory, true, null)) {
			setValue(children);
		}
		return super.update(projectCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "project", "projectCategory" }, allEntries = true)
	public ProjectCategory update(ProjectCategory projectCategory, String... ignoreProperties) {
		return super.update(projectCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "project", "projectCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "project", "projectCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "project", "projectCategory" }, allEntries = true)
	public void delete(ProjectCategory projectCategory) {
		super.delete(projectCategory);
	}

	/**
	 * 设置值
	 * 
	 * @param projectCategory
	 *            文章分类
	 */
	private void setValue(ProjectCategory projectCategory) {
		if (projectCategory == null) {
			return;
		}
		ProjectCategory parent = projectCategory.getParent();
		if (parent != null) {
			projectCategory.setTreePath(parent.getTreePath() + parent.getId() + ProjectCategory.TREE_PATH_SEPARATOR);
		} else {
			projectCategory.setTreePath(ProjectCategory.TREE_PATH_SEPARATOR);
		}
		projectCategory.setGrade(projectCategory.getParentIds().length);
	}

}
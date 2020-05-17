
package com.igomall.service.wechat.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.igomall.dao.wechat.ToolCategoryDao;
import com.igomall.entity.wechat.ToolCategory;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.ToolCategoryService;
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
public class ToolCategoryServiceImpl extends BaseServiceImpl<ToolCategory, Long> implements ToolCategoryService {

	@Autowired
	private ToolCategoryDao toolCategoryDao;
	@Autowired
	private CacheManager cacheManager;

	@Transactional(readOnly = true)
	public List<ToolCategory> findRoots() {
		return toolCategoryDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	public List<Map<String,Object>> findRoots1() {
		Ehcache cache = cacheManager.getEhcache("toolCategory");
		List<Map<String,Object>> toolCategories = new ArrayList<>();
		try {
			Element element = cache.get("toolCategoryTree");
			if (element != null) {
				toolCategories = (List<Map<String,Object>>) element.getObjectValue();
			} else {
				// 一级
				toolCategories = jdbcTemplate.queryForList("select id,name from edu_tool_category where parent_id is null order by orders asc ");
				// 循环二级
				for (Map<String,Object> toolCategory:toolCategories) {
					List<Map<String,Object>> children = jdbcTemplate.queryForList("select id,name from edu_tool_category where parent_id=? order by orders asc ",toolCategory.get("id"));
					for (Map<String,Object> child:children) {
						child.put("toolItems",jdbcTemplate.queryForList("select id,name, memo,icon,download_url downloadUrl,site_url siteUrl from edu_tool_item where tool_category_id=?",child.get("id")));
					}
					toolCategory.put("children",children);
				}

			}
			cache.put(new Element("toolCategoryTree", toolCategories));
		}catch (Exception e){
			e.printStackTrace();
		}
		return toolCategories;
	}

	@Transactional(readOnly = true)
	public List<ToolCategory> findRoots(Integer count) {
		return toolCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "toolCategory", condition = "#useCache")
	public List<ToolCategory> findRoots(Integer count, boolean useCache) {
		return toolCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<ToolCategory> findParents(ToolCategory toolCategory, boolean recursive, Integer count) {
		return toolCategoryDao.findParents(toolCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "toolCategory", condition = "#useCache")
	public List<ToolCategory> findParents(Long toolCategoryId, boolean recursive, Integer count, boolean useCache) {
		ToolCategory toolCategory = toolCategoryDao.find(toolCategoryId);
		if (toolCategoryId != null && toolCategory == null) {
			return Collections.emptyList();
		}
		return toolCategoryDao.findParents(toolCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	public List<ToolCategory> findTree() {
		return toolCategoryDao.findChildren(null, true, null);
	}

	@Transactional(readOnly = true)
	public List<ToolCategory> findChildren(ToolCategory toolCategory, boolean recursive, Integer count) {
		return toolCategoryDao.findChildren(toolCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "toolCategory", condition = "#useCache")
	public List<ToolCategory> findChildren(Long toolCategoryId, boolean recursive, Integer count, boolean useCache) {
		ToolCategory toolCategory = toolCategoryDao.find(toolCategoryId);
		if (toolCategoryId != null && toolCategory == null) {
			return Collections.emptyList();
		}
		return toolCategoryDao.findChildren(toolCategory, recursive, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "tool", "toolCategory" }, allEntries = true)
	public ToolCategory save(ToolCategory toolCategory) {
		Assert.notNull(toolCategory);

		setValue(toolCategory);
		return super.save(toolCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "tool", "toolCategory" }, allEntries = true)
	public ToolCategory update(ToolCategory toolCategory) {
		Assert.notNull(toolCategory);

		setValue(toolCategory);
		for (ToolCategory children : toolCategoryDao.findChildren(toolCategory, true, null)) {
			setValue(children);
		}
		return super.update(toolCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "tool", "toolCategory" }, allEntries = true)
	public ToolCategory update(ToolCategory toolCategory, String... ignoreProperties) {
		return super.update(toolCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "tool", "toolCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "tool", "toolCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "tool", "toolCategory" }, allEntries = true)
	public void delete(ToolCategory toolCategory) {
		super.delete(toolCategory);
	}

	/**
	 * 设置值
	 * 
	 * @param toolCategory
	 *            文章分类
	 */
	private void setValue(ToolCategory toolCategory) {
		if (toolCategory == null) {
			return;
		}
		ToolCategory parent = toolCategory.getParent();
		if (parent != null) {
			toolCategory.setTreePath(parent.getTreePath() + parent.getId() + ToolCategory.TREE_PATH_SEPARATOR);
		} else {
			toolCategory.setTreePath(ToolCategory.TREE_PATH_SEPARATOR);
		}
		toolCategory.setGrade(toolCategory.getParentIds().length);
	}

}
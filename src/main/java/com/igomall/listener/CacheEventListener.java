
package com.igomall.listener;

import com.igomall.entity.Resource;
import com.igomall.entity.wechat.BookItem;
import com.igomall.entity.wechat.ProjectItem;
import com.igomall.entity.wechat.ToolItem;
import com.igomall.service.ResourceService;
import com.igomall.service.wechat.BookItemService;
import com.igomall.service.wechat.ProjectItemService;
import com.igomall.service.wechat.ToolItemService;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listener - 缓存
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Component
public class CacheEventListener extends CacheEventListenerAdapter {

	@Autowired
	private ResourceService resourceService;
	@Autowired
	private BookItemService bookItemService;
	@Autowired
	private ProjectItemService projectItemService;
	@Autowired
	private ToolItemService toolItemService;

	/**
	 * 元素过期调用
	 * 
	 * @param ehcache
	 *            缓存
	 * @param element
	 *            元素
	 */
	public void notifyElementExpired(Ehcache ehcache, Element element) {
		String cacheName = ehcache.getName();
		if (StringUtils.equals(cacheName, Resource.HITS_CACHE_NAME)) {
			Long id = (Long) element.getObjectKey();
			Long hits = (Long) element.getObjectValue();
			Resource resource = resourceService.find(id);
			if (resource != null && hits != null && hits > 0 && hits > resource.getDownloadHits()) {
				resource.setDownloadHits(hits);
				resourceService.update(resource);
			}
		}else if (StringUtils.equals(cacheName, BookItem.HITS_CACHE_NAME)) {
			Long id = (Long) element.getObjectKey();
			Long hits = (Long) element.getObjectValue();
			BookItem bookItem = bookItemService.find(id);
			if (bookItem != null && hits != null && hits > 0 && hits > bookItem.getDownloadHits()) {
				bookItem.setDownloadHits(hits);
				bookItemService.update(bookItem);
			}
		}else if (StringUtils.equals(cacheName, ProjectItem.HITS_CACHE_NAME)) {
			Long id = (Long) element.getObjectKey();
			Long hits = (Long) element.getObjectValue();
			ProjectItem projectItem = projectItemService.find(id);
			if (projectItem != null && hits != null && hits > 0 && hits > projectItem.getDownloadHits()) {
				projectItem.setDownloadHits(hits);
				projectItemService.update(projectItem);
			}
		}else if (StringUtils.equals(cacheName, ToolItem.HITS_CACHE_NAME)) {
			Long id = (Long) element.getObjectKey();
			Long hits = (Long) element.getObjectValue();
			ToolItem toolItem = toolItemService.find(id);
			if (toolItem != null && hits != null && hits > 0 && hits > toolItem.getDownloadHits()) {
				toolItem.setDownloadHits(hits);
				toolItemService.update(toolItem);
			}
		}
	}

}
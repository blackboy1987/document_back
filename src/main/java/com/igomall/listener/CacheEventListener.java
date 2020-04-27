
package com.igomall.listener;

import com.igomall.entity.document.Resource;
import com.igomall.service.document.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;

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
		if (StringUtils.equals(cacheName, Resource.DOWNLOAD_CACHE_NAME)) {
			Long id = (Long) element.getObjectKey();
			Long downloadHits = (Long) element.getObjectValue();
			Resource resource = resourceService.find(id);
			if (resource != null) {
				if(resource.getDownloadHits()==null){
					resource.setDownloadHits(0L);
				}
				if(downloadHits != null && downloadHits > 0 && downloadHits > resource.getDownloadHits()){
					resource.setDownloadHits(downloadHits);
					resourceService.update(resource);
				}
			}
		}
	}

}

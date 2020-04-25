
package com.igomall.listener;

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

	}

}

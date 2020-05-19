
package com.igomall.listener;

import com.igomall.entity.Resource;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.wechat.BookItem;
import com.igomall.entity.wechat.ProjectItem;
import com.igomall.entity.wechat.ToolItem;
import com.igomall.service.ResourceService;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.LessonService;
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
	@Autowired
	private CourseService courseService;
	@Autowired
	private LessonService lessonService;

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
		Long id = (Long) element.getObjectKey();
		Long hits = (Long) element.getObjectValue();
		if (StringUtils.equals(cacheName, Resource.HITS_CACHE_NAME)) {
			Resource resource = resourceService.find(id);
			if (resource != null && hits != null && hits > 0 && hits > resource.getDownloadHits()) {
				resource.setDownloadHits(hits);
				resourceService.update(resource);
			}
		}else if (StringUtils.equals(cacheName, BookItem.HITS_CACHE_NAME)) {
			BookItem bookItem = bookItemService.find(id);
			if (bookItem != null && hits != null && hits > 0 && hits > bookItem.getDownloadHits()) {
				bookItem.setDownloadHits(hits);
				bookItemService.update(bookItem);
			}
		}else if (StringUtils.equals(cacheName, ProjectItem.HITS_CACHE_NAME)) {
			ProjectItem projectItem = projectItemService.find(id);
			if (projectItem != null && hits != null && hits > 0 && hits > projectItem.getDownloadHits()) {
				projectItem.setDownloadHits(hits);
				projectItemService.update(projectItem);
			}
		}else if (StringUtils.equals(cacheName, ToolItem.HITS_CACHE_NAME)) {
			ToolItem toolItem = toolItemService.find(id);
			if (toolItem != null && hits != null && hits > 0 && hits > toolItem.getDownloadHits()) {
				toolItem.setDownloadHits(hits);
				toolItemService.update(toolItem);
			}
		}else if (StringUtils.equals(cacheName, Course.HITS_CACHE_NAME)) {
			Course course = courseService.find(id);
			if (course != null && hits != null && hits > 0 && hits > course.getDownloadHits()) {
				course.setDownloadHits(hits);
				courseService.update(course);
			}
		}else if (StringUtils.equals(cacheName, Lesson.HITS_CACHE_NAME)) {
			Lesson lesson = lessonService.find(id);
			if (lesson != null && hits != null && hits > 0 && hits > lesson.getDownloadHits()) {
				lesson.setDownloadHits(hits);
				lessonService.update(lesson);
			}
		}
	}

}
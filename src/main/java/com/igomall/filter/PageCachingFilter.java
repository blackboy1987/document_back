
package com.igomall.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import net.sf.ehcache.constructs.web.AlreadyCommittedException;
import net.sf.ehcache.constructs.web.AlreadyGzippedException;
import net.sf.ehcache.constructs.web.PageInfo;
import net.sf.ehcache.constructs.web.filter.FilterNonReentrantException;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

/**
 * Filter - 页面缓存
 * 
 * @author blackboy
 * @version 1.0
 */
public class PageCachingFilter extends SimplePageCachingFilter {

	/**
	 * 计算KEY
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return KEY
	 */
	@Override
	protected String calculateKey(HttpServletRequest request) {
		List<String> elements = new ArrayList<>();
		elements.add(request.getMethod());
		elements.add(request.getRequestURI());
		elements.add(request.getQueryString());
		elements.add("UNKNOWN");
		elements.add(request.getHeader("Accept"));
		return StringUtils.join(elements, "|");
	}

	/**
	 * 执行过滤器
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param chain
	 *            FilterChain
	 */
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws AlreadyGzippedException, AlreadyCommittedException, FilterNonReentrantException, LockTimeoutException, Exception {
		if (response.isCommitted()) {
			throw new AlreadyCommittedException("Response already committed before doing buildPage.");
		}
		logRequestHeaders(request);
		PageInfo pageInfo = buildPageInfo(request, response, chain);
		if (response.isCommitted()) {
			throw new AlreadyCommittedException("Response already committed after doing buildPage" + " but before writing response from PageInfo.");
		}
		writeResponse(request, response, pageInfo);
	}

}
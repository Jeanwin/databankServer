package org.utmost.portal.service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
/**
 * set no cache header
 * 
 * @author bull
 *
 */
public class ForceNoCacheFilter implements Filter {
	/**
	 * do filter
	 * set no cache
	 * @param request
	 * @param  response
	 * @param filterChain
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		((HttpServletResponse) response).setHeader("Cache-Control", "no-cache");
		((HttpServletResponse) response).setHeader("Pragma", "no-cache");
		((HttpServletResponse) response).setDateHeader("Expires", -1);
		filterChain.doFilter(request, response);
	}
	/**
	 * close filter call this function
	 */
	public void destroy() {}
	/**
	 * start filter
	 * call this function
	 */
	public void init(FilterConfig filterConfig) throws ServletException {}
}

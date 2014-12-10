package com.bossky.ocean.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 访问过滤器
 * 
 * @author bo
 *
 */
public class AccessFilter implements Filter {
	final static Logger _Logger = LoggerFactory.getLogger(AccessFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String userid = getIpAddr(httpRequest);
		String queryString = httpRequest.getQueryString();
		String content = "[" + httpRequest.getProtocol() + "],["
				+ httpRequest.getRequestURI()
				+ (queryString == null ? "" : "?" + queryString) + "],["
				+ userid + "]";

		_Logger.debug(content);

		chain.doFilter(request, response);

	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}

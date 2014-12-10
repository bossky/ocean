package com.bossky.ocean.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 就一个作用，返回404页面
 * 
 * @author bo
 *
 */
public class _404Filter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		((HttpServletResponse) response).sendError(404);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}

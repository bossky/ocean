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

import com.bossky.ocean.user.OceanUser;

/**
 * 登陆验证过滤器
 * 
 * @author daibo
 * 
 */
public class LoginFilter implements Filter {
	static Logger _Logger = LoggerFactory.getLogger(LoginFilter.class);

	public LoginFilter() {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		OceanUser user = (OceanUser) httpRequest.getSession().getAttribute("user");
		String url = httpRequest.getRequestURI();
		String qStr = httpRequest.getQueryString();
		if (null != qStr) {
			url += "?" + qStr;
		}
		if (null == user) {
			String contextPath = httpRequest.getContextPath();
			response.setContentType("text/html;charset=GBK");
			response.getWriter().print(
					"<script language=javascript>"
							+
							// "alert('请先登录');"+
							"var par = window; try{ par = "
							+ "par.parent;}catch(e){}  par.window.location=\""
							+ contextPath + "/?op=login&&url=" + url
							+ "\";</script>>");
			return;
		}

		if (!user.isAdmin() && url.contains("/admin/")) {
			String contextPath = httpRequest.getContextPath();
			response.setContentType("text/html;charset=GBK");
			response.getWriter().print(
					"<script language=javascript>"
							+ "var par = window; try{ par = "
							+ "par.parent;}catch(e){}  par.window.location=\""
							+ contextPath + "/nofind.jspx\";</script>>");
			return;
		}

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}

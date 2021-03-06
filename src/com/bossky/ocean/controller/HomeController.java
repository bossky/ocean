package com.bossky.ocean.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bossky.ocean.ext.ResultPage;
import com.bossky.ocean.ext.ResultPages;
import com.bossky.ocean.theme.Label;
import com.bossky.ocean.theme.Theme;
import com.bossky.ocean.theme.ThemeService;
import com.bossky.ocean.user.OceanUser;
import com.bossky.ocean.user.UserService;
import com.bossky.util.Util;

/**
 * 主页、根目录页面控制器
 * 
 * @author daibo
 * 
 */
@Controller
public class HomeController {
	@Resource(name = "userService")
	private UserService m_UserService;
	@Resource(name = "themeService")
	private ThemeService m_ThemeService;
	static Logger _Logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * 主页
	 */
	@RequestMapping
	String index(HttpServletRequest request, HttpServletResponse response) {
		ResultPage<Label> labelRP = m_ThemeService.getLabels();
		labelRP.setPageSize(10);
		labelRP.gotoPage(1);
		ResultPage<Theme> themeRP = m_ThemeService.listTheme(null, null, null);
		themeRP.setPageSize(10);
		themeRP.gotoPage(1);
		request.setAttribute("labelList", labelRP);
		request.setAttribute("themeList", themeRP);
		return "index";
	}

	/**
	 * 标签浏览
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String labels(HttpServletRequest request, HttpServletResponse response) {
		ResultPage<Label> labelRP = m_ThemeService.getLabels();
		request.setAttribute("labelList",
				ResultPages.toList(labelRP, ResultPage.LIMIT_NONE));
		return "labels";
	}

	/**
	 * 所有话题
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String themes(HttpServletRequest request, HttpServletResponse response) {
		try {
			String type = Util.toString(request.getParameter("type"));
			String labelId = Util.toString(request.getParameter("labelId"));
			ResultPage<Label> labelRP = m_ThemeService.getLabels();
			labelRP.setPageSize(labelRP.getCount());
			labelRP.gotoPage(1);
			ResultPage<Theme> themeRP = m_ThemeService.listTheme(type, null,
					labelId);
			themeRP.setPageSize(10);
			themeRP.gotoPage(Util.toInt(request.getParameter("p"), 1));
			request.setAttribute("labelList", labelRP);
			request.setAttribute("list", themeRP);
			request.setAttribute("type", type);
			request.setAttribute("labelId", labelId);
			return "themes";
		} catch (Exception e) {
			_Logger.error(e.getMessage());
			return "error";
		}
	}

	/**
	 * 用户登录
	 */
	@RequestMapping
	String login(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String username = Util.toString(request.getParameter("username"));
		String password = Util.toString(request.getParameter("password"));
		if (!Util.isEmpty(username) && !Util.isEmpty(password)) {// 是否为空
			OceanUser user = m_UserService.getUser(username);
			if (user == null) {// 用户账号不存在
				response.getWriter().write("failuser");
				return null;
			} else {
				if (null != user && user.checkPassword(password)) {// 成功
					request.getSession().setAttribute("user", user);// 把user放进session
					response.getWriter().write("success");
					return null;
				} else {// 密码错误
					response.getWriter().write("failpass");
					return null;
				}
			}
		} else {
			response.getWriter().write("isEmpty");
			return null;
		}
	}

	/**
	 * 退出登录
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	String logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.getSession().removeAttribute("user");
		response.sendRedirect("/");
		return null;
	}

	/**
	 * 查看单个话题
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String content(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = Util.toString(request.getParameter("id"));
			String op = Util.toString(request.getParameter("op"));
			OceanUser user = (OceanUser) request.getSession().getAttribute(
					"user");
			Theme theme = m_ThemeService.getTheme(id);
			if (null == theme) {
				return "nofind";
			} else if ("visit".equals(op)) {
				theme.visit();
			}
			if (null != user) {
				request.setAttribute("isCollected", theme.isCollected(user));
				request.setAttribute("isPraised", theme.isPraised(user));
			}
			request.setAttribute("theme", theme);
			return "content";
		} catch (Exception e) {
			_Logger.info(e.getMessage());
			return "error";
		}
	}

	/**
	 * 查看作者主页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String author(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = Util.toString(request.getParameter("id"));
			if (Util.isEmpty(id)) {
				return "nofind";
			}
			OceanUser user = m_UserService.getUser(id);
			if (null == user) {
				return "nofind";
			}

			ResultPage<Theme> list = m_ThemeService.getMyThemes(user);
			list.gotoPage(Util.toInt(request.getParameter("p"), 1));
			request.setAttribute("user", user);
			request.setAttribute("list", list);
			return "author";
		} catch (Exception e) {
			_Logger.info(e.getMessage());
			return "error";
		}
	}

	/**
	 * 找不到文件 不能直接访问jsp
	 */
	@RequestMapping
	void nofind(HttpServletRequest request) {

	}
}

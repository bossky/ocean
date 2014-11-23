package com.ourlinc.ocean.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ourlinc.ocean.theme.Comments;
import com.ourlinc.ocean.theme.Label;
import com.ourlinc.ocean.theme.Reply;
import com.ourlinc.ocean.theme.Theme;
import com.ourlinc.ocean.theme.ThemeService;
import com.ourlinc.ocean.user.User;
import com.ourlinc.ocean.user.UserService;
import com.ourlinc.tern.ResultPage;
import com.ourlinc.tern.ext.ResultPages;
import com.ourlinc.tern.util.Misc;

/**
 * 管理员功能控制器
 * 
 * @author bossky
 * 
 */
@Controller
public class AdminController {

	@Resource(name = "userService")
	private UserService m_UserService;

	@Resource(name = "themeService")
	private ThemeService m_ThemeService;
	final static Logger _Logger = LoggerFactory
			.getLogger(AdminController.class);

	/**
	 * 主页
	 */
	@RequestMapping
	String index(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect("themelist.jspx");
		} catch (IOException e) {
			_Logger.error("出错了", e);
		}
		return null;
	}

	/**
	 * 用户列表
	 */
	@RequestMapping
	String userlist(HttpServletRequest request, HttpServletResponse response) {
		try {
			ResultPage<User> list = m_UserService.listUser(null);
			list.gotoPage(Misc.toInt(request.getParameter("p"), 1));
			request.setAttribute("list", list);
			request.setAttribute("rolelist", User.ALL_ROLE);// 角色列表
			return "admin/userlist";
		} catch (Exception e) {
			_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 注册新用户
	 * 
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	String adduser(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, IOException {
		try {
			String op = Misc.toString(request.getParameter("op"));
			if ("add".equals(op)) {// 添加新用户
				User admin = (User) request.getSession().getAttribute("user");
				if (null == admin) {
					return "nofind";
				}
				if (!admin.isAdmin()) {// 判断是否为管理员在操作
					return "nofind";
				}
				int role = Misc.toInt(
						Misc.toString(request.getParameter("role")), -1);
				String username = Misc.toString(request
						.getParameter("username"));
				String password = Misc.toString(request
						.getParameter("password"));
				Date trainingDate = Misc.parseDate(Misc.toString(request
						.getParameter("trainingDate")));
				if (Misc.isEmpty(username)) {
					response.sendRedirect("userlist.jspx?msg="
							+ URLEncoder.encode("用户名不能为空", "utf-8"));
					return null;
				} else if (Misc.isEmpty(password)) {
					response.sendRedirect("userlist.jspx?msg="
							+ URLEncoder.encode("密码不能为空", "utf-8"));
					return null;
				} else if (-1 == role) {
					response.sendRedirect("userlist.jspx?msg="
							+ URLEncoder.encode("角色不存在", "utf-8"));
					return null;
				} else if (username.length() < 5 || username.length() > 30) {
					response.sendRedirect("userlist.jspx?msg="
							+ URLEncoder.encode("用户名长度只能在5到30之间", "utf-8"));
					return null;
				} else if (password.length() < 6 || password.length() > 16) {
					response.sendRedirect("userlist.jspx?msg="
							+ URLEncoder.encode("密码长度只能在6至16之间", "utf-8"));
					return null;
				}
				int i;
				for (i = 0; i < User.ALL_ROLE.length; i++) {
					if (role == User.ALL_ROLE[i].id) {
						break;
					}
				}
				if (i == User.ALL_ROLE.length) {
					response.sendRedirect("userlist.jspx?msg="
							+ URLEncoder.encode("角色不存在", "utf-8"));
					return null;
				}
				String reg = "[(\")(\')( )]";
				Pattern p = Pattern.compile(reg);
				Matcher mat = p.matcher(username);
				if (mat.find()) {
					response.sendRedirect("userlist.jspx?msg="
							+ URLEncoder.encode("添加失败,用户名存在特殊字符", "utf-8"));
					return null;
				}
				User u = m_UserService.addUser(username, password, role);
				if (null == u) {
					response.sendRedirect("userlist.jspx?msg="
							+ URLEncoder.encode("注册用户失败,用户名已存在!", "utf-8"));
					return null;
				}
				if (null != trainingDate) {
					u.setTrainingDate(trainingDate);
				}
				response.sendRedirect("userlist.jspx?msg="
						+ URLEncoder.encode("注册用户成功!", "utf-8"));
				return null;
			}
			return "admin/adduser";
		} catch (Exception e) {
			_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 修改培训日期
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	String updateuser(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String op = Misc.toString(request.getParameter("op"));
			if ("update".equals(op)) {// 修改
				User admin = (User) request.getSession().getAttribute("user");
				if (null == admin) {
					return "login";
				}
				if (!admin.isAdmin()) {// 判断是否为管理员在操作
					return "nofind";
				}
				String id = Misc.toString(request.getParameter("id"));
				User user = m_UserService.getUser(id);
				if (null == user) {
					return "nofind";
				}
				Date trainingDate = Misc.parseDate(Misc.toString(request
						.getParameter("trainingDate")));
				user.setTrainingDate(trainingDate);// 修改培训日期
				response.sendRedirect("userlist.jspx?msg="
						+ URLEncoder.encode("修改成功", "UTF-8"));
				return null;
			}
			return "admin/updateuser";
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 重置密码
	 */
	@RequestMapping
	String resetpass(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String op = request.getParameter("op");
			if ("resetpass".equals(op)) {
				User admin = (User) request.getSession().getAttribute("user");
				if (null == admin) {
					return "nofind";
				}
				if (!admin.isAdmin()) {// 判断是否为管理员在操作
					return "nofind";
				}
				String id = request.getParameter("id");
				User user = m_UserService.getUser(id);
				if (null == user) {
					return "login";
				}
				String password = request.getParameter("password");
				if (Misc.isEmpty(password)) {
					response.sendRedirect("userlist.jspx?msg="
							+ URLEncoder.encode("重置失败,密码不能为空", "UTF-8"));
					return null;
				} else if (password.length() < 6 || password.length() > 16) {
					response.sendRedirect("userlist.jspx?msg="
							+ URLEncoder.encode("重置失败,密码长度错误", "UTF-8"));
					return null;
				}
				user.resetPassword(password);
				response.sendRedirect("userlist.jspx?msg="
						+ URLEncoder.encode("重置密码成功", "UTF-8"));
				return null;
			}
			return "admin/resetpass";
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 拉黑/恢复用户
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	String pullblack(HttpServletRequest request, HttpServletResponse response) {
		try {
			String op = Misc.toString(request.getParameter("op"));
			if ("pullblack".equals(op)) {// 拉黑
				User admin = (User) request.getSession().getAttribute("user");
				if (null == admin) {
					return "nofind";
				}
				if (!admin.isAdmin()) {// 判断是否为管理员在操作
					return "nofind";
				}
				String id = Misc.toString(request.getParameter("id"));
				User user = m_UserService.getUser(id);
				if (null == user) {
					return "login";
				}
				user.pullBlack();
				response.getWriter().write("shield");
			} else if ("recover".equals(op)) {// 恢复
				User admin = (User) request.getSession().getAttribute("user");
				if (null == admin) {
					return "nofind";
				}
				if (!admin.isAdmin()) {// 判断是否为管理员在操作
					return "nofind";
				}
				String id = Misc.toString(request.getParameter("id"));
				User user = m_UserService.getUser(id);
				if (null == user) {
					return "login";
				}
				user.rescover();
				response.getWriter().write("recover");
			}
			return null;
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 添加标签
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String addlabel(HttpServletRequest request, HttpServletResponse response) {
		try {
			String op = Misc.toString(request.getParameter("op"));
			if ("add".equals(op)) {
				String name = Misc.toString(request.getParameter("name"));
				String parentId = Misc.toString(request
						.getParameter("parentId"));
				if (Misc.isEmpty(name)) {
					response.sendRedirect("labellist.jspx?msg="
							+ URLEncoder.encode("标签名不能为空!", "utf-8"));
					return null;
				}
				if (name.length() > 18) {
					response.sendRedirect("labellist.jspx?msg="
							+ URLEncoder.encode("标签名不能大于18个字符!", "utf-8"));
					return null;
				}
				Label label = m_ThemeService.getLabel(parentId);
				if (null != label) {
					label.addChildLabel(name);
				} else {
					m_ThemeService.addLabel(name);
				}
				response.sendRedirect("labellist.jspx?msg="
						+ URLEncoder.encode("添加成功!", "utf-8"));
				return null;
			}
			/*
			 * else{ request.setAttribute("labelList",
			 * ResultPages.toList(m_ThemeService
			 * .getLabels(),ResultPage.LIMIT_NONE)); }
			 */
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}
		return "admin/addlabel";
	}

	/**
	 * 标签列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String labellist(HttpServletRequest request, HttpServletResponse response) {
		try {
			ResultPage<Label> list = m_ThemeService.getLabels();
			list.gotoPage(Misc.toInt(request.getParameter("p"), 1));
			request.setAttribute("list", list);
			// request.setAttribute("labelList",
			// ResultPages.toList(m_ThemeService.getLabels(),ResultPage.LIMIT_NONE));
			return "admin/labellist";
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 删除标签
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String deletelabel(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = Misc.toString(request.getParameter("id"));
			if (Misc.isEmpty(id)) {
				response.sendRedirect("labellist.jspx?msg="
						+ URLEncoder.encode("删除失败", "utf-8"));
				return null;
			}
			Label label = m_ThemeService.getLabel(id);
			if (null == label) {
				response.sendRedirect("labellist.jspx?msg="
						+ URLEncoder.encode("删除失败", "utf-8"));
				return null;
			}
			m_ThemeService.removeLabel(label);
			response.sendRedirect("labellist.jspx?msg="
					+ URLEncoder.encode("删除成功", "utf-8"));
			return null;
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}

	}

	/**
	 * 话题列表
	 */
	@RequestMapping
	String themelist(HttpServletRequest request, HttpServletResponse response) {
		try {
			String op = Misc.toString(request.getParameter("op"));
			String userName = Misc.toString(request.getParameter("userName"));
			String labelId = Misc.toString(request.getParameter("labelId"));
			if ("update".equals(op)) {
				String id = Misc.toString(request.getParameter("id"));
				Theme theme = m_ThemeService.getTheme(id);
				if (null == theme) {
					return "nofind";
				}
				String[] labels = request.getParameterValues("label");
				if (null != labels && labels.length != 0) {
					List<String> list = new ArrayList<String>();
					for (String lid : labels) {
						addParentLabel(list, lid);
					}
					theme.updateLabelList(list);
				} else {
					theme.updateLabelList(new ArrayList<String>());
				}
				request.setAttribute("msg", "更新成功");
			}
			ResultPage<Theme> list = m_ThemeService.listTheme(null, userName,
					labelId);
			list.gotoPage(Misc.toInt(request.getParameter("p"), 1));
			request.setAttribute("list", list);
			request.setAttribute("userName", userName);
			request.setAttribute("labelId", labelId);
			request.setAttribute("labelList", ResultPages.toList(
					m_ThemeService.getLabels(), ResultPage.LIMIT_NONE));
			return "admin/themelist";
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}

	}

	/**
	 * 屏蔽/恢复话题
	 */
	@RequestMapping
	String shieldtheme(HttpServletRequest request, HttpServletResponse response) {
		try {
			String op = Misc.toString(request.getParameter("op"));
			if ("shield".equals(op)) {// 屏蔽
				String id = Misc.toString(request.getParameter("id"));
				Theme theme = m_ThemeService.getTheme(id);
				theme.shield();
				response.getWriter().write("shield");

			} else if ("recover".equals(op)) {// 恢复
				String id = Misc.toString(request.getParameter("id"));
				Theme theme = m_ThemeService.getTheme(id);
				theme.recover();
				response.getWriter().write("recover");
			}
			return null;
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 递归添加标签
	 * 
	 * @param list
	 * @param l
	 */
	private void addParentLabel(List<String> list, String id) {
		list.add(id);
		Label l = m_ThemeService.getLabel(id);
		String parentId = l.getParentId();
		if (!Misc.isEmpty(parentId) && !list.contains(parentId)) {
			addParentLabel(list, parentId);
		}
	}

	/**
	 * 评论列表
	 */
	@RequestMapping
	String commentlist(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userName = Misc.toString(request.getParameter("userName"));
			String themeTitle = Misc.toString(request
					.getParameter("themeTitle"));
			ResultPage<Comments> list = m_ThemeService.listComments(userName,
					themeTitle);
			list = ResultPages.toSortResultPage(list, Comments.TIME_SORT,
					ResultPage.LIMIT_NONE);
			list.gotoPage(Misc.toInt(request.getParameter("p"), 1));
			request.setAttribute("userName", userName);
			request.setAttribute("themeTitle", themeTitle);
			request.setAttribute("list", list);
			return "admin/commentlist";
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 回复列表
	 */
	@RequestMapping
	String replylist(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userName = Misc.toString(request.getParameter("userName"));
			String themeTitle = Misc.toString(request
					.getParameter("themeTitle"));
			ResultPage<Reply> list = m_ThemeService.listReply(userName,
					themeTitle);
			list = ResultPages.toSortResultPage(list, Reply.TIME_SORT,
					ResultPage.LIMIT_NONE);
			list.gotoPage(Misc.toInt(request.getParameter("p"), 1));
			request.setAttribute("userName", userName);
			request.setAttribute("themeTitle", themeTitle);
			request.setAttribute("list", list);
			return "admin/replylist";
		} catch (Exception e) {
			_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 屏蔽/恢复评论
	 */
	@RequestMapping
	String shieldcomments(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String op = Misc.toString(request.getParameter("op"));
			String id = Misc.toString(request.getParameter("id"));
			Comments c = m_ThemeService.getComments(id);
			if (null == c) {
				return null;
			}
			if ("shield".equals(op)) {// 屏蔽
				c.shield();
				response.getWriter().write("shield");
			} else if ("recover".equals(op)) {// 恢复
				c.recover();
				response.getWriter().write("recover");
			}
			return null;
		} catch (Exception e) {
			_Logger.info(e.getMessage());
			return "error";
		}
	}

	/**
	 * 屏蔽/恢复回复
	 */
	@RequestMapping
	String shieldreply(HttpServletRequest request, HttpServletResponse response) {
		try {
			String op = Misc.toString(request.getParameter("op"));
			String id = Misc.toString(request.getParameter("id"));
			Reply c = m_ThemeService.getReply(id);
			if (null == c) {
				return null;
			}
			if ("shield".equals(op)) {// 屏蔽
				c.shield();
				response.getWriter().write("shield");
			} else if ("recover".equals(op)) {// 恢复
				c.recover();
				response.getWriter().write("recover");
			}
			return null;
		} catch (Exception e) {
			_Logger.info(e.getMessage());
			return "error";
		}
	}

}

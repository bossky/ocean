package com.ourlinc.ocean.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ourlinc.ocean.theme.Collect;
import com.ourlinc.ocean.theme.Comments;
import com.ourlinc.ocean.theme.Label;
import com.ourlinc.ocean.theme.Reply;
import com.ourlinc.ocean.theme.Theme;
import com.ourlinc.ocean.theme.ThemeService;
import com.ourlinc.ocean.user.Message;
import com.ourlinc.ocean.user.User;
import com.ourlinc.tern.ResultPage;
import com.ourlinc.tern.ext.ResultPages;
import com.ourlinc.tern.util.Misc;

/**
 * 用户功能控制器
 * 
 * @author Zhao_Gq
 * 
 */
@Controller
public class UserController {
	static Logger _Logger = LoggerFactory.getLogger(UserController.class);

	@Resource(name = "themeService")
	private ThemeService m_ThemeService;

	/**
	 * 主页
	 */
	@RequestMapping
	String index(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect("mytheme.jspx");
		} catch (IOException e) {
			_Logger.error("出错了", e);
		}
		return null;
	}

	/**
	 * 发表话题
	 */
	@RequestMapping
	String addtheme(HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = (User) request.getSession().getAttribute("user");
			if (null == user) {
				return "login";
			}
			if (user.isBlack()) {
				response.sendRedirect("/index.jspx?msg="
						+ URLEncoder.encode("你被拉黑,无法发表话题", "UTF-8"));
				return null;
			}
			String op = Misc.toString(request.getParameter("op"));
			String msg = null;
			if ("add".equals(op)) {
				String title = Misc.toString(request.getParameter("title"));
				String content = Misc.toString(request.getParameter("content"));
				if (Misc.isEmpty(title.trim())) {
					msg = "标题不能为空";
				} else if (Misc.isEmpty(content.trim())) {
					msg = "内容不能为空";
				} else if (title.length() > 60) {
					msg = "标题长度必须小于60";
				}
				if (null != msg) {
					response.sendRedirect("addtheme.jspx?msg="
							+ URLEncoder.encode(msg, "UTF-8"));
					return null;
				} else {
					String[] labels = request.getParameterValues("label");
					// -_-|| 这里是能过user在调用m_ThemeService.createTheme的方法
					// Theme theme = m_ThemeService.createTheme(user,
					// title,content);
					Theme theme = user.addTheme(title, content);
					if (null != labels && labels.length != 0) {
						List<String> list = new ArrayList<String>();
						for (String id : labels) {
							addParentLabel(list, id);
						}
						theme.updateLabelList(list);
					}
					response.sendRedirect("mytheme.jspx?msg="
							+ URLEncoder.encode("添加文章成功!", "UTF-8"));
					return null;
				}
			} else {
				request.setAttribute("labelList", ResultPages.toList(
						m_ThemeService.getLabels(), ResultPage.LIMIT_NONE));
				request.setAttribute("msg", msg);
				return "user/addtheme";
			}
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}

	}

	/**
	 * 修改话题标签
	 */
	@RequestMapping
	String updatetheme(HttpServletRequest request, HttpServletResponse response) {
		try {
			String op = Misc.toString(request.getParameter("op"));
			String id = Misc.toString(request.getParameter("id"));
			Theme theme = m_ThemeService.getTheme(id);
			User user = (User) request.getSession().getAttribute("user");
			if (null == user) {
				return "login";
			}
			if (null == theme
					|| !theme.getUserId().equals(user.getId().getId())) {
				return "nofind";
			}
			if ("update".equals(op)) {
				String[] labels = request.getParameterValues("label");
				if (null != labels && labels.length != 0) {
					List<String> list = new ArrayList<String>();
					for (String lid : labels) {
						addParentLabel(list, lid);
					}
					theme.updateLabelList(list);
				}
				response.sendRedirect("mytheme.jspx?msg="
						+ URLEncoder.encode("更新成功", "utf-8"));
				return null;

			} else {
				ResultPage<Label> labelRP = m_ThemeService.getLabels();
				labelRP.setPageSize(labelRP.getCount());
				labelRP.gotoPage(1);
				request.setAttribute("labelList", labelRP);
			}
			request.setAttribute("theme", theme);
			return "user/updatetheme";
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

		Label l = m_ThemeService.getLabel(id);
		if (l != null) {
			list.add(id);
			String parentId = l.getParentId();
			if (!Misc.isEmpty(parentId) && !list.contains(parentId)) {
				addParentLabel(list, parentId);
			}
		}
	}

	/**
	 * 话题列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String themeList(HttpServletRequest request, HttpServletResponse response) {
		try {
			ResultPage<Theme> list = m_ThemeService.listTheme(null, null, null);
			list.gotoPage(Misc.toInt(request.getParameter("p"), 1));
			request.setAttribute("list", list);
			return "user/themelist";
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 收藏或赞话题
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String operationtheme(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String op = Misc.toString(request.getParameter("op"));
			String id = Misc.toString(request.getParameter("id"));
			PrintWriter out = response.getWriter();
			Theme theme = m_ThemeService.getTheme(id);
			User user = (User) request.getSession().getAttribute("user");
			if (null == theme || null == user) {
				String str = "{root: [{result:'null',value:'0'}]} ";
				out.write(str);
				return null;
			}
			if (theme.getUserId().equals(user.getId().getId())) {
				String str = "{root: [{result:'self" + op + "',value:'0'}]} ";
				out.write(str);
				return null;
			}
			if ("collect".equals(op)) {
				if (theme.isCollected(user)) {
					String str = "{root: [{result:'isCollected',value:'0'}]} ";
					out.write(str);
					return null;
				} else {
					String str = "{root: [{result:'collect',value:'"
							+ theme.collectTheme(user) + "'}]} ";
					out.write(str);
					return null;
				}

			} else if ("praise".equals(op)) {
				if (theme.isPraised(user)) {
					String str = "{root: [{result:'isPraised',value:'0'}]} ";
					out.write(str);
					return null;
				} else {
					String str = "{root: [{result:'praise',value:'"
							+ theme.praiseTheme(user) + "'}]} ";
					out.write(str);
					return null;
				}
			} else if ("cannalCollect".equals(op)) {
				String str = "{root: [{result:'cannalCollect',value:'"
						+ theme.cannalCollected(user) + "'}]} ";
				out.write(str);
				return null;
			}
			out.write("{root: [{result:'praise',value:'"
					+ theme.praiseTheme(user) + "'}]}");
			return null;
		} catch (Exception e) {

				_Logger.error("出错了", e);
			return "error";
		}

	}

	/**
	 * 取消收藏话题
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String cannalcollcet(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = Misc.toString(request.getParameter("id"));
			Theme theme = m_ThemeService.getTheme(id);
			User user = (User) request.getSession().getAttribute("user");
			if (null != theme && null != user) {
				theme.cannalCollected(user);
				response.sendRedirect("mycollecttheme.jspx?msg="
						+ URLEncoder.encode("取消成功", "utf-8"));
			}
			return null;
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}

	}

	/**
	 * 我的话题
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String mytheme(HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = (User) request.getSession().getAttribute("user");
			String op = Misc.toString(request.getParameter("op"));
			if (null == user) {
				return "login";
			}
			if ("update".equals(op)) {
				String id = Misc.toString(request.getParameter("id"));
				Theme theme = m_ThemeService.getTheme(id);
				if (null == theme
						|| !theme.getUserId().equals(user.getId().getId())) {
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
			ResultPage<Theme> list = user.getMyThemes();
			// ResultPage<Theme> list = m_ThemeService.getMyThemes(user);
			list.gotoPage(Misc.toInt(request.getParameter("p"), 1));
			request.setAttribute("list", list);
			return "user/mytheme";

		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 我收藏的话题
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String mycollecttheme(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User user = (User) request.getSession().getAttribute("user");
			if (null == user) {
				return "login";
			}
			// ResultPage<Collect> list = m_ThemeService.getCollectThemes(user);
			ResultPage<Collect> list = user.getCollectThemes();
			list.gotoPage(Misc.toInt(request.getParameter("p"), 1));
			request.setAttribute("list", list);
			return "user/mycollecttheme";
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 我的消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String mymessage(HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = (User) request.getSession().getAttribute("user");
			if (null == user) {
				return "login";
			} else {
				ResultPage<Message> list = user.getMessage();
				list.gotoPage(Misc.toInt(request.getParameter("p"), 1));
				request.setAttribute("list", list);
			}
			return "user/mymessage";
		} catch (Exception e) {
				_Logger.error("出错了", e);
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 评论回复话题
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	String commentstheme(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User user = (User) request.getSession().getAttribute("user");
			String op = Misc.toString(request.getParameter("op"));
			String themeId = Misc.toString(request.getParameter("themeId"));
			String commentsId = Misc.toString(request
					.getParameter("commentsId"));
			String replyId = Misc.toString(request.getParameter("replyId"));
			String content = Misc.toString(request.getParameter("content"));
			String msg = null;
			Theme theme = m_ThemeService.getTheme(themeId);
			if (null == user) {
				return "login";
			}
			if (null == theme) {
				return "nofind";
			}
			if (user.isBlack()) {
				msg = "你被拉黑,无法评论话题";

			} else if ("comments".equals(op)) {
				Comments c = theme.commentTheme(user, content);
				if (null == c) {
					msg = "评论失败";
				} else {
					msg = "评论成功";
				}
			} else if ("replay".equals(op)) {
				Comments comments = theme.getComments(commentsId);
				if (null != comments) {
					Reply r = comments.reply(user, content);
					if (null == r) {
						msg = "回复失败";
					} else {
						msg = "回复成功";
					}
				}
			} else if ("replyAgain".equals(op)) {
				Reply r = m_ThemeService.getReply(replyId);
				if (null == r) {
					msg = "回复失败";
				} else {
					r.replyAgain(user, content);
					msg = "回复成功";
				}
			}

			if (null != msg) {
				response.sendRedirect("/content.jspx?id=" + themeId + "&&msg="
						+ URLEncoder.encode(msg, "utf-8"));
				return null;
			} else {
				response.sendRedirect("/content.jspx?id=" + themeId);
				return null;
			}
		} catch (Exception e) {

				_Logger.error("出错了", e);
			e.printStackTrace();
			return "error";
		}

	}

	/**
	 * 个人信息
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	String info(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String op = Misc.toString(request.getParameter("op"));
			if ("update".equals(op)) {
				User user = (User) request.getSession().getAttribute("user");
				if (null == user) {
					return "nofind";
				}
				String nickname = Misc.toString(request
						.getParameter("nickname"));// 昵称
				String phone = Misc.toString(request.getParameter("phone"));// 手机
				String qq = Misc.toString(request.getParameter("qqnum"));// qq
				String school = Misc.toString(request.getParameter("school"));// 毕业学院
				String education = Misc.toString(request
						.getParameter("education"));// 最高学历
				String job = Misc.toString(request.getParameter("job"));// 工作职位
				String signature = Misc.toString(request
						.getParameter("signature"));// 个性签名
				String msg = null;
				if (Misc.isEmpty(nickname)) {
					msg = "昵称不能为空!";
				} else if (nickname.trim().length() > 15) {
					msg = "昵称字符超长";
				} else if (phone.trim().length() > 15) {
					msg = "电话字符超长";
				} else if (qq.trim().length() > 15) {
					msg = "QQ字符超超长";
				} else if (school.trim().length() > 30) {
					msg = "毕业院校字符超长";
				} else if (education.trim().length() > 15) {
					msg = "最高学历字符超长";
				} else if (job.trim().length() > 20) {
					msg = "工作职位字符超长";
				} else if (signature.length() > 200) {
					msg = "个性签名字符超长";
				} else if (qq.trim().length() > 0 && !Misc.isNumber(qq)) {
					msg = "QQ只能是数字";
				} else if (phone.trim().length() > 0 && !Misc.isNumber(phone)) {
					msg = "手机只能是数字";
				} else if (phone.trim().length() > 0
						&& phone.trim().length() != 11) {
					msg = "手机号码长度出错！";
				}
				if (null != msg) {
					response.sendRedirect("info.jspx?msg="
							+ URLEncoder.encode(msg, "utf-8"));
					return null;
				}
				user.setPhone(phone.trim());
				user.setNickName(nickname.trim());
				user.setQQ(qq.trim());
				user.setJob(job.trim());
				user.setEducation(education.trim());
				user.setSignature(signature);
				user.setSchool(school.trim());
				request.getSession().setAttribute("user", user);
				response.sendRedirect("info.jspx?msg="
						+ URLEncoder.encode("更新成功", "UTF-8"));
				return null;
			}
			return "user/info";
		} catch (Exception e) {
				_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 修改密码
	 */
	@RequestMapping
	String updatepass(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String op = Misc.toString(request.getParameter("op"));
			if ("update".equals(op)) {
				User user = (User) request.getSession().getAttribute("user");
				if (null == user) {
					return "nofind";
				}
				String oldpass = Misc.toString(request.getParameter("oldpass"));// 旧密码
				String newpass = Misc.toString(request.getParameter("newpass"));// 新密码
				String renewpass = Misc.toString(request
						.getParameter("renewpass"));// 重复新密码
				if (oldpass.isEmpty() || newpass.isEmpty()
						|| renewpass.isEmpty()) {// 如果密码为空
					response.sendRedirect("updatepass.jspx?msg="
							+ URLEncoder.encode("修改密码失败,密码不能为空!", "utf-8"));
					return null;
				}
				// 判断密码长度
				else if ((newpass.length() < 6 || newpass.length() > 16)
						|| (renewpass.length() < 6 || renewpass.length() > 16)) {
					response.sendRedirect("updatepass.jspx?msg="
							+ URLEncoder.encode("失败,新密码长度只能在6到16之间!", "utf-8"));
					return null;
				} else if (!(newpass.equals(renewpass))) {// 判断新密码与重复密码是否相同
					response.sendRedirect("updatepass.jspx?msg="
							+ URLEncoder.encode("修改失败,新密码与重复密码不相同!", "utf-8"));
					return null;
				} else if (user.updatePassword(oldpass, newpass)) {// 修改密码
					response.sendRedirect("info.jspx?msg="
							+ URLEncoder.encode("修改密码成功!", "utf-8"));
					return null;
				} else {
					response.sendRedirect("updatepass.jspx?msg="
							+ URLEncoder.encode("修改密码失败,原密码错误!", "utf-8"));
					return null;
				}
			}
			return "user/updatepass";
		} catch (Exception e) {
			_Logger.error("出错了", e);
			return "error";
		}
	}

	/**
	 * 选择标签显示页面
	 */
	@RequestMapping
	String selectlabel(HttpServletRequest request, HttpServletResponse response) {
		ResultPage<Label> labelRP = m_ThemeService.getLabels();
		request.setAttribute("labelList",
				ResultPages.toList(labelRP, ResultPage.LIMIT_NONE));

		return "user/selectlabel";
	}

	/**
	 * 操作成功提示
	 */
	@RequestMapping
	String okmsg(HttpServletRequest request, HttpServletResponse response) {
		String msg = Misc.toString(request.getParameter("msg"));
		request.setAttribute("msg", msg);
		return "user/okmsg";
	}

}

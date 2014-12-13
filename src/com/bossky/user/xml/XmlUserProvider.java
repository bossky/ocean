package com.bossky.user.xml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bossky.user.Right;
import com.bossky.user.Role;
import com.bossky.user.User;
import com.bossky.user.UserProvider;
import com.bossky.util.IndexList;
import com.bossky.util.Util;

/**
 * Xml用户提供者
 * 
 * @author bo
 *
 */
@SuppressWarnings("unchecked")
public class XmlUserProvider implements UserProvider {
	final static Logger _Logger = LoggerFactory
			.getLogger(XmlUserProvider.class);
	/** 权限集合 */
	IndexList<XmlRight> rightList;
	/** 角色集合 */
	IndexList<XmlRole> roleList;
	/** 用户集合 */
	IndexList<XmlUser> userList;

	public XmlUserProvider(String path) {
		rightList = IndexList.getInstance(XmlRight.class);
		roleList = IndexList.getInstance(XmlRole.class);
		userList = IndexList.getInstance(XmlUser.class);
		load(path);
	}

	public void load(String path) {
		try {
			SAXReader reader = new SAXReader();
			// 获取xml文件的输入流
			InputStream inStream = new FileInputStream(path);
			// 取得代表文档的Document对象
			Document document = reader.read(inStream);
			Element root = document.getRootElement();
			loadRights(root);
			loadRoles(root);
			loadUsers(root);
		} catch (Exception e) {
			_Logger.error("加载文档出错误,文档路径" + path);
		}
	}

	private void loadUsers(Element root) {
		List<Element> users = (List<Element>) root.elements("user");
		for (Element e : users) {
			loadUser(e);
		}

	}

	private User loadUser(Element e) {
		int id = Util.toInt16(e.attributeValue("id"), -1);
		if (id == -1) {
			_Logger.warn("忽略id为null的配置,内容:" + e.asXML());
			return null;
		}
		String loginName = e.attributeValue("loginname");
		if (null == loginName) {
			loginName = e.attributeValue("name");
		}
		if (null == loginName) {
			_Logger.warn("忽略loginName为null的配置,内容:" + e.asXML());
			return null;
		}
		String password = e.attributeValue("password");
		if (null == password) {
			_Logger.warn("忽略password为null,使用loginName作为密码,内容:" + e.asXML());
			password = Util.MD5(loginName);
		}
		List<Element> rolesElements = e.elements("role");
		List<Role> roles = new ArrayList<Role>(rolesElements.size());
		for (Element role : rolesElements) {
			Role r = loadRole(role);
			if (null != r) {
				roles.add(r);
			}
		}
		List<Element> rightsElemet = e.elements("right");
		List<Right> rights = new ArrayList<Right>(rightsElemet.size());
		for (Element right : rightsElemet) {
			Right r = loadRight(right);
			if (null != r) {
				rights.add(r);
			}
		}
		XmlUser user = XmlUser.valueOf(id, loginName, password);
		user.setRoles(roles);
		user.setRights(rights);
		User original = userList.put(user);
		if (null != original) {
			_Logger.warn("用户" + original + "被" + user + "替换");
		}
		return user;
	}

	/**
	 * 加载角色集合
	 * 
	 * @param root
	 */
	private void loadRoles(Element root) {
		List<Element> rights = (List<Element>) root.elements("role");
		for (Element e : rights) {
			loadRole(e);
		}

	}

	/**
	 * 加载角色
	 * 
	 * @param e
	 * @return
	 */
	private Role loadRole(Element e) {
		String ref = e.attributeValue("ref");
		int id;
		if (null != ref) {
			id = Util.toInt16(ref, -1);
			XmlRole r = roleList.get(id);
			if (null == r) {
				_Logger.warn("忽略ref无对应角色的配置,内容:" + e.asXML());
			}
			return r;
		}
		id = Util.toInt16(e.attributeValue("id"), -1);
		String name = e.attributeValue("name");
		String desc = e.attributeValue("desc");
		XmlRole role = XmlRole.valueOf(id, name, desc);
		List<Element> rights = (List<Element>) e.elements("right");
		List<Right> list = new ArrayList<Right>();
		for (Element right : rights) {
			Right r = loadRight(right);
			if (null != r) {
				list.add(r);
			}
		}
		role.setRights(list);
		Role original = roleList.put(role);
		if (null != original) {
			_Logger.warn("角色" + original + "被" + role + "替换");
		}
		return role;
	}

	/**
	 * 加载权限集合
	 * 
	 * @param root
	 */
	private void loadRights(Element root) {
		List<Element> rights = (List<Element>) root.elements("right");
		for (Element e : rights) {
			loadRight(e);
		}
	}

	/**
	 * 加载权限
	 * 
	 * @param e
	 * @return
	 */
	private Right loadRight(Element e) {
		String ref = e.attributeValue("ref");
		int id;
		if (null != ref) {
			id = Util.toInt16(ref, -1);
			XmlRight r = rightList.get(id);
			if (null == r) {
				_Logger.warn("忽略ref无对应权限的配置,内容:" + e.asXML());
			}
			return r;
		}
		id = Util.toInt16(e.attributeValue("id"), -1);
		if (id == -1) {
			_Logger.warn("忽略id为null的配置,内容:" + e.asXML());
			return null;
		}
		String name = e.attributeValue("name");
		String rule = e.attributeValue("rule");
		if (Util.isEmpty(rule)) {
			_Logger.warn("忽略rule为null的配置,内容:" + e.asXML());
			return null;
		}
		String desc = e.attributeValue("desc");
		String type = e.attributeValue("type");
		if (null == type) {
			type = Right.TYPE_ALLOW;
		}
		XmlRight r = XmlRight.valueOf(id, type, name, desc, rule);
		XmlRight original = rightList.put(r);
		if (null != original) {
			_Logger.warn("权限" + original + "被" + r + "替换");
		}
		return r;
	}

	public static void main(String[] args) {
	}

	@Override
	public User getUser(String id) {
		int index = Util.toInt(id, -1);
		return userList.get(index);
	}

	@Override
	public User getUserByLoginToken(String loginToken) {
		for (User u : userList) {
			if (Util.eq(loginToken, u.getLoginName())) {
				return u;
			}
			if (Util.eq(loginToken, u.getPhone())) {
				return u;
			}
			if (Util.eq(loginToken, u.getEmail())) {
				return u;
			}
		}
		return null;
	}

	@Override
	public List<User> list() {
		List<User> list = new ArrayList<User>(userList.size());
		Iterator<XmlUser> it = userList.iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
}

package com.bossky.user.xml;

import java.util.List;

import com.bossky.user.Right;
import com.bossky.user.Role;
import com.bossky.user.impl.UserAdapter;
import com.bossky.util.Indexable;
import com.bossky.util.Util;

/**
 * xml用户　
 * 
 * @author bo
 *
 */
public class XmlUser extends UserAdapter implements Indexable {
	/** 用户id */
	protected int id;
	/** 登陆名 */
	protected String loginName;
	/** 密码 */
	protected String password;
	/** 角色 */
	protected List<Role> roles;
	/** 权限 */
	protected List<Right> rights;

	protected XmlUser(int id, String loginName, String password) {
		this.id = id;
		this.loginName = loginName;
		this.password = password;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}

	@Override
	public int index() {
		return id;
	}

	@Override
	public String getId() {
		return String.valueOf(id);
	}

	@Override
	public String getLoginName() {
		return loginName;
	}

	@Override
	public boolean checkPassword(String password) {
		return this.password.equals(Util.MD5(password));
	}

	/**
	 * 实例化一个用户　
	 * 
	 * @param id
	 *            用户id
	 * @param loginName
	 *            登陆名
	 * @param password
	 *            加密后的密码
	 * @return
	 */
	public static XmlUser valueOf(int id, String loginName, String password) {
		return new XmlUser(id, loginName, password);
	}

	@Override
	public String toString() {
		return "[id=" + id + ",loginName=" + loginName + "]";
	}

}

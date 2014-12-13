package com.bossky.user.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.bossky.user.Right;
import com.bossky.user.Role;
import com.bossky.user.User;
import com.bossky.util.Util;

/**
 * 基本的用户
 * 
 * @author bo
 *
 */
public class SimpleUser implements User {
	/** 用户id */
	@Resource
	protected String id;
	/** 登陆名 */
	@Resource
	protected String loginName;
	/** 密码 */
	@Resource
	protected String password;
	/** 姓名 */
	@Resource
	protected String realName;
	/** 电话 */
	@Resource
	protected String phone;
	/** 邮箱 */
	@Resource
	protected String email;
	/** 昵称 */
	@Resource
	protected String nickName;
	/** 创建时间 */
	@Resource
	protected Date createTime;
	/** 来源 */
	@Resource
	protected String from;

	public SimpleUser(String loginName, String password) {
		this.loginName = loginName;
		this.password = Util.MD5(password);
		this.createTime = new Date();
	}

	@Override
	public boolean changeLoginName(String oldLoginName, String newLoginName) {
		if (loginName.equals(oldLoginName)) {
			loginName = newLoginName;
			return true;
		}
		return false;
	}

	@Override
	public boolean checkPassword(String password) {
		return password.equals(Util.MD5(password));
	}

	@Override
	public boolean changePassword(String oldPassword, String newPassowrd) {
		if (checkPassword(oldPassword)) {
			password = newPassowrd;
			return true;
		}
		return false;
	}

	@Override
	public void resetPassword(String password) {
		this.password = password;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getLoginName() {
		return loginName;
	}

	@Override
	public String getUserName() {
		return getLoginName();
	}

	@Override
	public String getRealName() {
		return realName;
	}

	@Override
	public String getNickName() {
		return nickName;
	}

	@Override
	public String getPhone() {
		return phone;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public String getFrom() {
		return from;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@Override
	public List<Role> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Right> getRights() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasRight(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRight(String url) {
		// TODO Auto-generated method stub
		return false;
	}

}

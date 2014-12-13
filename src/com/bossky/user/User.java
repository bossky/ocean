package com.bossky.user;

import java.util.Date;
import java.util.List;

/**
 * 用户接口
 * 
 * @author bo
 *
 */
public interface User {
	/**
	 * 用户id
	 * 
	 * @return
	 */
	public String getId();

	/**
	 * 获取用户登陆名
	 * 
	 * @return
	 */
	public String getLoginName();

	/**
	 * 用户名
	 * 
	 * @return
	 */
	public String getUserName();

	/**
	 * 获取真实的用户名
	 * 
	 * @return
	 */
	public String getRealName();

	/**
	 * 获取用户昵称
	 * 
	 * @return
	 */
	public String getNickName();

	/**
	 * 获取用户手机
	 * 
	 * @return
	 */
	public String getPhone();

	/**
	 * 获取用户邮箱
	 * 
	 * @return
	 */
	public String getEmail();

	/**
	 * 获取用户来源
	 * 
	 * @return
	 */
	public String getFrom();

	/**
	 * 获取用户创建时间
	 * 
	 * @return
	 */
	public Date getCreateTime();

	/**
	 * 角色列表
	 * 
	 * @return
	 */
	public List<Role> getRoles();

	/**
	 * 独立权限列表，不包括用色的权限
	 * 
	 * @return
	 */
	public List<Right> getRights();

	/**
	 * 是否拥有该权限　
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasRight(String id);

	/**
	 * 是否能访问url
	 * 
	 * @param url
	 * @return
	 */
	public boolean isRight(String url);

	/**
	 * 修改用户名
	 * 
	 * @param oldLoginName
	 * @param newLoginName
	 * @return
	 */
	public boolean changeLoginName(String oldLoginName, String newLoginName);

	/**
	 * 校验密码
	 * 
	 * @param password
	 *            密码
	 * @return
	 */
	public boolean checkPassword(String password);

	/**
	 * 修改密码
	 * 
	 * @param oldPassword
	 *            旧密码
	 * @param newPassowrd
	 *            　新密码
	 * @return
	 */
	public boolean changePassword(String oldPassword, String newPassowrd);

	/**
	 * 重置密码
	 * 
	 * @param password
	 *            新密码
	 */
	public void resetPassword(String password);
}

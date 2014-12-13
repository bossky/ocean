package com.bossky.user;

import java.util.List;

/**
 * 用户业务接口
 * 
 * @author bo
 *
 */
public interface UserService {

	/**
	 * 登陆
	 * 
	 * @param loginToken
	 * @param password
	 * @return
	 */
	public User login(String loginToken, String password);

	/**
	 * 通过id获取用户
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(String id);

	/**
	 * 列出所有用户　
	 * 
	 * @return
	 */
	public List<User> listUser();

}

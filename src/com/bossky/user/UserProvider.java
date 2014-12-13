package com.bossky.user;

import java.util.List;

/**
 * 用户提供者
 * 
 * @author bo
 *
 */
public interface UserProvider {
	/**
	 * 获取用户　
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(String id);

	/**
	 * 根据登陆凭证获取用户　
	 * 
	 * @param loginToken
	 * @return
	 */
	public User getUserByLoginToken(String loginToken);

	/**
	 * 列表所有用户　
	 * 
	 * @return
	 */
	public List<User> list();
}

package com.ourlinc.ocean.user;

import com.ourlinc.tern.ResultPage;

/**用户业务接口
 * @author Zhao_Gq
 */
public interface UserService {
	/**添加用户
	 * @param username 用户账号
	 * @param password 密码
	 * @param role 角色
	 * @return
	 * 	User对象
	 */
	public User addUser(String username,String password,Integer role);
	
	
	/**查询用户
	 * @param username 用户名（邮箱）
	 * @return
	 */
	public ResultPage<User> listUser(String username);
	
	/**获取单个用户对象
	 * @param id 用户ID
	 * @return
	 * 	User对象
	 */
	public User getUser(String id);
	
}

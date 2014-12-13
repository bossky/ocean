package com.bossky.user;

import java.util.List;

/**
 * 角色
 * 
 * @author bo
 *
 */
public interface Role {

	/**
	 * 角色id
	 * 
	 * @return
	 */
	public String getId();

	/**
	 * 角色名
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 角色描述
	 * 
	 * @return
	 */
	public String getDesc();

	/**
	 * 获取角色权限
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

}

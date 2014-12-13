package com.bossky.user;


/**
 * 权限
 * 
 * @author bo
 *
 */
public interface Right  {
	/** 类型-允许 */
	public String TYPE_ALLOW = "allow";
	/** 类型-不允许 */
	public String TYPE_DISALLOW = "disallow";

	/**
	 * 权限id
	 * 
	 * @return
	 */
	public String getId();

	/**
	 * 权限类型
	 * 
	 * @return @see TYPE_XXX
	 */
	public String getType();

	/**
	 * 权限名
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 权限描述
	 * 
	 * @return
	 */
	public String getDesc();

	/**
	 * 获取规则
	 * 
	 * @return
	 */
	public String getRule();

	/**
	 * 是否匹配
	 * 
	 * @param url
	 * @return
	 */
	public boolean isMatch(String url);
}

package com.bossky.user;


/**
 * 消息
 * 
 * @author bo
 *
 */
public interface Message {

	/**
	 * 消息来源,发送人
	 * 
	 * @return
	 */
	public User getFrom();

	/**
	 * 消息去向,接收人
	 * 
	 * @return
	 */
	public User getTo();

	/**
	 * 消息内容
	 * 
	 * @return
	 */
	public String getContent();

	/**
	 * 消息创建时间
	 * 
	 * @return
	 */
	public String getCreateTime();

	/**
	 * 是否已读
	 * 
	 * @return
	 */
	public boolean isReaded();
}

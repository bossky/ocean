package com.bossky.user;

import java.util.Date;
import java.util.List;

/**
 * 消息提供者
 * 
 * @author bo
 *
 */
public interface MessageProvider {

	/**
	 * 前top条消息
	 * 
	 * @param top
	 *            前top条消息
	 * @param onlyNew
	 *            是否只要新消息
	 * @return
	 */
	public List<Message> topMessage(int top, boolean onlyNew);

	/**
	 * 指定时间的消息
	 * 
	 * @param beight
	 * @param end
	 * @return
	 */
	public List<Message> searchMessge(User user, Date beight, Date end);

	/**
	 * 获取新消息(未读消息)
	 * 
	 * @param user
	 * @return
	 */
	public List<Message> searchNewMessages(User user, Date beight, Date end);

	/**
	 * 获取旧消息(已读消息)
	 * 
	 * @param user
	 * @param beigh
	 * @param end
	 * @return
	 */
	public List<Message> searchOldMessage(User user, Date begin, Date end);
}

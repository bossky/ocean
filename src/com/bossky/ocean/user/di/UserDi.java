package com.bossky.ocean.user.di;

import com.bossky.data.business.Di;
import com.bossky.ocean.ext.ResultPage;
import com.bossky.ocean.theme.Collect;
import com.bossky.ocean.theme.Theme;
import com.bossky.ocean.user.Message;
import com.bossky.ocean.user.User;

/**
 * 业务依赖接口
 * 
 * @author Zhao_Gq
 *
 */
public interface UserDi extends Di {
	/**
	 * 获取我的消息
	 * 
	 * @param user
	 *            User对象
	 * @return
	 */
	public ResultPage<Message> getMessages(User user);

	/**
	 * 获取我的收藏
	 * 
	 * @param user
	 *            User对象
	 * @return
	 */
	public ResultPage<Collect> getCollocetThems(User user);

	/**
	 * 获取我的话题
	 * 
	 * @param user
	 *            User对象
	 * @return
	 */
	public ResultPage<Theme> getMyThemes(User user);

	/**
	 * 发表话题的方法
	 * 
	 * @param user
	 *            User对象
	 * @param title
	 *            文章标题
	 * @param content
	 *            文章内容
	 */
	public Theme addTheme(User user, String title, String content);
}

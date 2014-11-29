package com.bossky.ocean.theme;


import com.bossky.ocean.ext.ResultPage;
import com.bossky.ocean.user.Message;
import com.bossky.ocean.user.User;
/**
 * 话题业务接口
 * @author daibo
 *
 */
public interface ThemeService {
	/**
	 * 查看话题列表
	 * @param type  排序类型
	 * @param userName 用户名（支持模糊查询，忽略大小写）
	 * @param Labelid	标签Id
	 * @return
	 */
	public ResultPage<Theme> listTheme(String type,String userName,String Labelid);
	/**
	 * 查找评论
	 * @param userName	用户名（支持模糊查询，忽略大小写）
	 * @param title		话题标题（支持模糊查询，忽略大小写）
	 * @return
	 */
	public ResultPage<Comments> listComments(String userName,String title);
	/**
	 * 查看回复
	 * @param userName	用户名（支持模糊查询，忽略大小写）
	 * @param themeTitle 话题标题（支持模糊查询，忽略大小写）
	 * @return
	 */
	public ResultPage<Reply> listReply(String userName, String themeTitle);
	/**
	 * 拿到所有标签
	 * @return
	 */
	public ResultPage<Label> getLabels();
	/**
	 * 添加根标签
	 * @param name	标签名
	 * @return
	 */
	public Label addLabel(String name);
	/**
	 * 移除指定标签对象
	 * @param label
	 */
	public void removeLabel(Label label);
	/**
	 * 我的收藏话题列表
	 * @param user
	 * @return
	 */
	public ResultPage<Collect> getCollectThemes(User user);

	/**
	 * 我发布的话题列表
	 * @param user
	 * @return
	 */
	public ResultPage<Theme> getMyThemes(User user);
	/**
	 * 我的消息列表
	 * @param user
	 * @return
	 */
	public ResultPage<Message> getMessage(User user);
	/**
	 * 通过Id获得标签
	 * @param id
	 * @return
	 */
	public Label getLabel(String id);
	/**
	 * 发表话题
	 * @param user	用户对象 
	 * @param title	标题
	 * @param content 内容
	 * @return
	 */
	public Theme createTheme(User user,String title,String content);
	/**
	 * 通过Id找到话题
	 * @param id
	 * @return
	 */
	public Theme getTheme(String id);
	/**
	 * 通过Id找到评论
	 * @param id
	 * @return
	 */
	public Comments getComments(String id);
	/**
	 * 通过Id找到回复 
	 * @param id
	 * @return
	 */
	public Reply getReply(String id);
	

	

}

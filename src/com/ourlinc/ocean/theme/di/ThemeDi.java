package com.ourlinc.ocean.theme.di;


import java.util.List;

import com.ourlinc.ocean.theme.Comments;
import com.ourlinc.ocean.theme.Label;
import com.ourlinc.ocean.theme.Reply;
import com.ourlinc.ocean.theme.Theme;
import com.ourlinc.ocean.user.User;
import com.ourlinc.tern.ResultPage;
import com.ourlinc.tern.search.Searcher;
import com.ourlinc.tern.support.BusinessDi;
/**
 * 业务依赖接口
 * @author daibo
 *
 */
public interface ThemeDi extends BusinessDi {
	/**
	 * 获取一个话题对象下的所有评论
	 * @param theme 话题对象 
	 * @return
	 */
	public ResultPage<Comments> getComments(Theme theme);
	/**
	 * 通过id获取标签
	 * @param id
	 * @return
	 */
	public Label getLabel(String id);
	/**
	 * 获取label对象的子标签 
	 * @param label
	 * @return
	 */
	public List<Label> getChildList(Label label);
	/**
	 * 获取评论下的所回复的评论
	 * @param comments
	 * @return
	 */
	public ResultPage<Reply> getReplys(Comments comments);
	/**
	 * 是否已收藏
	 * @param theme
	 * @param user
	 * @return
	 */
	public boolean isCollected(Theme theme,User user);
	/**
	 * 是否已赞
	 * @param theme
	 * @param user
	 * @return
	 */
	public boolean isPraised(Theme theme,User user);
	/**
	 * 标签的话题数
	 * @param label
	 * @return
	 */
	public int getThemeNum(Label label);
	/**
	 * 通过话题ID获取话题
	 * @param id
	 * @return
	 */
	public Theme getTheme(String id);
	/**
	 * 通过评论Id获取评论
	 * @param id
	 * @return
	 */
	public Comments getComments(String id);
	/**
	 * 通过用户ID获取用户对象
	 * @param id
	 * @return
	 */
	public User getUser(String id);
	/**
	 * 取消收藏
	 * @param user
	 * @param theme
	 */
	boolean cancelCollect(User user, Theme theme);
	/**
	 * 通过Id获取回复对象
	 * @param id
	 * @return
	 */
	public Reply getReply(String id);
	
	/**
	 * 获取话题搜索器
	 * @return
	 */
	Searcher getThemeSearcher();
	/**
	 * 获取评论搜索器
	 * @return
	 */
   Searcher getCommentsSearcher();
	

	
	
}

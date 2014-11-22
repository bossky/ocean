package com.ourlinc.ocean.theme.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ourlinc.ocean.theme.Collect;
import com.ourlinc.ocean.theme.Comments;
import com.ourlinc.ocean.theme.Label;
import com.ourlinc.ocean.theme.Operation;
import com.ourlinc.ocean.theme.Praise;
import com.ourlinc.ocean.theme.Reply;
import com.ourlinc.ocean.theme.Theme;
import com.ourlinc.ocean.theme.ThemeService;
import com.ourlinc.ocean.theme.di.ThemeDi;
import com.ourlinc.ocean.user.Message;
import com.ourlinc.ocean.user.User;
import com.ourlinc.ocean.user.UserService;
import com.ourlinc.tern.Persistent;
import com.ourlinc.tern.Persister;
import com.ourlinc.tern.ResultPage;
import com.ourlinc.tern.UniteId;
import com.ourlinc.tern.ext.ResultPages;
import com.ourlinc.tern.search.IndexKeyword;
import com.ourlinc.tern.search.IndexKeywords;
import com.ourlinc.tern.search.ResultPageWrap;
import com.ourlinc.tern.search.Searcher;
import com.ourlinc.tern.support.DataHub;
import com.ourlinc.tern.util.Misc;
/**
 * 话题业务接口实现类
 * @author daibo
 *
 */
public class ThemeServiceImpl implements ThemeService{
	
	static Log _Logger = LogFactory.getLog(ThemeServiceImpl.class);
	@Resource(name = "userService")
	private UserService m_UserService;
	final DataHub m_DataHub;
	final ThemeDiImpl m_ThemeDi;
	final Persister<Label> m_PsLabel;
	final Persister<Theme> m_PsTheme;
	final Persister<Collect> m_PsCollect;
	final Persister<Comments> m_PsComments;
	final Persister<Reply> m_PsReply;
	final Persister<Praise> m_PsPraise;
	final Searcher m_ThemeSearcher;//话题搜索器，LabelId作索引
	final Searcher m_CommentsSearcher;//评论搜索器，m_CommentetatorId作索引
	public ThemeServiceImpl(DataHub hub){
		super();
		m_DataHub=hub;
		m_ThemeDi=new ThemeDiImpl();
		m_PsLabel=hub.createPersister(Label.class, m_ThemeDi);
		m_PsTheme=hub.createPersister(Theme.class,m_ThemeDi);
		m_PsComments=hub.createPersister(Comments.class,m_ThemeDi);
		m_PsReply=hub.createPersister(Reply.class,m_ThemeDi);
		m_PsCollect=hub.createPersister(Collect.class,m_ThemeDi);
		m_PsPraise=hub.createPersister(Praise.class,m_ThemeDi);
		m_ThemeSearcher=hub.createSearcher("theme");
		m_CommentsSearcher=hub.createSearcher("comments");
	}
	@Override
	public Label addLabel(String name) {
		return new Label(m_ThemeDi,name);
	}
	@Override
	public ResultPage<Label> getLabels() {
		return m_PsLabel.startsWith(null);
	}
	@Override
	public Label getLabel(String id) {
		return m_PsLabel.get(id);
	}
	@Override
	public void removeLabel(Label label) {
		List<Label> list=ResultPages.toList(getLabels(), ResultPage.LIMIT_NONE);
		for(Label l:list){
			Label parent=l.getParentLabel();
			if(null!=parent&&label.getId().equals(parent.getId())){
				removeLabel(l);//递归移除子标签
			}
		}
		//找到标签有关的话题,更新标签
		ResultPage<Theme> rp=ResultPageWrap.wrap(
				m_ThemeSearcher.search(Searcher.OPTION_NONE,Theme.REINDEX_LABEL+label.getId().getId())
				,m_PsTheme);
		while(rp.gotoPage(rp.getPage()+1)){
			while(rp.hasNext()){
				Theme t=rp.next();
				t.remvoeLabel(label);//将要删除的标签从话题中移除
			}
		}
		m_PsLabel.remove(label.getId());
	}

	@Override
	public Theme createTheme(User user, String title, String content) {
		return new Theme(m_ThemeDi,user,title,content);
	}

	@Override
	public Theme getTheme(String id) {
		return m_PsTheme.get(id);
	}
	@Override
	public ResultPage<Comments> listComments(String userName, String title) {
		ResultPage<Comments> rp=m_PsComments.startsWith(null);
		if(!Misc.isEmpty(userName)||!Misc.isEmpty(title)){
			List<Comments> list=new ArrayList<Comments>();
			while(rp.gotoPage(rp.getPage()+1)){
				while(rp.hasNext()){
					Comments c=rp.next();
					
					if(!Misc.isEmpty(userName)&&!c.getCommentator().
							getUserName().toLowerCase().contains(userName.toLowerCase())){
						continue;//userName不为空且评论者的用户名不是userName开头的评论不要
					}
					if(!Misc.isEmpty(title)&&!c.getTheme().getTitle().
							toLowerCase().contains(title.toLowerCase())){
						continue;//title不为空且评论者的用户名不是title开头的评论不要
					}
					list.add(c);
					
				}
			}
			rp=ResultPages.toResultPage(list);
		}
		return rp;
	}


	@Override
	public ResultPage<Reply> listReply(String userName, String title) {
		ResultPage<Reply> rp=m_PsReply.startsWith(null);
		if(!Misc.isEmpty(userName)||!Misc.isEmpty(title)){
			List<Reply> list=new ArrayList<Reply>();
			while(rp.gotoPage(rp.getPage()+1)){
				while(rp.hasNext()){
					Reply r=rp.next();
					if(!Misc.isEmpty(userName)&&!r.getReplyUser().getUserName().
							toLowerCase().contains(userName.toLowerCase())){
						continue;//userName不为空且回复者的用户名不是userName开头的回复不要
					}
					if(!Misc.isEmpty(title)&&!r.getTheme().getTitle().
							toLowerCase().contains(title.toLowerCase())){
						continue;//title不为空且回复者的用户名不是title开头的回复不要
					}
					list.add(r);
					
				}
			}
			rp=ResultPages.toResultPage(list);
		}
		return rp;
	}
	@Override
	public Comments getComments(String id) {
		return m_PsComments.get(id);
	}
	@Override
	public Reply getReply(String id) {
		return m_PsReply.get(id);
	}

	@Override
	public ResultPage<Theme> listTheme(String type,String userName,String labelId) {
		Comparator<Theme> comparator=null;
		if("comments".equals(type)){
			comparator=Theme.COMMENTS_SORT;
		}else if("view".equals(type)){
			comparator=Theme.VIEW_SORT;
		}else if("collcet".equals(type)){
			comparator=Theme.COLLCET_SORT;
		}else if("praise".equals(type)){
			comparator=Theme.PRAISE_SORT;
		}
		if(null==comparator){
			comparator=Theme.NEW_SORT;//没有类型时默认按时间排序
		}
		ResultPage<Theme> rp=null;
		
		if(Misc.isEmpty(labelId)){//labelId为null时查找出所有的话题
			rp=m_PsTheme.startsWith(null);
		}else{//labelId不为null是找出label有关的话题
			rp=ResultPageWrap.wrap(
					m_ThemeSearcher.search(Searcher.OPTION_NONE,Theme.REINDEX_LABEL+labelId)
					,m_PsTheme);
		}
		if(!Misc.isEmpty(userName)){
			List<Theme> list=new ArrayList<Theme>();
			while(rp.gotoPage(rp.getPage()+1)){
				while(rp.hasNext()){
					Theme t=rp.next();
					//找出话题作者的用户名为userName开头的话题
					if(t.getUser().getUserName().toLowerCase()
							.contains(userName.toLowerCase())){
						list.add(t);
					}
				}
			}
			rp=ResultPages.toResultPage(list);
			
		}
		//rp不为null是执行排序
		if(rp!=null){
			rp=ResultPages.toSortResultPage(rp, comparator, ResultPage.LIMIT_NONE);
		}
		return rp;
	}

	@Override
	public ResultPage<Collect> getCollectThemes(User user) {
		ResultPage<Collect> rp=m_PsCollect.startsWith(UniteId.getOrdinal(user.getId().getId()));
		//默认按时间排序
		rp=ResultPages.toSortResultPage(rp, Collect.NEW_SORT, ResultPage.LIMIT_NONE);
		return rp;
	}
	@Override
	public ResultPage<Theme> getMyThemes(User user) {
		ResultPage<Theme> rp=m_PsTheme.startsWith(UniteId.getOrdinal(user.getId().getId()));
		//默认按时间排序
		rp=ResultPages.toSortResultPage(rp, Theme.NEW_SORT, ResultPage.LIMIT_NONE);
		return rp;
	}
	@Override
	public ResultPage<Message> getMessage(User user) {
		List<Message> list=new ArrayList<Message>();
		ResultPage<Theme> trp=getMyThemes(user);
		//查找我的话题中的评论消息
		while(trp.gotoPage(trp.getPage()+1)){
			while(trp.hasNext()){
				Theme t=trp.next();
				List<Comments> cList=t.getCommentsList();
				if(null!=cList){
					for(Comments c:cList){
						//自己评论自己的话题的消息不要
						if(!c.getCommentator().getId().equals(user.getId().getId())){
							list.add(c);
						}
					}
				}
			}
		}
		ResultPage<Comments> crp=ResultPageWrap.wrap(m_CommentsSearcher.search(Searcher.OPTION_NONE,
				Comments.REINDEX_TYPE+user.getId().getId()),m_PsComments);
		//查找我的评论中的回复消息
		while(crp.gotoPage(crp.getPage()+1)){
			while(crp.hasNext()){
				Comments c=crp.next();
				List<Reply> rList=c.getReplyList();
					if(null!=rList){
						for(Reply r:rList){
							//自己回复自己的评论的消息不要
							if(!r.getReplyUser().getId().equals(user.getId().getId())){
								list.add(r);
							}
						}
					}
			}
		}
		//查找别人回复我的消息
		ResultPage<Reply> rrp=m_PsReply.startsWith(null);
		while(rrp.gotoPage(rrp.getPage()+1)){
			while(rrp.hasNext()){
				Reply r=rrp.next();
				if(Misc.isEmpty(r.getReplayerTargetId())){
		
				}else{
					//自己回复自己的不要
					if(r.getReplayerTargetId().equals(user.getId().getId())
						&&!r.getReplyUser().getId().equals(user.getId().getId())){
						list.add(r);
					}
				}
			}
		}
		ResultPage<Message> rp=ResultPages.toResultPage(list);
		//按时间排序
		rp=ResultPages.toSortResultPage(rp, Comments.NEW_SORT, ResultPage.LIMIT_NONE);	
		return rp;
	}
	

	class ThemeDiImpl implements ThemeDi{

		@Override
		public <E extends Persistent> Persister<E> getPersister(Class<E> clazz) {
			return m_DataHub.getPersister(clazz);
		}

		@Override
		public boolean cancelCollect(User user, Theme theme) {
			//通过userId和themeId的组合找到收藏记录
			String id=UniteId.getOrdinal(user.getId().getId())+
					Operation.ID_TYPE+UniteId.getOrdinal(theme.getId().getId());
			ResultPage<Collect> rp=m_PsCollect.startsWith(id);
			rp.gotoPage(1);
			return m_PsCollect.remove(rp.next().getId());
			
		}

		@Override
		public ResultPage<Comments> getComments(Theme theme) {
			return m_PsComments.startsWith(UniteId.getOrdinal(theme.getId().getId()));
		}

		@Override
		public Label getLabel(String id) {
			return m_PsLabel.get(id);
		}

		@Override
		public List<Label> getChildList(Label label) {
			ResultPage<Label> rp=m_PsLabel.startsWith(Label.ID_TYPE
					+UniteId.getOrdinal(label.getId().getId()));
			return ResultPages.toList(rp, ResultPage.LIMIT_NONE);
		}

		@Override
		public ResultPage<Reply> getReplys(Comments comments) {
			return m_PsReply.startsWith(UniteId.getOrdinal(comments.getId().getId()));
		}

		@Override
		public boolean isCollected(Theme theme, User user) {
			//通过userId和themeId的组合找到收藏记录
			String id=UniteId.getOrdinal(user.getId().getId())+
					Operation.ID_TYPE+UniteId.getOrdinal(theme.getId().getId());
			ResultPage<Collect> rp=m_PsCollect.startsWith(id);
			if(null!=rp&&rp.getCount()>0){
				return true;
			}else{
				return false;
			}
		}

		@Override
		public boolean isPraised(Theme theme, User user) {
			// 通过userId和themeId的组合找到赞记录
			String id=UniteId.getOrdinal(user.getId().getId())+
					Operation.ID_TYPE+UniteId.getOrdinal(theme.getId().getId());
			ResultPage<Praise> rp=m_PsPraise.startsWith(id);
			if(null!=rp&&rp.getCount()>0){
				return true;
			}else{
				return false;
			}
		}

		@Override
		public int getThemeNum(Label label) {
			List<IndexKeyword> ks = new ArrayList<IndexKeyword>();
			ks.add(IndexKeywords.newKeyword(Theme.REINDEX_LABEL+label.getId(),0));
			ResultPage<Theme> rp=ResultPageWrap.wrap(
					m_ThemeSearcher.search(ks,Searcher.OPTION_NONE)
					,m_PsTheme);
			if(null==rp){
				return 0;
			}else{
				return rp.getCount();
			}
		}

		@Override
		public Theme getTheme(String id) {
			return m_PsTheme.get(id);
		}

		@Override
		public Comments getComments(String id) {
			return m_PsComments.get(id);
		}

		@Override
		public User getUser(String id) {
			return m_UserService.getUser(id);
		}
		@Override
		public Searcher getThemeSearcher() {
			return m_ThemeSearcher;
		}

		@Override
		public Searcher getCommentsSearcher() {
			return m_CommentsSearcher;
		}
		@Override
		public Reply getReply(String m_ReplayerTargetId) {
			return m_PsReply.get(m_ReplayerTargetId);
		}


}
}

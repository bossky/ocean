package com.ourlinc.ocean.theme;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ourlinc.ocean.theme.di.ThemeDi;
import com.ourlinc.ocean.user.Message;
import com.ourlinc.ocean.user.User;
import com.ourlinc.tern.NameItem;
import com.ourlinc.tern.ResultPage;
import com.ourlinc.tern.UniteId;
import com.ourlinc.tern.ext.ResultPages;
import com.ourlinc.tern.search.IndexElement;
import com.ourlinc.tern.support.AbstractPersistent;
/**
 * 评论业务类
 * @author daibo
 *
 */
public class Comments extends AbstractPersistent<ThemeDi> implements Message{
	@Resource
	private String m_ThemeId;//所评论的话题的Id
	@Resource		
	private String m_CommentetatorId;//评论发表者
	@Resource			
	private String m_Content;//内容
	@Resource
	private Date m_CreateDate;//发表时间
	@Resource		
	private int m_Status;
	public static final NameItem STATUS_SHIELD=new NameItem("已屏蔽",0);
	public static final NameItem STATUS_NORMAL = new NameItem("正常",1);
	public static final NameItem[] ALL_STATUS = {STATUS_SHIELD,STATUS_NORMAL};
	public static final String REINDEX_TYPE="C:";
	public Comments(ThemeDi di,Theme theme,User user,String content){
		super(di);
		m_Id=getPersister().getNewId(UniteId.getOrdinal(theme.getId().getId()));
		m_ThemeId=theme.getId().getId();
		m_CommentetatorId=user.getId().getId();
		m_Content=content;
		m_CreateDate=new Date();
		m_Status=STATUS_NORMAL.id;
		markPersistenceUpdate();
		reindex();
	}

	/**
	 * 获得所评论的主题
	 * @return
	 */
	public Theme getTheme(){
		return getBusinessDi().getTheme(m_ThemeId);
	}
	public String getThemeId(){
		return m_ThemeId;
	}
	/**
	 * 获得评论者
	 * @return
	 */
	public User getCommentator(){
		return getBusinessDi().getUser(m_CommentetatorId);
	}
	/**
	 * 是否被屏蔽
	 * @return
	 */
	public boolean isShield(){
		if(STATUS_SHIELD.id==m_Status){
			return true;
		}
		return false;
	}
	/**
	 * 屏蔽
	 */
	public void shield(){
		m_Status=STATUS_SHIELD.id;
		markPersistenceUpdate();
	}
	/**
	 * 恢复
	 */
	public void recover(){
		m_Status=STATUS_NORMAL.id;
		markPersistenceUpdate();
	}
	/**
	 * 获取所有回复
	 * @return
	 */
	public List<Reply> getReplyList(){
		ResultPage<Reply> rp=getBusinessDi().getReplys(this);
		if(null==rp){
			return null;
		}else{
			return ResultPages.toList(rp, ResultPage.LIMIT_NONE);
		}
	}
	/**
	 * 回复评论
	 * @param content
	 * @return
	 */
	public Reply reply(User user,String content){
		//被屏蔽时不能回复
		if(isShield()){
			return null;
		}
		User commentator=getCommentator();
		//如果话题发表者不是评论者则新消息加1
		if(!user.getId().getId().equals(commentator.getId().getId())){
			getCommentator().acquireMessage();
		}
		return new Reply(getBusinessDi(),this,user,content);
	}
	public String getContent(){
		//评论被屏蔽时不显示消息
		if(isShield()){
			return "<span style='color:red'>该评论已被屏蔽</span>";
		}
		return m_Content;
	}
	public String getNoHTMLContent(){
		return m_Content.replaceAll("<.*?>", "");
	}
	
	public Date getCreateDate(){
		return m_CreateDate;
	}

	protected Comments(ThemeDi arg0) {
		super(arg0);
	}
	
	@Override
	public User getSender() {
		return getCommentator();
	}
	/**
	 * 索引方法
	 */
	public void reindex(){
		getBusinessDi().getCommentsSearcher().updateElement(IndexElement.valueOf(getId()),
				REINDEX_TYPE +m_CommentetatorId);
	}
	@Override
	public int getType() {
		return Message.COMMENT;
	}
	//按时间降序排序的消息排序器
	public static final	Comparator<Message> NEW_SORT=new Comparator<Message>(){
		@Override
		public int compare(Message t1, Message t2) {
			if (null == t1 || null == t2) {
				return -1;
				}
			return t1.getCreateDate().after(t2.getCreateDate())?0:1;
		}
	};
	//按时间数降序排序的评论排序器
	public static final	Comparator<Comments> TIME_SORT=new Comparator<Comments>(){
		@Override
		public int compare(Comments t1, Comments t2) {
			if (null == t1 || null == t2) {
				return -1;
				}
			return t1.getCreateDate().after(t2.getCreateDate())?0:1;
		}
	};
}

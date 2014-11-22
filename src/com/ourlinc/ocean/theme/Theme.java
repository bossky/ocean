package com.ourlinc.ocean.theme;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ourlinc.ocean.theme.di.ThemeDi;
import com.ourlinc.ocean.user.User;
import com.ourlinc.tern.NameItem;
import com.ourlinc.tern.ResultPage;
import com.ourlinc.tern.UniteId;
import com.ourlinc.tern.ext.ResultPages;
import com.ourlinc.tern.search.IndexElement;
import com.ourlinc.tern.search.IndexKeyword;
import com.ourlinc.tern.search.IndexKeywords;
import com.ourlinc.tern.support.AbstractPersistent;
/**
 * 主题
 * @author daibo
 *
 */
public class Theme extends AbstractPersistent<ThemeDi>{
	@Resource
	private String m_UserId;//发表话题的用户Id
	@Resource
	private String m_Title;
	@Resource
	private String m_Content;
	@Resource
	private List<String> m_LabelList;//话题所属的标签Id的List
	@Resource
	private int m_ViewNum=0;//浏览数
	@Resource
	private int m_CollectNum=0;//收藏数
	@Resource 
	private int m_PraiseNum=0;//赞数
	@Resource 
	private int m_CommentsNum=0;//评论数
	@Resource
	private int m_Status;
	@Resource
	private Date m_CreateDate;
	public static final NameItem STATUS_SHIELD=new NameItem("已屏蔽",0);
	public static final NameItem STATUS_NORMAL = new NameItem("正常",1);
	public static final NameItem[] ALL_STATUS = {STATUS_SHIELD,STATUS_NORMAL};
	public static final String REINDEX_LABEL="L:";
	public Theme(ThemeDi di,User user,String title,String content){
		super(di);
		//使用userId构造话题id
		m_Id=getPersister().getNewId(UniteId.getOrdinal(user.getId().getId()));
		m_UserId=user.getId().getId();
		m_CreateDate=new Date();
		m_ViewNum=0;
		m_LabelList=new ArrayList<String>();
		m_Status=STATUS_NORMAL.id;
		m_Title=title;
		m_Content=content;
		markPersistenceUpdate();
	}
	/**
	 * 浏览数+1
	 */
	public synchronized void visit(){
		m_ViewNum++;
		markPersistenceUpdate();
	}
	/**
	 * 拿到话题的所有评论
	 * @return
	 */
	public List<Comments> getCommentsList(){
		ResultPage<Comments> rp=getBusinessDi().getComments(this);
		if(null==rp){
			return null;
		}else{
			return ResultPages.toList(rp, ResultPage.LIMIT_NONE);
		}
		
	}
	public Comments getComments(String id){
		Comments c=getBusinessDi().getComments(id);
		if(null!=c&&getId().getId().equals(c.getThemeId())){
			return c;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取话题的标签
	 * @return
	 */
	public List<Label> getLabelList(){
		List<Label> result=new ArrayList<Label>();
		ThemeDi di=getBusinessDi();
		for(String id:m_LabelList){
			result.add(di.getLabel(id));
		}
		return result;
	}
	/**
	 * 返回话题标签
	 * @return
	 */
	public String getLabelStr(){
		if(null==m_LabelList||0==m_LabelList.size()){
			return null;
		}
		return m_LabelList.toString();
	}
	/**
	 * 更新话题的标签
	 * @param list
	 */
	public void updateLabelList(List<String> list){
		m_LabelList.clear();
		m_LabelList.addAll(list);
		markPersistenceUpdate();
		reindex();
	}
	/**
	 * 拿到拥有的标签ID
	 * @param id
	 * @return
	 */
	public List<String> getLabelListId(){
		return m_LabelList;
	}
	/**
	 * 移除指定标签
	 * @param label
	 */
	public void remvoeLabel(Label label) {
		List<String> list=new ArrayList<String>();
		list.addAll(m_LabelList);
		if(list.remove(label.getId().getId())){
			updateLabelList(list);
		}
		
	}
	/**
	 * 收藏话题
	 * @param user
	 * @return
	 */
	public synchronized int collectTheme(User user){
		//用户已收藏则不能重复收藏
		if(isCollected(user)){
			return m_CollectNum;
		}else{
			new Collect(getBusinessDi(),user,this);
			m_CollectNum++;
			markPersistenceUpdate();
			return m_CollectNum;
			
		}
	}
	
	/**
	 * 是否已收藏
	 * @param user
	 * @return
	 */
	public boolean isCollected(User user){
		return getBusinessDi().isCollected(this, user);
	}
	public synchronized int cannalCollected(User user){
		if(getBusinessDi().cancelCollect(user, this)){
			m_CollectNum--;
			markPersistenceUpdate();
		}
		return m_CollectNum;
	}
	/**
	 * 赞话题
	 * @param user
	 */
	public synchronized int praiseTheme(User user){
		//已赞不能重复赞
		if(isPraised(user)){
			return m_PraiseNum;
		}else{
			new Praise(getBusinessDi(),user,this);
			m_PraiseNum++;
			markPersistenceUpdate();
			return m_PraiseNum;
		}
	
	}
	/**
	 * 是否已赞 
	 * @param user
	 * @return
	 */
	public boolean isPraised(User user){
		return getBusinessDi().isPraised(this, user);
	}
	/**
	 * 评论话题
	 * @param user
	 */
	public synchronized Comments commentTheme(User user,String content){
		//被屏蔽后不能被评论
		if(isShield()){
			return null;
		}
		if(!user.getId().getId().equals(m_UserId)){
			getUser().acquireMessage();
		}
		m_CommentsNum++;
		return new Comments(getBusinessDi(),this,user,content);
	}
	/**
	 * 屏蔽话题
	 */
	public void shield(){
		m_Status=STATUS_SHIELD.id;
		markPersistenceUpdate();
	}
	/**
	 * 恢复话题
	 */
	public void recover(){
		m_Status=STATUS_NORMAL.id;
		markPersistenceUpdate();
	}
	/**
	 * 是否已屏蔽
	 * @return
	 */
	public boolean isShield(){
		if(STATUS_SHIELD.id==m_Status){
			return true;
		}
		return false;
	}
	/**
	 * 获得发表话题的用户
	 * @return
	 */
	public User getUser(){
		return getBusinessDi().getUser(m_UserId);
	}
	public String getUserId(){
		return m_UserId;
	}
	
	/**
	 * 获取浏览数
	 * @return
	 */
	public int getViewNum(){
		return m_ViewNum;
	}
	/**
	 * 获取收藏数
	 * @return
	 */
	public int getCollectNum(){
		return m_CollectNum;
	}
	/**
	 * 获取赞数
	 * @return
	 */
	public int getPraiseNum(){
		return m_PraiseNum;
	}
	/**
	 * 获取评论数
	 * @return
	 */
	public int getCommentsNum(){
		return m_CommentsNum;
				
	}
	public String getTitle(){
		return m_Title;
	}
	
	public String getContent(){
		if(isShield()){
			return "<span style='color:red'>该话题被已屏蔽</span>";
		}
		return m_Content;
	}
	public String getNoHTMLContent(){
		if(isShield()){
			return "<span style='color:red'>该话题被已屏蔽</span>";
		}
		return m_Content.replaceAll("<.*?>", "");
	}
	public Date getCreateDate(){
		return m_CreateDate;
	}
	protected Theme(ThemeDi di) {
		super(di);
		
	}
	public void reindex(){
		List<IndexKeyword> ks = new ArrayList<IndexKeyword>();
		for(String id:m_LabelList){
			//_Logger.info("id="+id+"\tname"+this.getBusinessDi().getLabel(id).getName());
			ks.add(IndexKeywords.newKeyword(REINDEX_LABEL+id,0));
		}
		getBusinessDi().getThemeSearcher().updateElement(IndexElement.valueOf(getId().getOrdinal()),ks);
	}
	
	public static final	Comparator<Theme> NEW_SORT=new Comparator<Theme>(){
		@Override
		public int compare(Theme t1, Theme t2) {
			
			if (null == t1 || null == t2) {
				return -1;
				}
			return t1.getCreateDate().after(t2.getCreateDate())?0:1;
		}
	};
	public static final	Comparator<Theme> COMMENTS_SORT=new Comparator<Theme>(){
		@Override
		public int compare(Theme t1, Theme t2) {
			
			if (null == t1 || null == t2) {
				return -1;
				}
			return t1.m_CommentsNum>t2.m_CommentsNum?0:1;
		}
	};
	public static final	Comparator<Theme> VIEW_SORT=new Comparator<Theme>(){
		@Override
		public int compare(Theme t1, Theme t2) {
			
			if (null == t1 || null == t2) {
				return -1;
				}
			return t1.m_ViewNum>t2.m_ViewNum?0:1;
		}
	};
	public static final	Comparator<Theme> COLLCET_SORT=new Comparator<Theme>(){
		@Override
		public int compare(Theme t1, Theme t2) {
			
			if (null == t1 || null == t2) {
				return -1;
				}
			return t1.m_CollectNum>t2.m_CollectNum?0:1;
		}
	};
	public static final	Comparator<Theme>	PRAISE_SORT=new Comparator<Theme>(){
		@Override
		public int compare(Theme t1, Theme t2) {
			
			if (null == t1 || null == t2) {
				return -1;
				}
			return t1.m_PraiseNum>t2.m_PraiseNum?0:1;
		}
	};

}

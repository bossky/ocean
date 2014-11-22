package com.ourlinc.ocean.theme;

import java.util.Date;

import javax.annotation.Resource;

import com.ourlinc.ocean.theme.di.ThemeDi;
import com.ourlinc.ocean.user.User;
import com.ourlinc.tern.UniteId;
import com.ourlinc.tern.support.AbstractPersistent;
/**
 * 操作业务抽象类，给Collect、Praise继承用的
 * @author daibo
 *
 */
public abstract class Operation extends AbstractPersistent<ThemeDi>{
	@Resource
	protected String m_UserId;
	@Resource
	protected String m_ThemeId;
	@Resource
	protected Date m_CreateDate;
	public static final String ID_TYPE=":";
	protected Operation(ThemeDi di) {
		super(di);
		
	}
	public Operation(ThemeDi di,User user,Theme theme){
		super(di);
		String id=UniteId.getOrdinal(user.getId().getId())
				+ID_TYPE+UniteId.getOrdinal(theme.getId().getId());
		m_Id=getPersister().getNewId(id);
		m_UserId=user.getId().getId();
		m_ThemeId=theme.getId().getId();
		m_CreateDate=new Date();
		markPersistenceUpdate();
	}
	public User getUser(){
		return getBusinessDi().getUser(m_UserId);
	}
	public String getThemeId(){
		return m_ThemeId;
	}
	public Theme getTheme(){
		return getBusinessDi().getTheme(m_ThemeId);
	}
	public Date getCreateDate(){
		return m_CreateDate;
	}
}

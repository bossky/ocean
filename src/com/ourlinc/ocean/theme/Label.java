package com.ourlinc.ocean.theme;

import java.util.List;

import javax.annotation.Resource;

import com.ourlinc.ocean.theme.di.ThemeDi;
import com.ourlinc.tern.UniteId;
import com.ourlinc.tern.support.AbstractPersistent;
/**
 * 标签业务类
 * @author daibo
 *
 */
public class Label  extends AbstractPersistent<ThemeDi>{
	@Resource
	private String m_Name;
	@Resource
	private String m_ParentId;//父标签Id
	public static final String ID_TYPE="P:";
	public Label(ThemeDi di,String name,Label label){
		super(di);
		//以P:+父标签id做前缀
		m_Id=getPersister().getNewId(ID_TYPE+UniteId.getOrdinal(label.getId().getId()));
		m_Name=name;
		m_ParentId=label.getId().getId();
		markPersistenceUpdate();
	}
	public Label(ThemeDi di,String name){
		super(di);
		m_Id=getPersister().getNewId();
		m_Name=name;
		markPersistenceUpdate();
	}
	protected Label(ThemeDi di) {
		super(di);
	
	}
	/**
	 * 获得父标签
	 * @return
	 */
	public Label getParentLabel(){
		return getBusinessDi().getLabel(m_ParentId);
	}
	/**
	 * 获得子标签
	 * @return
	 */
	public List<Label> getChildList(){
		return getBusinessDi().getChildList(this);
	}
	/**
	 * 添加子标签
	 * @param label
	 */
	public Label addChildLabel(String name){
		return new Label(getBusinessDi(),name,this);
	}
	/**
	 * 获得标签下的主题的个数
	 * @return
	 */
	public int getThemeNum(){
		return getBusinessDi().getThemeNum(this);
	}
	public String getParentId(){
		return m_ParentId;
	}
	public String getName(){
		return m_Name;
	}
	
}

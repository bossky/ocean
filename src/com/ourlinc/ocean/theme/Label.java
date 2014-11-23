package com.ourlinc.ocean.theme;

import java.util.List;

import javax.annotation.Resource;

import com.bossky.data.business.Persistent;
import com.ourlinc.ocean.theme.di.ThemeDi;

/**
 * 标签业务类
 * 
 * @author daibo
 *
 */
public class Label extends Persistent<ThemeDi> {
	@Resource
	private String name;
	@Resource
	private String parentId;// 父标签Id
	public static final String ID_TYPE = "P:";

	public Label(ThemeDi di, String name, Label label) {
		super(di);
		// 以P:+父标签id做前缀
		id = ID_TYPE + label.getId().getId()
				+ Long.toString(System.currentTimeMillis());
		this.name = name;
		this.parentId = label.getId().getId();
		markPersistenceUpdate();
	}

	public Label(ThemeDi di, String name) {
		super(di);
		id = Long.toHexString(System.currentTimeMillis());
		this.name = name;
		markPersistenceUpdate();
	}

	protected Label(ThemeDi di) {
		super(di);

	}

	/**
	 * 获得父标签
	 * 
	 * @return
	 */
	public Label getParentLabel() {
		return getBusinessDi().getLabel(parentId);
	}

	/**
	 * 获得子标签
	 * 
	 * @return
	 */
	public List<Label> getChildList() {
		return getBusinessDi().getChildList(this);
	}

	/**
	 * 添加子标签
	 * 
	 * @param label
	 */
	public Label addChildLabel(String name) {
		return new Label(getBusinessDi(), name, this);
	}

	/**
	 * 获得标签下的主题的个数
	 * 
	 * @return
	 */
	public int getThemeNum() {
		return getBusinessDi().getThemeNum(this);
	}

	public String getParentId() {
		return parentId;
	}

	public String getName() {
		return name;
	}

}

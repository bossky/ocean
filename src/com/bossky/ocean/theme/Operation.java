package com.bossky.ocean.theme;

import java.util.Date;

import javax.annotation.Resource;

import com.bossky.data.business.Persistent;
import com.bossky.ocean.theme.di.ThemeDi;
import com.bossky.ocean.user.User;

/**
 * 操作业务抽象类，给Collect、Praise继承用的
 * 
 * @author daibo
 *
 */
public abstract class Operation extends Persistent<ThemeDi> {
	@Resource
	protected String userId;
	@Resource
	protected String themeId;
	@Resource
	protected Date createDate;
	public static final String ID_TYPE = ":";

	protected Operation(ThemeDi di) {
		super(di);

	}

	public Operation(ThemeDi di, User user, Theme theme) {
		super(di);
		id = user.getId().getId() + ID_TYPE + theme.getId().getId()
				+ Long.toHexString(System.currentTimeMillis());
		userId = user.getId().getId();
		themeId = theme.getId().getId();
		createDate = new Date();
		markPersistenceUpdate();
	}

	public User getUser() {
		return getBusinessDi().getUser(userId);
	}

	public String getThemeId() {
		return themeId;
	}

	public Theme getTheme() {
		return getBusinessDi().getTheme(themeId);
	}

	public Date getCreateDate() {
		return createDate;
	}
}

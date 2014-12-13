package com.bossky.ocean.theme;

import com.bossky.ocean.theme.di.ThemeDi;
import com.bossky.ocean.user.OceanUser;

/**
 * 赞业务类
 * 
 * @author daibo
 *
 */
public class Praise extends Operation {

	public Praise(ThemeDi di, OceanUser user, Theme theme) {
		super(di, user, theme);
	}

	public Praise(ThemeDi di) {
		super(di);
	}
}

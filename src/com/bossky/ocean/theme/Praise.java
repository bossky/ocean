package com.bossky.ocean.theme;


import com.bossky.ocean.theme.di.ThemeDi;
import com.bossky.ocean.user.User;
import com.ourlinc.tern.annotation.Inherited;
/**
 * 赞业务类
 * @author daibo
 *
 */
@Inherited
public class Praise extends Operation{

	public Praise(ThemeDi di, User user, Theme theme) {
		super(di, user, theme);
	}
	public Praise(ThemeDi di){
		super(di);
	}
}

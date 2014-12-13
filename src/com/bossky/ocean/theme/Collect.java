package com.bossky.ocean.theme;

import java.util.Comparator;

import com.bossky.ocean.theme.di.ThemeDi;
import com.bossky.ocean.user.OceanUser;

/**
 * 收藏业务类
 * 
 * @author daibo
 * 
 */
public class Collect extends Operation {

	public Collect(ThemeDi di, OceanUser user, Theme theme) {
		super(di, user, theme);
	}

	public Collect(ThemeDi di) {
		super(di);
	}

	// 按时间降序的排序器
	public static final Comparator<Collect> NEW_SORT = new Comparator<Collect>() {

		@Override
		public int compare(Collect t1, Collect t2) {
			if (null == t1 || null == t2) {
				return -1;
			}
			return t1.getCreateDate().after(t2.getCreateDate()) ? 0 : 1;
		}

	};
}

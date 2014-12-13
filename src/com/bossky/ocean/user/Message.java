package com.bossky.ocean.user;

import java.util.Date;

import com.bossky.ocean.theme.Theme;

public interface Message {
	public static int COMMENT=0;
	public static int REPLAY=1;
	public OceanUser getSender();
	public Theme getTheme();
	public Date getCreateDate();
	public int getType();
}

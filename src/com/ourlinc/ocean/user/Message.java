package com.ourlinc.ocean.user;

import java.util.Date;

import com.ourlinc.ocean.theme.Theme;

public interface Message {
	public static int COMMENT=0;
	public static int REPLAY=1;
	public User getSender();
	public Theme getTheme();
	public Date getCreateDate();
	public int getType();
}

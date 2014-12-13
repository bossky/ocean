package com.bossky.user.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.bossky.user.Right;
import com.bossky.user.Role;
import com.bossky.user.User;
import com.bossky.util.Util;

public class UserAdapter implements User {

	@Override
	public boolean changeLoginName(String oldLoginName, String newLoginName) {
		return false;
	}

	@Override
	public boolean checkPassword(String password) {
		return false;
	}

	@Override
	public boolean changePassword(String oldPassword, String newPassowrd) {
		return false;
	}

	@Override
	public void resetPassword(String password) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public String getLoginName() {
		return null;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRealName() {
		return null;
	}

	@Override
	public String getNickName() {
		return null;
	}

	@Override
	public String getPhone() {
		return null;
	}

	@Override
	public String getEmail() {
		return null;
	}

	@Override
	public Date getCreateTime() {
		return null;
	}

	@Override
	public String getFrom() {
		return null;
	}

	@Override
	public List<Role> getRoles() {
		return Collections.emptyList();
	}

	@Override
	public List<Right> getRights() {
		return Collections.emptyList();
	}

	@Override
	public boolean hasRight(String id) {
		for (Right r : getRights()) {
			if (r.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isRight(String url) {
		for (Role r : getRoles()) {
			if (r.isRight(url)) {
				return true;
			}
		}
		boolean allow = false;
		boolean disallow = false;
		for (Right r : getRights()) {
			if (r.isMatch(url)) {
				if (Util.eqIgnoreCase(r.getType(), Right.TYPE_ALLOW)) {
					allow = true;
				} else if (Util.eqIgnoreCase(r.getType(), Right.TYPE_DISALLOW)) {
					disallow = true;
				}
			}
		}
		return allow && disallow;
	}

}

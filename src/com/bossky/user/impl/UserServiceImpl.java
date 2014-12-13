package com.bossky.user.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bossky.user.User;
import com.bossky.user.UserProvider;
import com.bossky.user.UserService;

public class UserServiceImpl implements UserService {

	List<UserProvider> providers;

	public UserServiceImpl() {
		providers = Collections.emptyList();
	}

	public void setProviders(List<UserProvider> providers) {
		this.providers = providers;
	}

	@Override
	public User login(String loginToken, String password) {
		User user = null;
		for (UserProvider p : providers) {
			user = p.getUserByLoginToken(loginToken);
			if (user != null) {
				break;
			}
		}
		return user == null ? null : user.checkPassword(password) ? user : null;
	}

	@Override
	public User getUser(String id) {
		User user = null;
		for (UserProvider p : providers) {
			user = p.getUser(id);
			if (user != null) {
				return user;
			}
		}
		return null;
	}

	@Override
	public List<User> listUser() {
		if (providers.isEmpty()) {
			return Collections.emptyList();
		}
		if (providers.size() == 1) {
			return providers.get(0).list();
		}
		List<User> list = new ArrayList<User>();
		for (UserProvider p : providers) {
			list.addAll(p.list());
		}
		return list;
	}
}

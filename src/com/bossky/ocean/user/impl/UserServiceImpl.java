package com.bossky.ocean.user.impl;

import javax.annotation.Resource;

import com.bossky.data.DataFactory;
import com.bossky.data.DataManager;
import com.bossky.ocean.ext.ResultPage;
import com.bossky.ocean.ext.ResultPages;
import com.bossky.ocean.theme.Collect;
import com.bossky.ocean.theme.Theme;
import com.bossky.ocean.theme.ThemeService;
import com.bossky.ocean.user.Message;
import com.bossky.ocean.user.User;
import com.bossky.ocean.user.UserService;
import com.bossky.ocean.user.di.UserDi;

/**
 * 用户业务接口实现类
 * 
 * @author Zhao_Gq
 * 
 */
public class UserServiceImpl implements UserService {
	@Resource(name = "themeService")
	private ThemeService m_ThemeService;

	final UserDiImpl m_UserDi;
	DataManager<User> userDM;
	DataFactory factory;

	public UserServiceImpl(DataFactory factory) {
		this.factory = factory;
		m_UserDi = new UserDiImpl();
		userDM = factory.createDataManage(User.class, m_UserDi);
	}

	public User addUser(String username, String password, Integer role) {
		ResultPage<User> rp = listUser(username);
		while (rp.gotoPage(rp.getPage() + 1)) {
			while (rp.hasNext()) {
				if (rp.next().getUserName().equals(username)) {// 检测用户是否存在
					return null;
				}
			}
		}
		return new User(m_UserDi, username, password, role);
	}

	public User getUser(String id) {
		return userDM.get(id);
	}

	public ResultPage<User> listUser(String username) {
		return ResultPages.toResultPage(userDM.list(username));
	}

	class UserDiImpl implements UserDi {
		UserDiImpl() {

		}

		@Override
		public ResultPage<Collect> getCollocetThems(User user) {
			return m_ThemeService.getCollectThemes(user);
		}

		@Override
		public ResultPage<Message> getMessages(User user) {
			return m_ThemeService.getMessage(user);
		}

		@Override
		public ResultPage<Theme> getMyThemes(User user) {
			return m_ThemeService.getMyThemes(user);
		}

		@Override
		public Theme addTheme(User user, String title, String content) {
			return m_ThemeService.createTheme(user, title, content);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <E> DataManager<E> getDataManager(Class<? extends E> clazz) {
			return (DataManager<E>) factory.get(clazz.getSimpleName());
		}

	}

}

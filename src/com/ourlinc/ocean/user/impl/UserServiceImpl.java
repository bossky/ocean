package com.ourlinc.ocean.user.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import com.ourlinc.ocean.theme.Collect;
import com.ourlinc.ocean.theme.Theme;
import com.ourlinc.ocean.theme.ThemeService;
import com.ourlinc.ocean.user.Message;
import com.ourlinc.ocean.user.User;
import com.ourlinc.ocean.user.UserService;
import com.ourlinc.ocean.user.di.UserDi;
import com.ourlinc.tern.Persistent;
import com.ourlinc.tern.Persister;
import com.ourlinc.tern.ResultPage;
import com.ourlinc.tern.support.DataHub;

/**
 * 用户业务接口实现类
 * 
 * @author Zhao_Gq
 * 
 */
public class UserServiceImpl implements UserService {
	@Resource(name = "themeService")
	private ThemeService m_ThemeService;

	final DataHub m_DataHub;
	final UserDiImpl m_UserDi;
	final Persister<User> m_psUser;

	public UserServiceImpl(DataHub hub) throws ClassNotFoundException,
			SQLException {
		super();
		m_DataHub = hub;
		m_UserDi = new UserDiImpl();
		m_psUser = hub.createPersister(User.class, m_UserDi);
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
		return m_psUser.get(id);
	}

	public ResultPage<User> listUser(String username) {
		return m_psUser.startsWith(username);
	}

	class UserDiImpl implements UserDi {
		UserDiImpl() {

		}

		public <E extends Persistent> Persister<E> getPersister(Class<E> clazz) {
			return m_DataHub.getPersister(clazz);
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

	}

}

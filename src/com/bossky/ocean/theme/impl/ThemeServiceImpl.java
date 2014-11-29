package com.bossky.ocean.theme.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bossky.data.DataFactory;
import com.bossky.data.DataManager;
import com.bossky.data.search.CompareCondition;
import com.bossky.ocean.ext.Misc;
import com.bossky.ocean.ext.ResultPage;
import com.bossky.ocean.ext.ResultPages;
import com.bossky.ocean.theme.Collect;
import com.bossky.ocean.theme.Comments;
import com.bossky.ocean.theme.Label;
import com.bossky.ocean.theme.Operation;
import com.bossky.ocean.theme.Praise;
import com.bossky.ocean.theme.Reply;
import com.bossky.ocean.theme.Theme;
import com.bossky.ocean.theme.ThemeService;
import com.bossky.ocean.theme.di.ThemeDi;
import com.bossky.ocean.user.Message;
import com.bossky.ocean.user.User;
import com.bossky.ocean.user.UserService;

/**
 * 话题业务接口实现类
 * 
 * @author daibo
 *
 */
public class ThemeServiceImpl implements ThemeService {

	static Log _Logger = LogFactory.getLog(ThemeServiceImpl.class);
	@Resource(name = "userService")
	private UserService m_UserService;
	final DataManager<Label> labelDM;
	final DataManager<Theme> themeDM;
	final DataManager<Collect> collectDM;
	final DataManager<Comments> commentsDM;
	final DataManager<Reply> replyDM;
	final DataManager<Praise> praiseDM;
	final DataFactory factory;
	final ThemeDi m_ThemeDi;

	public ThemeServiceImpl(DataFactory factory) {
		this.factory = factory;
		m_ThemeDi = new ThemeDiImpl();
		themeDM = factory.createDataManage(Theme.class, m_ThemeDi);
		commentsDM = factory.createDataManage(Comments.class, m_ThemeDi);
		labelDM = factory.createDataManage(Label.class, m_ThemeDi);
		replyDM = factory.createDataManage(Reply.class, m_ThemeDi);
		collectDM = factory.createDataManage(Collect.class, m_ThemeDi);
		praiseDM = factory.createDataManage(Praise.class, m_ThemeDi);

	}

	@Override
	public Label addLabel(String name) {
		return new Label(m_ThemeDi, name);
	}

	@Override
	public ResultPage<Label> getLabels() {
		return ResultPages.toResultPage(labelDM.list(null));
	}

	@Override
	public Label getLabel(String id) {
		if (null == id) {
			return null;
		} else {
			id = id.trim();
		}
		return labelDM.get(id);
	}

	@Override
	public void removeLabel(Label label) {
		List<Label> list = ResultPages.toList(getLabels(),
				ResultPage.LIMIT_NONE);
		for (Label l : list) {
			Label parent = l.getParentLabel();
			if (null != parent && label.getId().equals(parent.getId())) {
				removeLabel(l);// 递归移除子标签
			}
		}
		// 找到标签有关的话题,更新标签
		List<Theme> ts = themeDM.search(new CompareCondition("labelList", label
				.getId().getId(), CompareCondition.COMP_OPTION_LIKE));
		for (Theme t : ts) {
			t.remvoeLabel(label);// 将要删除的标签从话题中移除
		}
		labelDM.remove(label.getId().getId());
	}

	@Override
	public Theme createTheme(User user, String title, String content) {
		return new Theme(m_ThemeDi, user, title, content);
	}

	@Override
	public Theme getTheme(String id) {
		return themeDM.get(id);
	}

	@Override
	public ResultPage<Comments> listComments(String userName, String title) {
		ResultPage<Comments> rp = ResultPages.toResultPage(commentsDM
				.list(null));
		if (!Misc.isEmpty(userName) || !Misc.isEmpty(title)) {
			List<Comments> list = new ArrayList<Comments>();
			while (rp.gotoPage(rp.getPage() + 1)) {
				while (rp.hasNext()) {
					Comments c = rp.next();

					if (!Misc.isEmpty(userName)
							&& !c.getCommentator().getUserName().toLowerCase()
									.contains(userName.toLowerCase())) {
						continue;// userName不为空且评论者的用户名不是userName开头的评论不要
					}
					if (!Misc.isEmpty(title)
							&& !c.getTheme().getTitle().toLowerCase()
									.contains(title.toLowerCase())) {
						continue;// title不为空且评论者的用户名不是title开头的评论不要
					}
					list.add(c);

				}
			}
			rp = ResultPages.toResultPage(list);
		}
		return rp;
	}

	@Override
	public ResultPage<Reply> listReply(String userName, String title) {
		ResultPage<Reply> rp = ResultPages.toResultPage(replyDM.list(null));
		if (!Misc.isEmpty(userName) || !Misc.isEmpty(title)) {
			List<Reply> list = new ArrayList<Reply>();
			while (rp.gotoPage(rp.getPage() + 1)) {
				while (rp.hasNext()) {
					Reply r = rp.next();
					if (!Misc.isEmpty(userName)
							&& !r.getReplyUser().getUserName().toLowerCase()
									.contains(userName.toLowerCase())) {
						continue;// userName不为空且回复者的用户名不是userName开头的回复不要
					}
					if (!Misc.isEmpty(title)
							&& !r.getTheme().getTitle().toLowerCase()
									.contains(title.toLowerCase())) {
						continue;// title不为空且回复者的用户名不是title开头的回复不要
					}
					list.add(r);

				}
			}
			rp = ResultPages.toResultPage(list);
		}
		return rp;
	}

	@Override
	public Comments getComments(String id) {
		return commentsDM.get(id);
	}

	@Override
	public Reply getReply(String id) {
		return replyDM.get(id);
	}

	@Override
	public ResultPage<Theme> listTheme(String type, String userName,
			String labelId) {
		Comparator<Theme> comparator = null;
		if ("comments".equals(type)) {
			comparator = Theme.COMMENTS_SORT;
		} else if ("view".equals(type)) {
			comparator = Theme.VIEW_SORT;
		} else if ("collcet".equals(type)) {
			comparator = Theme.COLLCET_SORT;
		} else if ("praise".equals(type)) {
			comparator = Theme.PRAISE_SORT;
		}
		if (null == comparator) {
			comparator = Theme.NEW_SORT;// 没有类型时默认按时间排序
		}
		ResultPage<Theme> rp = null;

		if (Misc.isEmpty(labelId)) {// labelId为null时查找出所有的话题
			rp = ResultPages.toResultPage(themeDM.list(null));
		} else {// labelId不为null是找出label有关的话题
			List<Theme> ts = themeDM.search(new CompareCondition("labelList",
					labelId, CompareCondition.COMP_OPTION_LIKE));
			rp = ResultPages.toResultPage(ts);
		}
		if (!Misc.isEmpty(userName)) {
			List<Theme> list = new ArrayList<Theme>();
			while (rp.gotoPage(rp.getPage() + 1)) {
				while (rp.hasNext()) {
					Theme t = rp.next();
					// 找出话题作者的用户名为userName开头的话题
					if (t.getUser().getUserName().toLowerCase()
							.contains(userName.toLowerCase())) {
						list.add(t);
					}
				}
			}
			rp = ResultPages.toResultPage(list);

		}
		// rp不为null是执行排序
		if (rp != null) {
			rp = ResultPages.toSortResultPage(rp, comparator,
					ResultPage.LIMIT_NONE);
		}
		return rp;
	}

	@Override
	public ResultPage<Collect> getCollectThemes(User user) {
		ResultPage<Collect> rp = ResultPages.toResultPage(collectDM.list(user
				.getId().getId()));
		// 默认按时间排序
		rp = ResultPages.toSortResultPage(rp, Collect.NEW_SORT,
				ResultPage.LIMIT_NONE);
		return rp;
	}

	@Override
	public ResultPage<Theme> getMyThemes(User user) {
		ResultPage<Theme> rp = ResultPages.toResultPage(themeDM.list(user
				.getId().getId()));
		// 默认按时间排序
		rp = ResultPages.toSortResultPage(rp, Theme.NEW_SORT,
				ResultPage.LIMIT_NONE);
		return rp;
	}

	@Override
	public ResultPage<Message> getMessage(User user) {
		List<Message> list = new ArrayList<Message>();
		ResultPage<Theme> trp = getMyThemes(user);
		// 查找我的话题中的评论消息
		while (trp.gotoPage(trp.getPage() + 1)) {
			while (trp.hasNext()) {
				Theme t = trp.next();
				List<Comments> cList = t.getCommentsList();
				if (null != cList) {
					for (Comments c : cList) {
						// 自己评论自己的话题的消息不要
						if (!c.getCommentator().getId()
								.equals(user.getId().getId())) {
							list.add(c);
						}
					}
				}
			}
		}
		List<Comments> cs = commentsDM.search(new CompareCondition("userId",
				user.getId().getId()));
		ResultPage<Comments> crp = ResultPages.toResultPage(cs);
		// 查找我的评论中的回复消息
		while (crp.gotoPage(crp.getPage() + 1)) {
			while (crp.hasNext()) {
				Comments c = crp.next();
				List<Reply> rList = c.getReplyList();
				if (null != rList) {
					for (Reply r : rList) {
						// 自己回复自己的评论的消息不要
						if (!r.getReplyUser().getId()
								.equals(user.getId().getId())) {
							list.add(r);
						}
					}
				}
			}
		}
		// 查找别人回复我的消息
		ResultPage<Reply> rrp = ResultPages.toResultPage(replyDM.list(null));
		while (rrp.gotoPage(rrp.getPage() + 1)) {
			while (rrp.hasNext()) {
				Reply r = rrp.next();
				if (Misc.isEmpty(r.getReplayerTargetId())) {

				} else {
					// 自己回复自己的不要
					if (r.getReplayerTargetId().equals(user.getId().getId())
							&& !r.getReplyUser().getId()
									.equals(user.getId().getId())) {
						list.add(r);
					}
				}
			}
		}
		ResultPage<Message> rp = ResultPages.toResultPage(list);
		// 按时间排序
		rp = ResultPages.toSortResultPage(rp, Comments.NEW_SORT,
				ResultPage.LIMIT_NONE);
		return rp;
	}

	class ThemeDiImpl implements ThemeDi {
		@Override
		public boolean cancelCollect(User user, Theme theme) {
			// 通过userId和themeId的组合找到收藏记录
			String id = user.getId().getId() + Operation.ID_TYPE
					+ theme.getId().getId();
			List<Collect> list = collectDM.list(id);
			if (list.size() > 1) {
				return collectDM.remove(list.get(0));
			}
			return false;
		}

		@Override
		public ResultPage<Comments> getComments(Theme theme) {
			return ResultPages.toResultPage(commentsDM.list(theme.getId()
					.getId()));
		}

		@Override
		public Label getLabel(String id) {
			if (null == id) {
				return null;
			} else {
				id = id.trim();
			}
			return labelDM.get(id);
		}

		@Override
		public List<Label> getChildList(Label label) {
			return labelDM.list(Label.ID_TYPE + label.getId().getId());
		}

		@Override
		public ResultPage<Reply> getReplys(Comments comments) {
			return ResultPages.toResultPage(replyDM.list(comments.getId()
					.getId()));
		}

		@Override
		public boolean isCollected(Theme theme, User user) {
			// 通过userId和themeId的组合找到收藏记录
			String id = user.getId().getId() + Operation.ID_TYPE
					+ theme.getId().getId();
			List<Collect> list = collectDM.list(id);
			return !list.isEmpty();
		}

		@Override
		public boolean isPraised(Theme theme, User user) {
			// 通过userId和themeId的组合找到赞记录
			String id = user.getId().getId() + Operation.ID_TYPE
					+ theme.getId().getId();
			List<Praise> list = praiseDM.list(id);
			return !list.isEmpty();
		}

		@Override
		public int getThemeNum(Label label) {
			List<Theme> ts = themeDM.search(new CompareCondition("labelList",
					label.getId().getId(), CompareCondition.COMP_OPTION_LIKE));
			ResultPage<Theme> rp = ResultPages.toResultPage(ts);
			if (null == rp) {
				return 0;
			} else {
				return rp.getCount();
			}
		}

		@Override
		public Theme getTheme(String id) {
			return themeDM.get(id);
		}

		@Override
		public Comments getComments(String id) {
			return commentsDM.get(id);
		}

		@Override
		public User getUser(String id) {
			return m_UserService.getUser(id);
		}

		@Override
		public Reply getReply(String m_ReplayerTargetId) {
			return replyDM.get(m_ReplayerTargetId);
		}

		@Override
		public <E> DataManager<E> getDataManager(Class<? extends E> clazz) {
			return factory.get(clazz.getSimpleName());
		}

	}
}

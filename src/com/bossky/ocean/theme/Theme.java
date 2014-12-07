package com.bossky.ocean.theme;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.bossky.data.business.Persistent;
import com.bossky.ocean.ext.ResultPage;
import com.bossky.ocean.ext.ResultPages;
import com.bossky.ocean.theme.di.ThemeDi;
import com.bossky.ocean.user.User;
import com.bossky.util.NameItem;

/**
 * 主题
 * 
 * @author daibo
 *
 */
public class Theme extends Persistent<ThemeDi> {
	@Resource
	private String userId;// 发表话题的用户Id
	@Resource
	private String title;
	@Resource
	private String content;
	@Resource
	private List<String> labelList;// 话题所属的标签Id的List
	@Resource
	private int viewNum = 0;// 浏览数
	@Resource
	private int collectNum = 0;// 收藏数
	@Resource
	private int praiseNum = 0;// 赞数
	@Resource
	private int commentsNum = 0;// 评论数
	@Resource
	private int status;
	@Resource
	private Date createDate;
	public static final NameItem STATUS_SHIELD = NameItem.valueOf(0, "已屏蔽");
	public static final NameItem STATUS_NORMAL = NameItem.valueOf(1, "正常");
	public static final NameItem[] ALL_STATUS = { STATUS_SHIELD, STATUS_NORMAL };
	public static final String REINDEX_LABEL = "L:";

	public Theme(ThemeDi di, User user, String title, String content) {
		super(di);
		// 使用userId构造话题id
		id = user.getId().getId()
				+ Long.toHexString(System.currentTimeMillis());
		userId = user.getId().getId();
		createDate = new Date();
		viewNum = 0;
		labelList = new ArrayList<String>();
		status = STATUS_NORMAL.id;
		this.title = title;
		this.content = content;
		markPersistenceUpdate();
	}

	/**
	 * 浏览数+1
	 */
	public synchronized void visit() {
		viewNum++;
		markPersistenceUpdate();
	}

	/**
	 * 拿到话题的所有评论
	 * 
	 * @return
	 */
	public List<Comments> getCommentsList() {
		ResultPage<Comments> rp = getBusinessDi().getComments(this);
		if (null == rp) {
			return null;
		} else {
			return ResultPages.toList(rp, ResultPage.LIMIT_NONE);
		}

	}

	public Comments getComments(String id) {
		Comments c = getBusinessDi().getComments(id);
		if (null != c && getId().getId().equals(c.getThemeId())) {
			return c;
		} else {
			return null;
		}
	}

	/**
	 * 获取话题的标签
	 * 
	 * @return
	 */
	public List<Label> getLabelList() {
		List<Label> result = new ArrayList<Label>();
		ThemeDi di = getBusinessDi();
		for (String id : labelList) {
			result.add(di.getLabel(id));
		}
		return result;
	}

	/**
	 * 返回话题标签
	 * 
	 * @return
	 */
	public String getLabelStr() {
		if (null == labelList || 0 == labelList.size()) {
			return null;
		}
		return labelList.toString();
	}

	/**
	 * 更新话题的标签
	 * 
	 * @param list
	 */
	public void updateLabelList(List<String> list) {
		labelList.clear();
		labelList.addAll(list);
		markPersistenceUpdate();
	}

	/**
	 * 拿到拥有的标签ID
	 * 
	 * @param id
	 * @return
	 */
	public List<String> getLabelListId() {
		return labelList;
	}

	/**
	 * 移除指定标签
	 * 
	 * @param label
	 */
	public void remvoeLabel(Label label) {
		List<String> list = new ArrayList<String>();
		list.addAll(labelList);
		if (list.remove(label.getId().getId())) {
			updateLabelList(list);
		}

	}

	/**
	 * 收藏话题
	 * 
	 * @param user
	 * @return
	 */
	public synchronized int collectTheme(User user) {
		// 用户已收藏则不能重复收藏
		if (isCollected(user)) {
			return collectNum;
		} else {
			new Collect(getBusinessDi(), user, this);
			collectNum++;
			markPersistenceUpdate();
			return collectNum;

		}
	}

	/**
	 * 是否已收藏
	 * 
	 * @param user
	 * @return
	 */
	public boolean isCollected(User user) {
		return getBusinessDi().isCollected(this, user);
	}

	public synchronized int cannalCollected(User user) {
		if (getBusinessDi().cancelCollect(user, this)) {
			collectNum--;
			markPersistenceUpdate();
		}
		return collectNum;
	}

	/**
	 * 赞话题
	 * 
	 * @param user
	 */
	public synchronized int praiseTheme(User user) {
		// 已赞不能重复赞
		if (isPraised(user)) {
			return praiseNum;
		} else {
			new Praise(getBusinessDi(), user, this);
			praiseNum++;
			markPersistenceUpdate();
			return praiseNum;
		}

	}

	/**
	 * 是否已赞
	 * 
	 * @param user
	 * @return
	 */
	public boolean isPraised(User user) {
		return getBusinessDi().isPraised(this, user);
	}

	/**
	 * 评论话题
	 * 
	 * @param user
	 */
	public synchronized Comments commentTheme(User user, String content) {
		// 被屏蔽后不能被评论
		if (isShield()) {
			return null;
		}
		if (!user.getId().getId().equals(userId)) {
			getUser().acquireMessage();
		}
		commentsNum++;
		return new Comments(getBusinessDi(), this, user, content);
	}

	/**
	 * 屏蔽话题
	 */
	public void shield() {
		status = STATUS_SHIELD.id;
		markPersistenceUpdate();
	}

	/**
	 * 恢复话题
	 */
	public void recover() {
		status = STATUS_NORMAL.id;
		markPersistenceUpdate();
	}

	/**
	 * 是否已屏蔽
	 * 
	 * @return
	 */
	public boolean isShield() {
		if (STATUS_SHIELD.id == status) {
			return true;
		}
		return false;
	}

	/**
	 * 获得发表话题的用户
	 * 
	 * @return
	 */
	public User getUser() {
		return getBusinessDi().getUser(userId);
	}

	public String getUserId() {
		return userId;
	}

	/**
	 * 获取浏览数
	 * 
	 * @return
	 */
	public int getViewNum() {
		return viewNum;
	}

	/**
	 * 获取收藏数
	 * 
	 * @return
	 */
	public int getCollectNum() {
		return collectNum;
	}

	/**
	 * 获取赞数
	 * 
	 * @return
	 */
	public int getPraiseNum() {
		return praiseNum;
	}

	/**
	 * 获取评论数
	 * 
	 * @return
	 */
	public int getCommentsNum() {
		return commentsNum;

	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		if (isShield()) {
			return "<span style='color:red'>该话题被已屏蔽</span>";
		}
		return content;
	}

	public String getNoHTMLContent() {
		if (isShield()) {
			return "<span style='color:red'>该话题被已屏蔽</span>";
		}
		return content.replaceAll("<.*?>", "");
	}

	public Date getCreateDate() {
		return createDate;
	}

	protected Theme(ThemeDi di) {
		super(di);

	}

	public static final Comparator<Theme> NEW_SORT = new Comparator<Theme>() {
		@Override
		public int compare(Theme t1, Theme t2) {

			if (null == t1 || null == t2) {
				return -1;
			}
			return t1.getCreateDate().after(t2.getCreateDate()) ? 0 : 1;
		}
	};
	public static final Comparator<Theme> COMMENTS_SORT = new Comparator<Theme>() {
		@Override
		public int compare(Theme t1, Theme t2) {

			if (null == t1 || null == t2) {
				return -1;
			}
			return t1.commentsNum > t2.commentsNum ? 0 : 1;
		}
	};
	public static final Comparator<Theme> VIEW_SORT = new Comparator<Theme>() {
		@Override
		public int compare(Theme t1, Theme t2) {

			if (null == t1 || null == t2) {
				return -1;
			}
			return t1.viewNum > t2.viewNum ? 0 : 1;
		}
	};
	public static final Comparator<Theme> COLLCET_SORT = new Comparator<Theme>() {
		@Override
		public int compare(Theme t1, Theme t2) {

			if (null == t1 || null == t2) {
				return -1;
			}
			return t1.collectNum > t2.collectNum ? 0 : 1;
		}
	};
	public static final Comparator<Theme> PRAISE_SORT = new Comparator<Theme>() {
		@Override
		public int compare(Theme t1, Theme t2) {

			if (null == t1 || null == t2) {
				return -1;
			}
			return t1.praiseNum > t2.praiseNum ? 0 : 1;
		}
	};

}

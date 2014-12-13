package com.bossky.ocean.theme;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.bossky.data.business.Persistent;
import com.bossky.ocean.ext.ResultPage;
import com.bossky.ocean.ext.ResultPages;
import com.bossky.ocean.theme.di.ThemeDi;
import com.bossky.ocean.user.Message;
import com.bossky.ocean.user.OceanUser;
import com.bossky.util.NameItem;

/**
 * 评论业务类
 * 
 * @author daibo
 *
 */
public class Comments extends Persistent<ThemeDi> implements Message {
	@Resource
	private String themeId;// 所评论的话题的Id
	@Resource
	private String commentetatorId;// 评论发表者
	@Resource
	private String content;// 内容
	@Resource
	private Date createDate;// 发表时间
	@Resource
	private int status;
	public static final NameItem STATUS_SHIELD = NameItem.valueOf(0, "已屏蔽");
	public static final NameItem STATUS_NORMAL = NameItem.valueOf(1, "正常");
	public static final NameItem[] ALL_STATUS = { STATUS_SHIELD, STATUS_NORMAL };
	public static final String REINDEX_TYPE = "C:";

	public Comments(ThemeDi di, Theme theme, OceanUser user, String content) {
		super(di);
		id = theme.getId().getId() + Long.valueOf(System.currentTimeMillis());
		themeId = theme.getId().getId();
		commentetatorId = user.getId().getId();
		this.content = content;
		createDate = new Date();
		status = STATUS_NORMAL.id;
		markPersistenceUpdate();
	}

	/**
	 * 获得所评论的主题
	 * 
	 * @return
	 */
	public Theme getTheme() {
		return getBusinessDi().getTheme(themeId);
	}

	public String getThemeId() {
		return themeId;
	}

	/**
	 * 获得评论者
	 * 
	 * @return
	 */
	public OceanUser getCommentator() {
		return getBusinessDi().getUser(commentetatorId);
	}

	/**
	 * 是否被屏蔽
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
	 * 屏蔽
	 */
	public void shield() {
		status = STATUS_SHIELD.id;
		markPersistenceUpdate();
	}

	/**
	 * 恢复
	 */
	public void recover() {
		status = STATUS_NORMAL.id;
		markPersistenceUpdate();
	}

	/**
	 * 获取所有回复
	 * 
	 * @return
	 */
	public List<Reply> getReplyList() {
		ResultPage<Reply> rp = getBusinessDi().getReplys(this);
		if (null == rp) {
			return null;
		} else {
			return ResultPages.toList(rp, ResultPage.LIMIT_NONE);
		}
	}

	/**
	 * 回复评论
	 * 
	 * @param content
	 * @return
	 */
	public Reply reply(OceanUser user, String content) {
		// 被屏蔽时不能回复
		if (isShield()) {
			return null;
		}
		OceanUser commentator = getCommentator();
		// 如果话题发表者不是评论者则新消息加1
		if (!user.getId().getId().equals(commentator.getId().getId())) {
			getCommentator().acquireMessage();
		}
		return new Reply(getBusinessDi(), this, user, content);
	}

	public String getContent() {
		// 评论被屏蔽时不显示消息
		if (isShield()) {
			return "<span style='color:red'>该评论已被屏蔽</span>";
		}
		return content;
	}

	public String getNoHTMLContent() {
		return content.replaceAll("<.*?>", "");
	}

	public Date getCreateDate() {
		return createDate;
	}

	protected Comments(ThemeDi arg0) {
		super(arg0);
	}

	@Override
	public OceanUser getSender() {
		return getCommentator();
	}

	@Override
	public int getType() {
		return Message.COMMENT;
	}

	// 按时间降序排序的消息排序器
	public static final Comparator<Message> NEW_SORT = new Comparator<Message>() {
		@Override
		public int compare(Message t1, Message t2) {
			if (null == t1 || null == t2) {
				return -1;
			}
			return t1.getCreateDate().after(t2.getCreateDate()) ? 0 : 1;
		}
	};
	// 按时间数降序排序的评论排序器
	public static final Comparator<Comments> TIME_SORT = new Comparator<Comments>() {
		@Override
		public int compare(Comments t1, Comments t2) {
			if (null == t1 || null == t2) {
				return -1;
			}
			return t1.getCreateDate().after(t2.getCreateDate()) ? 0 : 1;
		}
	};
}

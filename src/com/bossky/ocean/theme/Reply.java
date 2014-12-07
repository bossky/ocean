package com.bossky.ocean.theme;

import java.util.Comparator;
import java.util.Date;

import javax.annotation.Resource;

import com.bossky.data.business.Persistent;
import com.bossky.ocean.theme.di.ThemeDi;
import com.bossky.ocean.user.Message;
import com.bossky.ocean.user.User;
import com.bossky.util.NameItem;

/**
 * 回复业务类
 * 
 * @author daibo
 *
 */
public class Reply extends Persistent<ThemeDi> implements Message {
	@Resource
	private String commentsId;// 所回复的评论的ID
	@Resource
	private String replayerId;// 回复发表者的ID
	@Resource
	private String replayerTargetId;// 所回复的回复记录的ID.
	@Resource
	private String content;
	@Resource
	private Date createDate;
	@Resource
	private int m_Status;
	public static final NameItem STATUS_SHIELD = NameItem.valueOf(0, "已屏蔽");
	public static final NameItem STATUS_NORMAL = NameItem.valueOf(1, "正常");
	public static final NameItem[] ALL_STATUS = { STATUS_SHIELD, STATUS_NORMAL };

	public Reply(ThemeDi di, Comments comments, User user, String content) {
		super(di);
		id = comments.getId().getId()
				+ Long.toHexString(System.currentTimeMillis());
		commentsId = comments.getId().getId();
		replayerId = user.getId().getId();
		this.content = content;
		createDate = new Date();
		m_Status = STATUS_NORMAL.id;
		markPersistenceUpdate();
	}

	public Reply(ThemeDi di, Comments comments, User user, String content,
			Reply reply) {
		super(di);
		id = comments.getId().getId()
				+ Long.toHexString(System.currentTimeMillis());
		commentsId = comments.getId().getId();
		replayerId = user.getId().getId();
		replayerTargetId = reply.getId().getId();
		this.content = content;
		createDate = new Date();
		m_Status = STATUS_NORMAL.id;
		markPersistenceUpdate();
	}

	/**
	 * 获得评论者对象
	 * 
	 * @return
	 */
	public User getReplyUser() {
		return getBusinessDi().getUser(replayerId);
	}

	/**
	 * 返回所回复的对象
	 * 
	 * @return
	 */
	public Reply getReplayerTarget() {
		return getBusinessDi().getReply(replayerTargetId);
	}

	public String getReplayerTargetId() {
		return replayerTargetId;
	}

	/**
	 * 回复中再回复的方法
	 * 
	 * @param user
	 * @param content
	 * @return
	 */
	public Reply replyAgain(User user, String content) {
		// 被屏蔽时不能被回复
		if (isShield()) {
			return null;
		}
		// 如果不是自己回复自己的信息，加被回复者新消息数加1
		if (!user.getId().getId().equals(getReplyUser().getId().getId())) {
			getReplyUser().acquireMessage();
		}
		return new Reply(getBusinessDi(), this.getComments(), user, content,
				this);
	}

	/**
	 * 是否被屏蔽
	 * 
	 * @return
	 */
	public boolean isShield() {
		if (STATUS_SHIELD.id == m_Status) {
			return true;
		}
		return false;
	}

	/**
	 * 屏蔽
	 */
	public void shield() {
		m_Status = STATUS_SHIELD.id;
		markPersistenceUpdate();
	}

	/**
	 * 恢复
	 */
	public void recover() {
		m_Status = STATUS_NORMAL.id;
		markPersistenceUpdate();
	}

	/**
	 * 获得所回复的评论
	 * 
	 * @return
	 */
	public Comments getComments() {
		return getBusinessDi().getComments(commentsId);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getContent() {
		// 被屏蔽时不显示内容
		if (isShield()) {
			return "<span style='color:red'>该回复已被屏蔽</span>";
		}
		return content;
	}

	public String getNoHTMLContent() {
		return content.replaceAll("<.*?>", "");
	}

	protected Reply(ThemeDi arg0) {
		super(arg0);
	}

	@Override
	public User getSender() {
		return getReplyUser();
	}

	@Override
	public Theme getTheme() {
		return getComments().getTheme();
	}

	@Override
	public int getType() {
		return Message.REPLAY;
	}

	public static final Comparator<Reply> TIME_SORT = new Comparator<Reply>() {
		@Override
		public int compare(Reply t1, Reply t2) {
			if (null == t1 || null == t2) {
				return -1;
			}
			return t1.getCreateDate().after(t2.getCreateDate()) ? 0 : 1;
		}
	};
}

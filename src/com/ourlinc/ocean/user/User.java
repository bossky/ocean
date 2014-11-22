package com.ourlinc.ocean.user;

import java.util.Date;

import javax.annotation.Resource;

import com.ourlinc.ocean.theme.Collect;
import com.ourlinc.ocean.theme.Theme;
import com.ourlinc.ocean.user.di.UserDi;
import com.ourlinc.tern.NameItem;
import com.ourlinc.tern.ResultPage;
import com.ourlinc.tern.support.AbstractPersistent;

/**
 * 用户业务对象
 * 
 * @author Zhao_Gq
 * 
 */
public class User extends AbstractPersistent<UserDi> {
	/** 用户账号（登录名）与邮箱相同 */
	@Resource
	private String m_UserName;
	/** 用户密码 */
	@Resource
	private String m_Password;
	/** 昵称 */
	@Resource
	private String m_NickName;
	/** 邮箱 */
	@Resource
	private String m_Email;
	/** 手机号码 */
	@Resource
	private String m_Phone;
	/** QQ */
	@Resource
	private String m_QQ;
	/** 毕业院校 */
	@Resource
	private String m_School;
	/** 最高学历 */
	@Resource
	private String m_Education;
	/** 工作职位 */
	@Resource
	private String m_Job;
	/** 个性签名 */
	@Resource
	private String m_Signature;
	/** 培训日期 */
	@Resource
	private Date m_TrainingDate;
	/** 角色 */
	@Resource
	private int m_Role;
	/** 状态 */
	@Resource
	private int m_Status;
	/** 新消息的数量 */
	@Resource
	private int m_NewMessageNum = 0;

	public static final NameItem STATUS_NORMAL = new NameItem("正常", 0);
	public static final NameItem STATUS_BLACK = new NameItem("黑色单", 1);
	public static final NameItem ROLE_ADMIN = new NameItem("管理员", 0);
	public static final NameItem ROLE_USER = new NameItem("普通用户", 1);
	public static final NameItem[] ALL_ROLE = { ROLE_ADMIN, ROLE_USER };
	public static final NameItem[] ALL_STATUS = { STATUS_NORMAL, STATUS_BLACK };

	/**
	 * 业务构造方法
	 * 
	 * @param di
	 * @param username
	 *            用户账号
	 * @param password
	 *            密码
	 * @param role
	 *            角色
	 */
	public User(UserDi di, String username, String password, Integer role) {
		super(di);
		m_Id = getPersister().getNewId(username);
		m_UserName = username;
		m_NickName = username;
		m_Password = password;
		m_Role = role;
		m_Email = username;
		m_Status = STATUS_NORMAL.id;
		markPersistenceUpdate();
	}

	/**
	 * @param di
	 */
	protected User(UserDi di) {
		super(di);
	}

	/**
	 * 获得我的消息
	 * 
	 * @return
	 */
	public ResultPage<Message> getMessage() {
		clearMessageNum();
		return this.getBusinessDi().getMessages(this);

	}

	/**
	 * 获得我收藏的话题
	 * 
	 * @return
	 */
	public ResultPage<Collect> getCollectThemes() {
		return this.getBusinessDi().getCollocetThems(this);
	}

	/**
	 * 获得我发表的话题
	 * 
	 * @return
	 */
	public ResultPage<Theme> getMyThemes() {
		return this.getBusinessDi().getMyThemes(this);
	}

	/**
	 * 更新密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 */
	public boolean updatePassword(String oldPassword, String newPassword) {
		if (oldPassword.equals(m_Password)) {
			m_Password = newPassword;
			markPersistenceUpdate();
			return true;
		}
		return false;
	}

	/**
	 * 重置密码
	 * 
	 * @param password
	 * @return
	 */
	public void resetPassword(String password) {
		m_Password = password;
		markPersistenceUpdate();
	}

	/**
	 * 检查用户密码是否正确
	 * 
	 * @param password
	 * @return
	 */
	public boolean checkPassword(String password) {
		if (password.equals(m_Password)) {
			return true;
		}
		return false;
	}

	/**
	 * 拉黑
	 */
	public void pullBlack() {
		m_Status = STATUS_BLACK.id;
		markPersistenceUpdate();
	}

	/**
	 * 恢复
	 */
	public void rescover() {
		m_Status = STATUS_NORMAL.id;
		markPersistenceUpdate();
	}

	/**
	 * 是否黑名
	 * 
	 * @return
	 */
	public boolean isBlack() {
		if (STATUS_NORMAL.id == m_Status) {
			return false;
		} else if (STATUS_BLACK.id == m_Status) {
			return true;
		}
		return false;
	}

	/**
	 * 发表话题
	 * 
	 * @param content
	 * @param title
	 */
	public Theme addTheme(String title, String content) {
		return getBusinessDi().addTheme(this, title, content);

	}

	/**
	 * 获得消息，将未读消息数+1
	 */
	public void acquireMessage() {
		synchronized (this) {
			m_NewMessageNum++;
		}
		markPersistenceUpdate();
	}

	/**
	 * 将未读消息归0
	 */
	public void clearMessageNum() {
		m_NewMessageNum = 0;
		markPersistenceUpdate();
	}

	public int getNewMessageNum() {
		return m_NewMessageNum;
	}

	/**
	 * 判断用户是否为管理员
	 * 
	 * @return
	 */
	public boolean isAdmin() {
		if (ROLE_ADMIN.id == m_Role) {
			return true;
		}
		return false;
	}

	public String getUserName() {
		return m_UserName;
	}

	public String getNickName() {
		return m_NickName;
	}

	public void setNickName(String nickName) {
		m_NickName = nickName;
		markPersistenceUpdate();
	}

	public String getEmail() {
		return m_Email;
	}

	public String getPhone() {
		return m_Phone;
	}

	public void setPhone(String phone) {
		m_Phone = phone;
		markPersistenceUpdate();
	}

	public String getQQ() {
		return m_QQ;
	}

	public void setQQ(String QQ) {
		m_QQ = QQ;
		markPersistenceUpdate();
	}

	public String getSchool() {
		return m_School;
	}

	public void setSchool(String school) {
		m_School = school;
		markPersistenceUpdate();
	}

	public String getEducation() {
		return m_Education;
	}

	public void setEducation(String education) {
		m_Education = education;
		markPersistenceUpdate();
	}

	public String getJob() {
		return m_Job;
	}

	public void setJob(String job) {
		m_Job = job;
		markPersistenceUpdate();
	}

	public String getSignature() {
		return m_Signature;
	}

	public void setSignature(String signature) {
		m_Signature = signature;
		markPersistenceUpdate();
	}

	public Date getTrainingDate() {
		return m_TrainingDate;
	}

	public void setTrainingDate(Date trainingDate) {
		m_TrainingDate = trainingDate;
		markPersistenceUpdate();
	}
}

package com.bossky.ocean.user;

import java.util.Date;

import javax.annotation.Resource;

import com.bossky.data.business.Persistent;
import com.bossky.ocean.ext.ResultPage;
import com.bossky.ocean.theme.Collect;
import com.bossky.ocean.theme.Theme;
import com.bossky.ocean.user.di.UserDi;
import com.bossky.util.NameItem;

/**
 * 用户业务对象
 * 
 * @author Zhao_Gq
 * 
 */
public class User extends Persistent<UserDi> {
	/** 用户账号（登录名）与邮箱相同 */
	@Resource
	private String userName;
	/** 用户密码 */
	@Resource
	private String password;
	/** 昵称 */
	@Resource
	private String nickName;
	/** 邮箱 */
	@Resource
	private String email;
	/** 手机号码 */
	@Resource
	private String phone;
	/** QQ */
	@Resource
	private String QQ;
	/** 毕业院校 */
	@Resource
	private String school;
	/** 最高学历 */
	@Resource
	private String education;
	/** 工作职位 */
	@Resource
	private String job;
	/** 个性签名 */
	@Resource
	private String signature;
	/** 培训日期 */
	@Resource
	private Date trainingDate;
	/** 角色 */
	@Resource
	private int role;
	/** 状态 */
	@Resource
	private int status;
	/** 新消息的数量 */
	@Resource
	private int newMessageNum = 0;

	public static final NameItem STATUS_NORMAL = NameItem.valueOf(0, "正常");
	public static final NameItem STATUS_BLACK = NameItem.valueOf(1, "黑色单");
	public static final NameItem ROLE_ADMIN = NameItem.valueOf(0, "管理员");
	public static final NameItem ROLE_USER = NameItem.valueOf(1, "普通用户");
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
		id = username;
		userName = username;
		nickName = username;
		this.password = password;
		this.role = role;
		email = username;
		status = STATUS_NORMAL.id;
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
		if (oldPassword.equals(this.password)) {
			password = newPassword;
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
		this.password = password;
		markPersistenceUpdate();
	}

	/**
	 * 检查用户密码是否正确
	 * 
	 * @param password
	 * @return
	 */
	public boolean checkPassword(String password) {
		if (password.equals(password)) {
			return true;
		}
		return false;
	}

	/**
	 * 拉黑
	 */
	public void pullBlack() {
		status = STATUS_BLACK.id;
		markPersistenceUpdate();
	}

	/**
	 * 恢复
	 */
	public void rescover() {
		status = STATUS_NORMAL.id;
		markPersistenceUpdate();
	}

	/**
	 * 是否黑名
	 * 
	 * @return
	 */
	public boolean isBlack() {
		if (STATUS_NORMAL.id == status) {
			return false;
		} else if (STATUS_BLACK.id == status) {
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
			newMessageNum++;
		}
		markPersistenceUpdate();
	}

	/**
	 * 将未读消息归0
	 */
	public void clearMessageNum() {
		newMessageNum = 0;
		markPersistenceUpdate();
	}

	public int getNewMessageNum() {
		return newMessageNum;
	}

	/**
	 * 判断用户是否为管理员
	 * 
	 * @return
	 */
	public boolean isAdmin() {
		if (ROLE_ADMIN.id == role) {
			return true;
		}
		return false;
	}

	public String getUserName() {
		return userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
		markPersistenceUpdate();
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
		markPersistenceUpdate();
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String QQ) {
		this.QQ = QQ;
		markPersistenceUpdate();
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
		markPersistenceUpdate();
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
		markPersistenceUpdate();
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
		markPersistenceUpdate();
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
		markPersistenceUpdate();
	}

	public Date getTrainingDate() {
		return trainingDate;
	}

	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
		markPersistenceUpdate();
	}
}

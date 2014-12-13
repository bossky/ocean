package com.bossky.business;

import javax.annotation.Resource;

import com.bossky.data.DataManager;

/**
 * 业务类
 * 
 * @author bo
 *
 */
public class Business<E extends Assistant> {
	/** 业务助手 */
	volatile E assistant;
	/** 数据管理 */
	volatile DataManager<E> dataManager;
	volatile int version;

	@Resource
	protected String id;

	public Business(E assistant) {
		this.assistant = assistant;
	}

	/**
	 * 生成新的id
	 */
	protected void genNewId() {
		this.id = Long.toHexString(System.currentTimeMillis());
	}

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 保存数据
	 */
	public void save() {
		assistant.getDataManager(getClass()).save(this);
	}

	/**
	 * 获取助手
	 * 
	 * @return
	 */
	protected E getAssistant() {
		return assistant;
	}
}

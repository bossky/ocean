package com.bossky.business;

import javax.annotation.Resource;

/**
 * 业务类
 * 
 * @author bo
 *
 */
public abstract class AbstractEntity<E extends Assistant> implements Entity {
	/** 助手 */
	volatile E assistant;

	@Resource
	protected String id;

	public AbstractEntity(E assistant) {
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
		assistant.save(this);
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

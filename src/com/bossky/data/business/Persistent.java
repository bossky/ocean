package com.bossky.data.business;

import javax.annotation.Resource;

import com.bossky.data.annotation.Id;

public class Persistent<E extends Di> {
	protected E di;
	@Id
	@Resource
	protected String id;

	public UniteId getId() {
		return UniteId.valueOf(id);
	}

	public Persistent(E di) {
		this.di = di;
	}

	public E getBusinessDi() {
		return di;
	}

	public void markPersistenceUpdate() {
		di.getDataManager(getClass()).save(this);
	}
}

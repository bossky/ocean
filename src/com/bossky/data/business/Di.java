package com.bossky.data.business;

import com.bossky.data.DataManager;

public interface Di {

	public <E extends Object> DataManager<E> getDataManager(
			Class<? extends E> clazz);
}

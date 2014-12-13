package com.bossky.business;

import com.bossky.data.DataManager;

/**
 * 助手
 * 
 * @author bo
 *
 */
public interface Assistant {
	public <E> DataManager<E> getDataManager(Class<? extends E> clazz);
}

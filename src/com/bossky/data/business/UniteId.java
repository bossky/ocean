package com.bossky.data.business;

public class UniteId {
	private String id;

	public UniteId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public static UniteId valueOf(String id) {
		return new UniteId(id);
	}

}

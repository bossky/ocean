package com.bossky.ocean.ext;

public class NameItem {

	public int id;
	public String name;
	public Object value;

	public NameItem(String name, int id) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
}

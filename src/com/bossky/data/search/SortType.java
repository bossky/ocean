package com.bossky.data.search;

public class SortType {
	/** 排序方式－默认 */
	public static int OPTION_DEFAULT = 0x01;
	/** 排序方式－升序 */
	public static int OPTION_ASC = 0x02;
	/** 排序方式－降序 */
	public static int OPTION_DESC = 0x03;

	String fieldName;
	int type;

	protected SortType(String fieldName, int type) {
		this.fieldName = fieldName;
		this.type = type;
	}

	public String getFieldName() {
		return fieldName;
	}

	public int getType() {
		return type;
	}

	public static SortType getDefaultSortType(String fieldName) {
		return new SortType(fieldName, OPTION_ASC);
	}

	public static SortType getAscSortType(String fieldName) {
		return new SortType(fieldName, OPTION_ASC);
	}

	public static SortType getDescSortType(String fieldName) {
		return new SortType(fieldName, OPTION_DESC);
	}
}

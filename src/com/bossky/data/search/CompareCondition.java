package com.bossky.data.search;

/**
 * 比较条件
 * 
 * @author bo
 *
 */
public class CompareCondition implements SearchCondition {
	
	/** 属性名 */
	String fieldName;
	/** 值 */
	Object value;
	/** 比较方式 */
	int option;

	public CompareCondition(String fieldName, Object value) {
		this(fieldName,value,COMP_OPTION_EQUALS);
	}

	public CompareCondition(String fieldName, Object value,int option) {
		this.fieldName = fieldName;
		this.option = option;
		this.value = value;
	}

	public String getFieldName() {
		return fieldName;
	}

	public int getOption() {
		return option;
	}

	public Object getValue() {
		return value;
	}
}

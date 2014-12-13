package com.bossky.user.xml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bossky.user.Right;
import com.bossky.util.Indexable;

/**
 * 从xml配置文件生成的权限
 * 
 * @author bo
 *
 */
public class XmlRight implements Right, Indexable {
	/** 权限id */
	protected int id;
	/** 权限 */
	protected String type;
	/** 名称 */
	protected String name;
	/** 描述 */
	protected String desc;
	/** 匹配规则 */
	protected String rule;

	protected XmlRight(int id, String type, String name, String desc,
			String rule) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.desc = desc;
		this.rule = rule;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public int index() {
		return id;
	}

	public String getId() {
		return String.valueOf(id);
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getRule() {
		return rule;
	}

	@Override
	public boolean isMatch(String url) {
		Pattern pattern = Pattern.compile(rule);
		Matcher m = pattern.matcher(url);
		boolean falg = m.find();
		pattern = null;
		m = null;
		return falg;
	}

	@Override
	public String toString() {
		return "[id=" + id + ",name=" + name + "]";
	}

	public static XmlRight valueOf(int id, String type, String name,
			String desc, String rule) {
		return new XmlRight(id, type, name, desc, rule);
	}

}

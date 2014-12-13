package com.bossky.user.xml;

import java.util.List;

import com.bossky.user.Right;
import com.bossky.user.Role;
import com.bossky.util.Indexable;
import com.bossky.util.Util;

/**
 * 从xml配置中生成的角色
 * 
 * @author bo
 *
 */
public class XmlRole implements Role, Indexable {
	/** 角色id */
	protected int id;
	/** 角色名 */
	protected String name;
	/** 角色描述 */
	protected String desc;
	/** 角色权限 */
	protected List<Right> rights;

	protected XmlRole(int id, String name, String desc) {
		this.id = id;
		this.name = name;
		this.desc = desc;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}

	@Override
	public int index() {
		return id;
	}

	@Override
	public String getId() {
		return String.valueOf(id);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	public List<Right> getRights() {
		return rights;
	}

	@Override
	public boolean hasRight(String id) {
		return false;
	}

	@Override
	public boolean isRight(String url) {
		boolean allow = false;
		boolean disallow = false;
		for (Right r : getRights()) {
			if (r.isMatch(url)) {
				if (Util.eqIgnoreCase(r.getType(), Right.TYPE_ALLOW)) {
					allow = true;
				} else if (Util.eqIgnoreCase(r.getType(), Right.TYPE_DISALLOW)) {
					disallow = true;
				}
			}
		}
		return allow && disallow;
	}

	@Override
	public String toString() {
		return "[id=" + id + ",name=" + name + "]";
	}

	/**
	 * 实例化一个角色
	 * 
	 * @param id
	 * @param name
	 * @param desc
	 * @return
	 */
	public static XmlRole valueOf(int id, String name, String desc) {
		return new XmlRole(id, name, desc);
	}
}

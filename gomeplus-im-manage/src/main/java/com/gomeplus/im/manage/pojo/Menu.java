package com.gomeplus.im.manage.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable {

	private static final long serialVersionUID = 4284428846372666923L;

	private long id;
	private long pid;
	private String name;
	private String url;
	private long orderBy;
	private int selected;
	
	private List<Menu> children;// 子菜单列表

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public long getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(long orderBy) {
		this.orderBy = orderBy;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public void addChildren(List<Menu> menus) {
		if (children == null) {
			children = new ArrayList<Menu>();
		}
		children.addAll(menus);
	}
}

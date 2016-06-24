package cn.com.gome.manage.pojo;

import java.io.Serializable;

public class UserPermit implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private long uid;
	private long menuId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}
}

package com.gomeplus.im.manage.mongodb.model;

public class AppAccount {
	
	private String appId;
	private String uId;
	private String uName;
	private String createTime;
	private String uDesc;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getuDesc() {
		return uDesc;
	}

	public void setuDesc(String uDesc) {
		this.uDesc = uDesc;
	}
}

package com.gome.im.api.db.model;

import java.io.Serializable;

public class User implements Serializable {

	private long id;
	private long imUserId;
	private String appId;
	private String phoneNumber;
	private String userName;
	private String password;
	private String nickName;
	private String avatar;
	private int gender;  //  0:男  1:女
	private String region;
	private int birthday;
	private String autograph;//签名
	private long createTime;
	private long updateTime;
	private String token;
	private long tokenValidity;
	private int mobileClientId;
	private int pcClientId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getBirthday() {
		return birthday;
	}

	public void setBirthday(int birthday) {
		this.birthday = birthday;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getTokenValidity() {
		return tokenValidity;
	}

	public void setTokenValidity(long tokenValidity) {
		this.tokenValidity = tokenValidity;
	}

	public int getMobileClientId() {
		return mobileClientId;
	}

	public void setMobileClientId(int mobileClientId) {
		this.mobileClientId = mobileClientId;
	}

	public int getPcClientId() {
		return pcClientId;
	}

	public void setPcClientId(int pcClientId) {
		this.pcClientId = pcClientId;
	}

	public long getImUserId() {
		return imUserId;
	}

	public void setImUserId(long imUserId) {
		this.imUserId = imUserId;
	}
}

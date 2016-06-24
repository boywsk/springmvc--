package cn.com.gome.manage.mongodb.model;

import java.io.Serializable;

public class UserInfo implements Serializable {

	/**
	 * 用户信息
	 */
	private static final long serialVersionUID = 1L;
	private long uid;
	private String nickName;
	private int deviceType;
	private String deviceToken;
	private long createTime;
	private long updateTime;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
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

	// private String formateCreateTime;
	// private String formateUpdateTime;
	// private boolean status;
	// private String devive;
	// private String gateIp;
	// private int gatePort;
	// private String deviceName;

}

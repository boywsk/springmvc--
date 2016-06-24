package com.gomeplus.im.manage.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * im用户信息
 */
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private long uid;
	private String nickName;
	private int deviceType;
	private String deviceToken;
	private long createTime;
	private long updateTime;
	private String formateCreateTime;
	private String formateUpdateTime;
	private boolean status;
	private String devive;
	private String gateIp;
	private int gatePort;
	private String deviceName;

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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDevive() {
		return devive;
	}

	public void setDevive(String devive) {
		this.devive = devive;
	}

	public String getGateIp() {
		return gateIp;
	}

	public void setGateIp(String gateIp) {
		this.gateIp = gateIp;
	}

	public int getGatePort() {
		return gatePort;
	}

	public void setGatePort(int gatePort) {
		this.gatePort = gatePort;
	}

	public String getDeviceName() {
		if(status) {
			deviceName = devive;
		} else {
			if(deviceType == 10) {
				deviceName = "IOS";
			} else if(deviceType == 11) {
				deviceName = "Android";
			}
			
		}
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public String getFormateCreateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formateCreateTime = formatter.format(new Date(createTime));
		return formateCreateTime;
	}

	public void setFormateCreateTime(String formateCreateTime) {
		this.formateCreateTime = formateCreateTime;
	}

	public String getFormateUpdateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formateUpdateTime = formatter.format(new Date(updateTime));
		return formateUpdateTime;
	}

	public void setFormateUpdateTime(String formateUpdateTime) {
		this.formateUpdateTime = formateUpdateTime;
	}
}

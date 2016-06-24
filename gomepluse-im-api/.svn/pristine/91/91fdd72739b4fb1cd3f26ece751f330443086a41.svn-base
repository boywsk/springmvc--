package com.gomeplus.im.api.request.response;

import java.io.Serializable;

public class RspGroupMember implements Serializable {

	private String groupId; // 群组
	  private int groupIdHash;
	private long userId; // 成员id
	private String nickName;// 成员昵称
	private long joinTime; // 加入时间

	private String mark;//对群组成员的备注名称

    private int identity;//身份;0:普通成员,1:创建者,2:管理员
    private int isTop; //置顶  0:否  1:是
    private int isShield; //屏蔽群消息 0:否  1:是
    private int status;//0:未通过 1:通过 2:拒绝
    
	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	public int getIsTop() {
		return isTop;
	}

	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}

	public int getIsShield() {
		return isShield;
	}

	public void setIsShield(int isShield) {
		this.isShield = isShield;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}

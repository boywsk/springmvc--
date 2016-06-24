package com.gome.im.api.model.response;

import java.io.Serializable;

public class RspGroupMember implements Serializable {
	private static final long serialVersionUID = 1L;

	private String groupId; // 群组
	private long uid; // 成员id
	private String nickName;// 成员昵称
	private long joinTime; // 加入时间

	private String mark;//对群组成员的备注名称

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

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

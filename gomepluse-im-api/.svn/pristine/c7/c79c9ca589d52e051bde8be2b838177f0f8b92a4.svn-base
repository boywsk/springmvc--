package com.gomeplus.im.api.request;

import java.io.Serializable;

/**
 * 请求群成员
 * @author liuzhenhuan
 * @date 2016年6月16日 上午10:47:31 
 * @version V1.0  
 */
public class ReqGroupMember implements Serializable{
	private long userId;
	private String groupId;
    private String nickName;
    private int identity;//身份;0:普通成员,1:创建者,2:管理员
    private int isTop; //置顶  0:否  1:是
    private int isShield; //屏蔽群消息 0:否  1:是
    private int status;//0:未通过 1:通过 2:拒绝
    
    private String mark;//备注
    
    public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public ReqGroupMember() {
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

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
    
}

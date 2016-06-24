package com.gomeplus.im.api.model;

import java.io.Serializable;

/**
 *
 * 群组成员
 * Created by wangshikai on 2016/2/19.
 */
public class GroupMember implements Serializable {
    private String groupId;
    private int groupIdHash;
    private  long userId;
    private String nickName;
    private int identity;//身份;0:普通成员,1:创建者,2:管理员
    private int isTop; //置顶  0:否  1:是
    private int isShield; //屏蔽群消息 0:否  1:是
    private int status;//0:未通过 1:通过 2:拒绝
    //private long initSeq;
    //private long readSeq;
    private long joinTime;
    private long updateTime;
    public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	private int isDel;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    

    public int getGroupIdHash() {
		return groupIdHash;
	}

	public void setGroupIdHash(int groupIdHash) {
		this.groupIdHash = groupIdHash;
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

//    public long getInitSeq() {
//        return initSeq;
//    }
//
//    public void setInitSeq(long initSeq) {
//        this.initSeq = initSeq;
//    }

//    public long getReadSeq() {
//        return readSeq;
//    }
//
//    public void setReadSeq(long readSeq) {
//        this.readSeq = readSeq;
//    }

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}

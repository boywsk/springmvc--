package com.gome.im.api.db.model;

import java.io.Serializable;

/**
 * Created by wangshikai on 2016/2/19.
 */
public class GroupMember implements Serializable {
    private long id;
    private  String groupId;
    private  long uid;
    private String nickName;
    private int stickies; //置顶  0:否  1:是
    private int isShield; //屏蔽群消息 0:否  1:是
    private int status;//0:未通过 1:通过 2:拒绝
    private long initSeq;
    private long readSeq;
    private long joinTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public int getStickies() {
        return stickies;
    }

    public void setStickies(int stickies) {
        this.stickies = stickies;
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

    public long getInitSeq() {
        return initSeq;
    }

    public void setInitSeq(long initSeq) {
        this.initSeq = initSeq;
    }

    public long getReadSeq() {
        return readSeq;
    }

    public void setReadSeq(long readSeq) {
        this.readSeq = readSeq;
    }

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }
}

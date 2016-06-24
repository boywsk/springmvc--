package com.gome.im.api.db.model;

import java.io.Serializable;

/**
 * Created by wangshikai on 2016/2/19.
 */
public class Friend implements Serializable{
    private long id;
    private long uid;
    private long friendUid;
    private String friendNickName;
    private int status; //0:未通过,1:通过,2:拒绝
    private String mark;
    private long friendGroupId;
    private long createTime;
    private long updateTime;

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

    public long getFriendUid() {
        return friendUid;
    }

    public void setFriendUid(long friendUid) {
        this.friendUid = friendUid;
    }

    public String getFriendNickName() {
        return friendNickName;
    }

    public void setFriendNickName(String friendNickName) {
        this.friendNickName = friendNickName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public long getFriendGroupId() {
        return friendGroupId;
    }

    public void setFriendGroupId(long friendGroupId) {
        this.friendGroupId = friendGroupId;
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
}

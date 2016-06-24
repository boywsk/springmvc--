package com.gome.im.api.model.response;

import java.io.Serializable;

/**
 * Created by wangshikai on 2016/3/9.
 */
public class RspFriend implements Serializable {
    private long friendUid;
    private String friendNickName;
    private String mark;
    private int status;
    private long updateTime;

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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}

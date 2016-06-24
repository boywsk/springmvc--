package com.gome.im.api.model;

/**
 * Created by wangshikai on 2016/2/23.
 */
public class Member {
    private long uid;
    private long imUserId;
    private String userName;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getImUserId() {
        return imUserId;
    }

    public void setImUserId(long imUserId) {
        this.imUserId = imUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

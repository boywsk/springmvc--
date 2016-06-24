package com.gome.im.api.db.model;

import java.io.Serializable;

/**
 * Created by wangshikai on 2016/2/19.
 */
public class GroupMemberMark implements Serializable {
    private long id;
    private String groupId;
    private long uid;
    private long markedUid;
    private String mark;
    private long createTime;

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

    public long getMarkedUid() {
        return markedUid;
    }

    public void setMarkedUid(long markedUid) {
        this.markedUid = markedUid;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}

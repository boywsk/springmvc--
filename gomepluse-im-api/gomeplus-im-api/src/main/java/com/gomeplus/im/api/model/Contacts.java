package com.gomeplus.im.api.model;

import java.io.Serializable;

/**
 *
 * 通讯录 联系人
 * Created by wangshikai on 2016/6/1.
 */
public class Contacts implements Serializable{
    private long id;
    private long userId;
    private String contactName;  //联系人姓名
    private String firstContactNameSpell;  //联系人姓名首字母
    private long phoneNumber;
    private long createTime;
    private long updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getFirstContactNameSpell() {
        return firstContactNameSpell;
    }

    public void setFirstContactNameSpell(String firstContactNameSpell) {
        this.firstContactNameSpell = firstContactNameSpell;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
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

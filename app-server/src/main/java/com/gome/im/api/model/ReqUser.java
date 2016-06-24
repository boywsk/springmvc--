package com.gome.im.api.model;

import java.io.Serializable;

/**
 * Created by wangshikai on 2016/1/12.
 */
public class ReqUser implements Serializable {
    private String uid;  //第三方应用的uid
//    private int deviceType; //app设备类型  BS 1:android  2:ios
//    private String deviceToken; //app设备token
//    private long createTime;
//    private long updateTime;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

//    public int getDeviceType() {
//        return deviceType;
//    }
//
//    public void setDeviceType(int deviceType) {
//        this.deviceType = deviceType;
//    }
//
//    public String getDeviceToken() {
//        return deviceToken;
//    }
//
//    public void setDeviceToken(String deviceToken) {
//        this.deviceToken = deviceToken;
//    }
//
//    public long getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(long createTime) {
//        this.createTime = createTime;
//    }
//
//    public long getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(long updateTime) {
//        this.updateTime = updateTime;
//    }
}

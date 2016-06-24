package com.gome.im.api.model.response;

import java.io.Serializable;

/**
 * Created by wangshikai on 2016/2/25.
 */
public class RespUserInfo implements Serializable{
    private String appId;
    private long uid;
    private String token;
    private long tokenExpires;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTokenExpires() {
        return tokenExpires;
    }

    public void setTokenExpires(long tokenExpires) {
        this.tokenExpires = tokenExpires;
    }
}

package cn.com.gome.api.model.response;

import java.io.Serializable;

/**
 * Created by wangshikai on 2016/2/25.
 */
public class RespUserInfo implements Serializable {
    private String appId;
    private String uid;
    private long imUserId;
    private String token;
    private long tokenExpires;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getImUserId() {
        return imUserId;
    }

    public void setImUserId(long imUserId) {
        this.imUserId = imUserId;
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

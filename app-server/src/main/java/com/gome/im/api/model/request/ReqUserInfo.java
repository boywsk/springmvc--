package com.gome.im.api.model.request;


import com.gome.im.api.model.ReqUser;

import java.io.Serializable;

/**
 * Created by wangshikai on 2016/1/13.
 */
public class ReqUserInfo implements Serializable {
    private int opt; //1：添加用户操作   2：更新用户操作
    private ReqUser reqUser;
    private int useUid;

    public int getOpt() {
        return opt;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public ReqUser getReqUser() {
        return reqUser;
    }

    public void setReqUser(ReqUser reqUser) {
        this.reqUser = reqUser;
    }

    public int getUseUid() {
        return useUid;
    }

    public void setUseUid(int useUid) {
        this.useUid = useUid;
    }
}

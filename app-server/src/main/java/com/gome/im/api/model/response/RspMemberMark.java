package com.gome.im.api.model.response;

import java.io.Serializable;

/**
 * Created by wangshikai on 2016/3/16.
 */
public class RspMemberMark implements Serializable {
    private long markedUid;// 备注群成员uid
    private String mark; //备注群成员名称

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
}

package com.gome.im.platform.model.request;

import java.io.Serializable;

/**
 *
 * 设置消息免打扰请求
 * Created by wangshikai on 2016/6/12.
 */
public class ReqBlockedMsg implements Serializable {
    private String groupId; //群组id

    private int isMsgBlocked; //是否消息免打扰

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getIsMsgBlocked() {
        return isMsgBlocked;
    }

    public void setIsMsgBlocked(int isMsgBlocked) {
        this.isMsgBlocked = isMsgBlocked;
    }
}

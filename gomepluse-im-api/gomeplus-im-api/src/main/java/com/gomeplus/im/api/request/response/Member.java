package com.gomeplus.im.api.request.response;

import java.io.Serializable;

/**
 * Created by wangshikai on 2016/2/23.
 */
public class Member implements Serializable{
    private long userId;
    private String userName; //在群里的昵称
    private int identity;//身份;0:普通成员,1:创建者,2:管理员

    public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

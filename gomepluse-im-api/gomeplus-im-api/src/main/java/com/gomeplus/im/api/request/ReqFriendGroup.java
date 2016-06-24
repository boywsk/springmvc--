package com.gomeplus.im.api.request;

import java.io.Serializable;

/**
 * 好友分组
 */
public class ReqFriendGroup  implements Serializable {
	private long id; //id 主键
    private String groupName;  //分组的名称
    
    public ReqFriendGroup() {
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
    
}

package com.gomeplus.im.api.request.response;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class RspFriendGroup implements Serializable{
	private long friendGroupId; //好友分组ID
	private String groupName;//好友分组名称
	
	public RspFriendGroup() {
	}

	public long getFriendGroupId() {
		return friendGroupId;
	}

	public void setFriendGroupId(long friendGroupId) {
		this.friendGroupId = friendGroupId;
	}

	public String getGroupName() {
		if (StringUtils.isBlank(groupName)) {
			return StringUtils.EMPTY;
		}
		
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}

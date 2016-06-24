package com.gomeplus.im.api.request.response;


import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class RspGroup implements Serializable {
	private String groupId; // 群组id
	private long userId;//创建者userId
	private int type; // 群组类型;1:单聊,2:群聊,3:系统消息,4:小秘书
	private String groupName; // 群组名称
	private String desc; // 群组描述
	private String avatar; // 群组头像url
	private String qRCode; // 群组二维码url
	private int capacity; // 群组容量
	private int isAudit; // 申请加入是否需要审核;0:否,1:是
	private int isTop; // 置顶;0:否,1:是
	private int isShield;// 是否屏蔽群消息;0:否,1:是
	private List<RspGroupMember> members;//群成员

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getDesc() {
		if (StringUtils.isBlank(desc)) {
			return StringUtils.EMPTY;
		}
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAvatar() {
		if (StringUtils.isBlank(avatar)) {
			return StringUtils.EMPTY;
		}
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getqRCode() {
		if (StringUtils.isBlank(qRCode)) {
			return StringUtils.EMPTY;
		}
		return qRCode;
	}

	public void setqRCode(String qRCode) {
		this.qRCode = qRCode;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(int isAudit) {
		this.isAudit = isAudit;
	}

	public int getIsTop() {
		return isTop;
	}

	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}

	public int getIsShield() {
		return isShield;
	}

	public void setIsShield(int isShield) {
		this.isShield = isShield;
	}

	public List<RspGroupMember> getMembers() {
		return members;
	}

	public void setMembers(List<RspGroupMember> members) {
		this.members = members;
	}

}

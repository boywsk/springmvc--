package com.gomeplus.im.manage.mongodb.model;

import java.io.Serializable;

/**
 * 群成员
 */
public class GroupMember implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String groupId; // 群组
	private long uid; // 成员id
	// private String nickName;//成员昵称
	// private String nickNameSpell;// 昵称全拼
	// private String nickNameHeadChar;// 昵称首字母
	// private String selfMark;//自己在群中的备注名称
	// private String selfMarkSpell;//自己在群中的备注名称全拼
	// private String selfMarkHeadChar;//自己在群中的备注名称字母
	 private int identity;//身份;0:普通成员,1:创建者,2:管理员
	// private int stickies; // 置顶;0:否,1:是
	// private int isShield;// 是否屏蔽群消息;0:否,1:是
	private long initSeq;// 加入群时，当前群消息seq
	private long readSeq;// 读取到的群消息最大seq
	// private int status; // 状态;0:未通过,1:通过,2:拒绝
	// private int joinType;//加入类型，1:邀请加入群，2:扫二维码加入群，3:通过群名/号加入群
	private long joinTime; // 加入时间
	// private List<GroupMemberMark> membersMark;//群组成员备注
	private long updateTime;// 最后一次修改时间

	private String formatJoinTime;
	private String formatUpdateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getInitSeq() {
		return initSeq;
	}

	public void setInitSeq(long initSeq) {
		this.initSeq = initSeq;
	}

	public long getReadSeq() {
		return readSeq;
	}

	public void setReadSeq(long readSeq) {
		this.readSeq = readSeq;
	}

	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getFormatJoinTime() {
		return formatJoinTime;
	}

	public void setFormatJoinTime(String formatJoinTime) {
		this.formatJoinTime = formatJoinTime;
	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	public String getFormatUpdateTime() {
		return formatUpdateTime;
	}

	public void setFormatUpdateTime(String formatUpdateTime) {
		this.formatUpdateTime = formatUpdateTime;
	}
	
}

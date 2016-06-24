package com.gome.im.platform.model;

import java.io.Serializable;

/**
 * 群成员
 */
public class GroupMember implements Serializable ,Cloneable{
	private static final long serialVersionUID = 1L;

	private String id;
	private String groupId; // 群组
	private long uid; // 成员id
	private String nickName;//成员昵称
//	private String nickNameSpell;// 昵称全拼
//	private String nickNameHeadChar;// 昵称首字母
//	private String selfMark;//自己在群中的备注名称
//	private String selfMarkSpell;//自己在群中的备注名称全拼
//	private String selfMarkHeadChar;//自己在群中的备注名称字母
	private int identity;//身份;0:普通成员,1:创建者,2:管理员
//	private int stickies; // 置顶;0:否,1:是
//	private int isShield;// 是否屏蔽群消息;0:否,1:是
	private long initSeq;// 加入群时，当前群消息seq
	private long readSeq;// 读取到的群消息最大seq
//	private int status; // 状态;0:未通过,1:通过,2:拒绝
//	private int joinType;//加入类型，1:邀请加入群，2:扫二维码加入群，3:通过群名/号加入群
	private long joinTime; // 加入时间
//	private List<GroupMemberMark> membersMark;//群组成员备注
	private long updateTime;//最后一次修改时间

	private int isMsgBlocked; //消息免打扰 1：设置   0：取消设置

	public GroupMember clone(){
		GroupMember object = null;
		try{
			object = (GroupMember)super.clone();
		}catch (Exception e){
		}
		return object;
	}

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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

//	public String getNickNameSpell() {
//		return nickNameSpell;
//	}
//
//	public void setNickNameSpell(String nickNameSpell) {
//		this.nickNameSpell = nickNameSpell;
//	}
//
//	public String getNickNameHeadChar() {
//		return nickNameHeadChar;
//	}
//
//	public void setNickNameHeadChar(String nickNameHeadChar) {
//		this.nickNameHeadChar = nickNameHeadChar;
//	}
//
//	public String getSelfMark() {
//		return selfMark;
//	}
//
//	public void setSelfMark(String selfMark) {
//		this.selfMark = selfMark;
//	}
//
//	public String getSelfMarkSpell() {
//		return selfMarkSpell;
//	}
//
//	public void setSelfMarkSpell(String selfMarkSpell) {
//		this.selfMarkSpell = selfMarkSpell;
//	}
//
//	public String getSelfMarkHeadChar() {
//		return selfMarkHeadChar;
//	}
//
//	public void setSelfMarkHeadChar(String selfMarkHeadChar) {
//		this.selfMarkHeadChar = selfMarkHeadChar;
//	}
//
	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}
//
//	public int getStickies() {
//		return stickies;
//	}
//
//	public void setStickies(int stickies) {
//		this.stickies = stickies;
//	}
//
//	public int getIsShield() {
//		return isShield;
//	}
//
//	public void setIsShield(int isShield) {
//		this.isShield = isShield;
//	}
//
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
//
//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}
//
//	public int getJoinType() {
//		return joinType;
//	}
//
//	public void setJoinType(int joinType) {
//		this.joinType = joinType;
//	}

	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}
	
//	public List<GroupMemberMark> getMembersMark() {
//		return membersMark;
//	}
//
//	public void setMembersMark(List<GroupMemberMark> membersMark) {
//		this.membersMark = membersMark;
//	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public int getIsMsgBlocked() {
		return isMsgBlocked;
	}

	public void setIsMsgBlocked(int isMsgBlocked) {
		this.isMsgBlocked = isMsgBlocked;
	}
}

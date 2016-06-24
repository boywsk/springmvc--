package cn.com.gome.api.model.response;

import java.io.Serializable;

public class RspGroupMember implements Serializable {
	private static final long serialVersionUID = 1L;

	private String groupId; // 群组
	private long uid; // 成员id
	private String nickName;// 成员昵称
	private String nickNameSpell;// 昵称全拼
	private String nickNameHeadChar;// 昵称首字母
	private String selfMark;//自己在群中的备注名称
	private String selfMarkSpell;//自己在群中的备注名称全拼
	private String selfMarkHeadChar;//自己在群中的备注名称字母
	private String mark; // 备注名
	private String markSpell;// 标注和备注全拼
	private String markHeadChar;// 标注和备注首字母
	private long joinTime; // 加入时间
	private long updateTime;// 最后一次修改时间

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

	public String getNickNameSpell() {
		return nickNameSpell;
	}

	public void setNickNameSpell(String nickNameSpell) {
		this.nickNameSpell = nickNameSpell;
	}

	public String getNickNameHeadChar() {
		return nickNameHeadChar;
	}

	public void setNickNameHeadChar(String nickNameHeadChar) {
		this.nickNameHeadChar = nickNameHeadChar;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getSelfMark() {
		return selfMark;
	}

	public void setSelfMark(String selfMark) {
		this.selfMark = selfMark;
	}

	public String getSelfMarkSpell() {
		return selfMarkSpell;
	}

	public void setSelfMarkSpell(String selfMarkSpell) {
		this.selfMarkSpell = selfMarkSpell;
	}

	public String getSelfMarkHeadChar() {
		return selfMarkHeadChar;
	}

	public void setSelfMarkHeadChar(String selfMarkHeadChar) {
		this.selfMarkHeadChar = selfMarkHeadChar;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getMarkSpell() {
		return markSpell;
	}

	public void setMarkSpell(String markSpell) {
		this.markSpell = markSpell;
	}

	public String getMarkHeadChar() {
		return markHeadChar;
	}

	public void setMarkHeadChar(String markHeadChar) {
		this.markHeadChar = markHeadChar;
	}

	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

}

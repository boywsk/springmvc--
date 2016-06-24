package cn.com.gome.api.model;

import java.io.Serializable;

/**
 * 群组成员标注;自己可以给自己在群中标注
 */
public class GroupMemberMark implements Serializable {

	private static final long serialVersionUID = 1L;

	//private String id;
	//private String groupId; // 群组id
	//private long uid; // 备注人id(主动方)
	private long markedUid; // 被备注人id
	private String mark; // 备注名
	private String markSpell;// 标注和备注全拼
	private String markHeadChar;// 标注和备注首字母
//	private long createTime; // 备注时间

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getGroupId() {
//		return groupId;
//	}
//
//	public void setGroupId(String groupId) {
//		this.groupId = groupId;
//	}
//
//	public long getUid() {
//		return uid;
//	}
//
//	public void setUid(long uid) {
//		this.uid = uid;
//	}

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

//	public long getCreateTime() {
//		return createTime;
//	}
//
//	public void setCreateTime(long createTime) {
//		this.createTime = createTime;
//	}

}

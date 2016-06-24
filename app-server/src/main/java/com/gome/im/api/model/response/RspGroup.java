package com.gome.im.api.model.response;


import java.io.Serializable;
import java.util.List;

public class RspGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	private String groupId; // 群组id
	private long uid;//创建者id
	private int type; // 群组类型;1:单聊,2:群聊,3:系统消息,4:小秘书
	private String groupName; // 群组名称
	private String desc; // 群组描述
	private String avatar; // 群组头像url
	private String qRCode; // 群组二维码url
	private int capacity; // 群组容量
	private int isAudit; // 申请加入是否需要审核;0:否,1:是
	private long seq; // 递增id
	private long initSeq;// 初始化seqId
	private long readSeq;// 读取至seqId
	private int stickies; // 置顶;0:否,1:是
	private int isShield;// 是否屏蔽群消息;0:否,1:是
	private Object lastMsg; // 最后一条消息内容
	private List<RspGroupMember> members;//群成员

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getqRCode() {
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

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
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

	public int getStickies() {
		return stickies;
	}

	public void setStickies(int stickies) {
		this.stickies = stickies;
	}

	public int getIsShield() {
		return isShield;
	}

	public void setIsShield(int isShield) {
		this.isShield = isShield;
	}

	public Object getLastMsg() {
		return lastMsg;
	}

	public void setLastMsg(Object lastMsg) {
		this.lastMsg = lastMsg;
	}

	public List<RspGroupMember> getMembers() {
		return members;
	}

	public void setMembers(List<RspGroupMember> members) {
		this.members = members;
	}

}

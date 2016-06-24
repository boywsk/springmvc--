package cn.com.gome.api.model;

import java.io.Serializable;

/**
 * 群组
 */
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	private String groupId; // 群组id
	private long uid; // 群组创建者id
	private int type; // 群组类型;1:单聊,2:群聊,3:系统消息,4:小秘书
	private String name; // 群组名称
//	private String desc; // 群组描述
//	private String avatar; // 群组头像url
//	private String qRCode; // 群组二维码url
	private int capacity; // 群组容量
//	private int isAudit; // 申请加入是否需要审核;0:否,1:是
//	private int updateTyte; // 群组变化类型;对不同类型进行数字定义,通过位或的方式表示类型变化
	private long seq; // 递增id
//	private Object lastMsg; // 最后一条消息内容
//	private String extra; // 最后一条消息扩展信息
	private int isDele; // 是否被删除;0:否,1:是
	private long createTime; // 创建时间
	private long updateTime; // 最后一次修改时间

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
//
//	public String getDesc() {
//		return desc;
//	}
//
//	public void setDesc(String desc) {
//		this.desc = desc;
//	}
//
//	public String getAvatar() {
//		return avatar;
//	}
//
//	public void setAvatar(String avatar) {
//		this.avatar = avatar;
//	}
//
//	public String getqRCode() {
//		return qRCode;
//	}
//
//	public void setqRCode(String qRCode) {
//		this.qRCode = qRCode;
//	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

//	public int getIsAudit() {
//		return isAudit;
//	}
//
//	public void setIsAudit(int isAudit) {
//		this.isAudit = isAudit;
//	}
//
//	public int getUpdateTyte() {
//		return updateTyte;
//	}
//
//	public void setUpdateTyte(int updateTyte) {
//		this.updateTyte = updateTyte;
//	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}


//	public Object getLastMsg() {
//		return lastMsg;
//	}
//
//	public void setLastMsg(Object lastMsg) {
//		this.lastMsg = lastMsg;
//	}
//
//
//	public String getExtra() {
//		return extra;
//	}
//
//	public void setExtra(String extra) {
//		this.extra = extra;
//	}

	public int getIsDele() {
		return isDele;
	}

	public void setIsDele(int isDele) {
		this.isDele = isDele;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
}

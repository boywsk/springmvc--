package com.gomeplus.im.manage.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 加入群消息通知
 */
public class JoinGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	protected short cmd;//命令字
	private int joinType; // 加入类型，1:邀请加入群，2:扫二维码加入群，3:通过群名/号加入群
	private long senderUid; // 主动申请/邀请用户ID
	private String senderName; // 主动申请/邀请用户昵称
	private List<Long> invitedUids; // 被邀请用户ID列表
	private List<String> invitedNames; // 被邀请用户昵称
	private long receiverId;// 消息接收者id
	private String groupId; // 群组ID
	private String groupName; // 群组名称
	private String groupImage; // 群组图片
	private String messag;// 消息内容
	private String content; // 内容
	private long sendTime; // 时间(毫秒)
	private long seq;// 消息自增id
	private String extra; // 扩展
	
	public short getCmd() {
		return cmd;
	}

	public void setCmd(short cmd) {
		this.cmd = cmd;
	}
	
	public int getJoinType() {
		return joinType;
	}

	public void setJoinType(int joinType) {
		this.joinType = joinType;
	}

	public long getSenderUid() {
		return senderUid;
	}

	public void setSenderUid(long senderUid) {
		this.senderUid = senderUid;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public List<Long> getInvitedUids() {
		return invitedUids;
	}

	public void setInvitedUids(List<Long> invitedUids) {
		this.invitedUids = invitedUids;
	}

	public List<String> getInvitedNames() {
		return invitedNames;
	}

	public void setInvitedNames(List<String> invitedNames) {
		this.invitedNames = invitedNames;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupImage() {
		return groupImage;
	}

	public void setGroupImage(String groupImage) {
		this.groupImage = groupImage;
	}

	public String getMessag() {
		return messag;
	}

	public void setMessag(String messag) {
		this.messag = messag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
}

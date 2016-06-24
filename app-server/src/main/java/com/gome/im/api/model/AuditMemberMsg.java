package com.gome.im.api.model;


import com.gome.im.api.global.Command;

import java.io.Serializable;

/**
 * 审核加入的群成员通知消息
 */
public class AuditMemberMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	private short cmd = Command.CMD_AUDIT_MEMBER; // 命令字
	private String msgId; // 消息id
	private int joinType; // 加入方式，1:邀请加入群，2:扫二维码加入群，3:通过群名/号加入群
	private long senderUid; // 主动申请/邀请用户ID
	private String senderName; // 主动申请/邀请用户昵称
	private long auditedUid; // 被邀请用户ID
	private String auditedName; // 被邀请用户昵称
	private String groupId; // 群组ID
	private String groupName; // 群组名称
	private String groupImage; // 群组图片
	private String message; // 消息内容
	private String content; // 文字描述
	private long sendTime; // 时间(毫秒)
	private String extra; // 扩展

	public AuditMemberMsg() {
		this.cmd = Command.CMD_AUDIT_MEMBER;
	}
	
	public short getCmd() {
		return cmd;
	}

	public void setCmd(short cmd) {
		this.cmd = cmd;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
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

	public long getAuditedUid() {
		return auditedUid;
	}

	public void setAuditedUid(long auditedUid) {
		this.auditedUid = auditedUid;
	}

	public String getAuditedName() {
		return auditedName;
	}

	public void setAuditedName(String auditedName) {
		this.auditedName = auditedName;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
}

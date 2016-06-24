package com.gome.im.api.model;


import com.gome.im.api.global.Command;

import java.io.Serializable;

/**
 * 审核通过通知消息
 */
public class AgreeMemberMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	private short cmd = Command.CMD_AGREE_MEMBER; // 命令字
	private String msgId; // 消息id
	private long senderUid; // 审核人id
	private String senderName; // 审核人昵称
	private String groupId; // 群组ID
	private String groupName; // 群组名称
	private String groupImage; // 群组图片
	private long auditerUid; // 审核者用户ID
	private String auditerName; // 审核者昵称
	private int agreeType; // 审批结果；0:为拒绝，1:为同意
	private String messag;// 消息内容
	private String content; // 文字描述
	private String extra; // 扩展

	public AgreeMemberMsg() {
		this.cmd = Command.CMD_AGREE_MEMBER;
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

	public long getAuditerUid() {
		return auditerUid;
	}

	public void setAuditerUid(long auditerUid) {
		this.auditerUid = auditerUid;
	}

	public String getAuditerName() {
		return auditerName;
	}

	public void setAuditerName(String auditerName) {
		this.auditerName = auditerName;
	}

	public int getAgreeType() {
		return agreeType;
	}

	public void setAgreeType(int agreeType) {
		this.agreeType = agreeType;
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

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

}

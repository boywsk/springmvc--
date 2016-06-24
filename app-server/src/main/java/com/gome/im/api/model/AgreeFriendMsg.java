package com.gome.im.api.model;


import com.gome.im.api.global.Command;

import java.io.Serializable;

/**
 * 是否同意对方加为好友
 */
public class AgreeFriendMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	private short cmd = Command.CMD_AGREE_FRIEND; //命令字
	private String msgId; // 消息id
	private long senderId; // 审批用户ID
	private String senderName; // 审批用户昵称
	private long receiverId; // 申请用户ID
	private int agreeType; // 审批结果；0:为拒绝，1:为同意
	private String messag;//消息内容
	private String content; // 文字描述
	private long sendTime; // 审批时间(毫秒)
	private String extra; // 扩展

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

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
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

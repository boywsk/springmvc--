package com.gome.im.api.model;


import com.gome.im.api.global.Command;

import java.io.Serializable;


/**
 * 添加好友通知信息
 */
public class AddFriendMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private short cmd = Command.CMD_ADD_FRIEND; //命令字
	private String msgId; // 消息id
	private long senderId; // 申请用户ID
	private long receiverId; // 审批用户ID
	private String senderName; // 申请用户昵称
	private String senderAvatar; // 申请用户头像
	private String messag;//消息内容
	private String content; // 文字描述
	private long sendTime; // 申请时间(毫秒)
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

	public String getSenderAvatar() {
		return senderAvatar;
	}

	public void setSenderAvatar(String senderAvatar) {
		this.senderAvatar = senderAvatar;
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

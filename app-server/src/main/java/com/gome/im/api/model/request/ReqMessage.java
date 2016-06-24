package com.gome.im.api.model.request;

import java.io.Serializable;

/**
 * 发送消息 Request model
 */
public class ReqMessage extends BaseRequest implements Serializable {
	private String appId;
	private long senderId;
	private String senderName;
	private int groupType;
	private long receiveId;
	private String groupId;
	private String groupName;
	private String msgBody;
	private String extra;
	private boolean isPersist;
	private boolean senderReceiveMsg;
	private int msgType;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public int getGroupType() {
		return groupType;
	}

	public void setGroupType(int groupType) {
		this.groupType = groupType;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public long getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(long receiveId) {
		this.receiveId = receiveId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
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

	public boolean getIsPersist() {
		return isPersist;
	}

	public void setIsPersist(boolean isPersist) {
		this.isPersist = isPersist;
	}

	public boolean getSenderReceiveMsg() {
		return senderReceiveMsg;
	}

	public void setSenderReceiveMsg(boolean senderReceiveMsg) {
		this.senderReceiveMsg = senderReceiveMsg;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
}

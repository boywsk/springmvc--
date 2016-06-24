package cn.com.gome.api.model.request;

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
	private boolean senderReceiveMsg;//单聊消息的发送者自己是否要收到该消息
	private String groupId;
	private String groupName;
	private String msgBody;
	private String extra;
	private boolean isPersist;
	private int msgType;

	private int sponserId; //发起者id
	private int senderType; //发送者类型；0:普通、1:系统/小秘书等；2:咨询者

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

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
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

	public int getSponserId() {
		return sponserId;
	}

	public void setSponserId(int sponserId) {
		this.sponserId = sponserId;
	}

	public int getSenderType() {
		return senderType;
	}

	public void setSenderType(int senderType) {
		this.senderType = senderType;
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

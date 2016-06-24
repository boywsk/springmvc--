package com.gome.im.api.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * 聊天消息
 */
public class GroupMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	private String msgId;	//消息id
	private int msgType;	//消息类型；1:文本、2:语音、3:图片、4:视频、5:位置、6:附件、7:名片、8:系统消息、
							//9:分享/转发(通过url)、10:商品、11:店铺、12:群组操作、99:消息透传
	private String msgBody;	// 消息体
	private long senderId;	// 发送者id
	private String senderName;	// 发送者名称
	private String senderRemark;// 消息发送者在该群中的昵称
	private int groupType;	// 群组类型，1:单聊，2:群聊
	private String groupId;	//群组id   群组类型为1单聊时：senderId + "_" + recieverId  （id值小的在前，大的在后）
	private String groupName;	//群组名称
	//private String groupImg;//群组图片
	private String msgUrl;//消息url
	private List<Attachment> msgAttch; //附件列表
	private String extra;	//扩展信息

	private long sendTime;//发送时间(由服务器端生成)
	private short cmd; //命令字(由服务器端生成)

	public short getCmd() {
		return cmd;
	}

	public void setCmd(short cmd) {
		this.cmd = cmd;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getMsgUrl() {
		return msgUrl;
	}

	public void setMsgUrl(String msgUrl) {
		this.msgUrl = msgUrl;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderRemark() {
		return senderRemark;
	}

	public void setSenderRemark(String senderRemark) {
		this.senderRemark = senderRemark;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getGroupType() {
		return groupType;
	}

	public void setGroupType(int groupType) {
		this.groupType = groupType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

//	public String getGroupImg() {
//		return groupImg;
//	}
//
//	public void setGroupImg(String groupImg) {
//		this.groupImg = groupImg;
//	}

	public List<Attachment> getMsgAttch() {
		return msgAttch;
	}

	public void setMsgAttch(List<Attachment> msgAttch) {
		this.msgAttch = msgAttch;
	}


//	public OperateGroup getOptGroup() {
//		return optGroup;
//	}
//
//	public void setOptGroup(OperateGroup optGroup) {
//		this.optGroup = optGroup;
//	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	public String toString() {
		return JSON.toJSONString(this);
	}
}

package com.gome.im.api.model;

import java.io.Serializable;

/**
 * 修改群通知信息
 */
public class EditGroupMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	private long senderUid;// 用户ID
	private String senderName;// 户昵称
	private int capacity;// 群组人数
	private String message; // 消息内容
	private String content; // 文字描述
	private String extra; // 扩展

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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
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

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

}

package com.gome.im.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * 退/踢出群通知信息
 */
public class QuitGroupMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	private int quitType;// 加入类型，1:为被踢，2:为主动退群
	private long receiverId;// 消息接收者id
	private long senderUid; // 主动退/踢出群用户ID
	private String senderName; // 主动退/踢出群用户昵称
	private List<Long> kickedUids; // 被退/踢出群用户ID列表
	private List<String> kickedNames; // 被退/踢出群用户昵称
	private String message; // 消息内容
	private String content; // 文字描述
	private String extra; // 扩展

	public int getQuitType() {
		return quitType;
	}

	public void setQuitType(int quitType) {
		this.quitType = quitType;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
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

	public List<Long> getKickedUids() {
		return kickedUids;
	}

	public void setKickedUids(List<Long> kickedUids) {
		this.kickedUids = kickedUids;
	}

	public List<String> getKickedNames() {
		return kickedNames;
	}

	public void setKickedNames(List<String> kickedNames) {
		this.kickedNames = kickedNames;
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

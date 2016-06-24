package cn.com.gome.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * 解散群通知信息
 */
public class DisbandGroupMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	private long senderId;
	private String senderName;
	private String messag;
	private String content; // 内容
	private String extra; // 扩展
	private List<Long> memberIds;

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

	public List<Long> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}
}

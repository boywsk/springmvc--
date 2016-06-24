package cn.com.gome.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * 加入群通知消息
 */
public class JoinGroupMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	private int joinType; // 加入类型，1:邀请加入群，2:扫二维码加入群，3:通过群名/号加入群
	private long senderUid; // 主动申请/邀请用户ID
	private String senderName; // 主动申请/邀请用户昵称
	private List<Long> invitedUids; // 被邀请用户ID列表
	private List<String> invitedNames; // 被邀请用户昵称
	private long receiverId;// 消息接收者id
	private String messag;// 消息内容
	private String content; // 内容
	private String extra; // 扩展

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

	public List<Long> getInvitedUids() {
		return invitedUids;
	}

	public void setInvitedUids(List<Long> invitedUids) {
		this.invitedUids = invitedUids;
	}

	public List<String> getInvitedNames() {
		return invitedNames;
	}

	public void setInvitedNames(List<String> invitedNames) {
		this.invitedNames = invitedNames;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
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

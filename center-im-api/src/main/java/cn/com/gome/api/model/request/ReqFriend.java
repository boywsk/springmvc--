package cn.com.gome.api.model.request;

import java.io.Serializable;

/**
 * 好友业务 Request model
 */
public class ReqFriend extends BaseRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private long uid; // 发起者用户id，申请用户的id
	private String nickName;// 发起者用户昵称
	private long friendUid; // 好友用户id，被申请用户的id
	private String content;// 内容
	//private String mark; // 好友标注和备注，uid 对 friendUid 的备注
	//private int status;//状态;0:未通过,1:通过,2:拒绝
	//private long time;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getFriendUid() {
		return friendUid;
	}

	public void setFriendUid(long friendUid) {
		this.friendUid = friendUid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

//	public String getMark() {
//		return mark;
//	}
//
//	public void setMark(String mark) {
//		this.mark = mark;
//	}
//
//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}
//
//	public long getTime() {
//		return time;
//	}
//
//	public void setTime(long time) {
//		this.time = time;
//	}
}

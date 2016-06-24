package com.gomeplus.im.api.message;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gomeplus.im.api.global.Command;
import com.gomeplus.im.api.global.Constant;
import com.gomeplus.im.api.utils.MQSender;

public class DelFriendMsg implements Serializable,Runnable{
	/**
	 * 删除好友
	 */
	private static final long serialVersionUID = 1L;
	private long fromUid; // 申请用户ID
	private long toUid; // 审批用户ID
	private String content; // 内容
	private long optTime; // 操作时间(毫秒)
	private String extra; // 扩展
	private transient int appId;

	public int getAppId() {
		return appId;
	}
	
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public long getFromUid() {
		return fromUid;
	}


	public void setFromUid(long fromUid) {
		this.fromUid = fromUid;
	}

	public long getToUid() {
		return toUid;
	}

	public void setToUid(long toUid) {
		this.toUid = toUid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getOptTime() {
		return optTime;
	}

	public void setOptTime(long optTime) {
		this.optTime = optTime;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	@Override
	public void run() {
		try {
			NoticeMsg noticeMsg = new NoticeMsg();
			noticeMsg.setMsgId(com.gomeplus.im.api.utils.StringUtils.getUuid());
			noticeMsg.setNoticeType(Constant.MSG_TASK_TYPE.DEL_FRIEND.value);
			noticeMsg.setAppId(appId);
			noticeMsg.setCmd(Command.CMD_OPERATION);
			noticeMsg.setDelFriend(this);
			String msgJson = JSON.toJSONString(noticeMsg);
			MQSender.getInstance().sendMsg(msgJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

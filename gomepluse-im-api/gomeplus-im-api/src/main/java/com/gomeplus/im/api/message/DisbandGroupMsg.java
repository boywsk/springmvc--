package com.gomeplus.im.api.message;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gomeplus.im.api.global.Command;
import com.gomeplus.im.api.global.Constant;
import com.gomeplus.im.api.utils.MQSender;

public class DisbandGroupMsg implements Serializable,Runnable {
	/**
	 * 解散群
	 */
	private static final long serialVersionUID = 1L;
	private long fromUid; // 用户ID
	private String fromName; // 户昵称
	private String content; // 内容
	private String groupId; // 群组id
	private long optTime; // 操作时间(毫秒)
	private String extra; // 扩展
	private transient int appId;
	
	public long getFromUid() {
		return fromUid;
	}

	public void setFromUid(long fromUid) {
		this.fromUid = fromUid;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}
	
	@Override
	public void run() {
		try {
			NoticeMsg noticeMsg = new NoticeMsg();
			noticeMsg.setMsgId(com.gomeplus.im.api.utils.StringUtils.getUuid());
			noticeMsg.setNoticeType(Constant.MSG_TASK_TYPE.DISBAND_GROUP.value);
			noticeMsg.setAppId(appId);
			noticeMsg.setCmd(Command.CMD_OPERATION);
			noticeMsg.setDisbandGroup(this);
			String msgJson = JSON.toJSONString(noticeMsg);
			MQSender.getInstance().sendMsg(msgJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

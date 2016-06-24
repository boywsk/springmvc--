package com.gomeplus.im.api.message;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gomeplus.im.api.global.Command;
import com.gomeplus.im.api.global.Constant;
import com.gomeplus.im.api.utils.MQSender;

public class NoticeManagerMsg implements Serializable ,Runnable{
	/**
	 * 通知管理员审核加入成员
	 */
	private static final long serialVersionUID = 1L;
	private long applicantId; // 申请用户ID
	private String applicantName; // 申请用户昵称
	private String content; // 内容
	private String groupId; // 群组id
	private long optTime; // 操作时间(毫秒)
	private String extra; // 扩展
	private transient int appId;
	
	public long getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(long applicantId) {
		this.applicantId = applicantId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public void run() {
		try {
			NoticeMsg noticeMsg = new NoticeMsg();
			noticeMsg.setMsgId(com.gomeplus.im.api.utils.StringUtils.getUuid());
			noticeMsg.setNoticeType(Constant.MSG_TASK_TYPE.AUDIT_GORUP_NOTIFY.value);
			noticeMsg.setAppId(appId);
			noticeMsg.setCmd(Command.CMD_OPERATION);
			noticeMsg.setNoticeManager(this);
			String msgJson = JSON.toJSONString(noticeMsg);
			MQSender.getInstance().sendMsg(msgJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package com.gomeplus.im.api.message;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gomeplus.im.api.global.Command;
import com.gomeplus.im.api.global.Constant;
import com.gomeplus.im.api.utils.MQSender;

public class AgreeFriendMsg implements Serializable,Runnable {
	/**
	 * 是否同意对方加为好友
	 */
	private static final long serialVersionUID = 1L;
	private long fromUid; // 审批用户ID
	private String fromName; // 审批用户昵称
	private long toUid; // 申请用户ID
	private int agreeType; // 审批结果；0:为拒绝，1:为同意
	private String content; // 消息内容
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

	public long getToUid() {
		return toUid;
	}

	public void setToUid(long toUid) {
		this.toUid = toUid;
	}

	public int getAgreeType() {
		return agreeType;
	}

	public void setAgreeType(int agreeType) {
		this.agreeType = agreeType;
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
			noticeMsg.setNoticeType(Constant.MSG_TASK_TYPE.AUDIT_FRIEND_RESULT.value);
			noticeMsg.setAppId(appId);
			noticeMsg.setCmd(Command.CMD_OPERATION);
			noticeMsg.setAgreeFriend(this);
			String msgJson = JSON.toJSONString(noticeMsg);
			MQSender.getInstance().sendMsg(msgJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

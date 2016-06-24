package com.gomeplus.im.api.message;

import java.io.Serializable;

public class NoticeMsg implements Serializable {
	/**
	 * 功能消息 CDM:0x0100
	 */
	private static final long serialVersionUID = 1L;
	private int appId;
	private String msgId; // 消息id
	private short cmd;
	private int noticeType;// 通知； 100:申请添加好友、101:删除好友、102:同意/拒绝好友申请，
							// 200:申请加入群、201:通知管理员审核加入成员、202:邀请加入群
							// 、203:通知被邀请加入群、204:退/踢出群、205:修改群信息、206:解散群
	private AddFriendMsg addFriend; // 添加好友
	private DelFriendMsg delFriend; // 删除好友
	private AgreeFriendMsg agreeFriend;// 是否同意对方加为好友
	private ApplyJoinGroupMsg applyJoinGroup; // 申请加入群
	private NoticeManagerMsg noticeManager; // 通知管理员审核加入成员
	private InvitedJoinGroupMsg invitedJoinGroup; // 邀请加入群
	private NoticeApplicantMsg noticeApplicant; // 通知被邀请加入群
	private QuitGroupMsg quitGroup; // 退/踢出群
	private EditGroupMsg editGroup; // 修改群信息
	private DisbandGroupMsg disbandGroup; // 解散群

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public int getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
	}

	public AddFriendMsg getAddFriend() {
		return addFriend;
	}

	public void setAddFriend(AddFriendMsg addFriend) {
		this.addFriend = addFriend;
	}

	public DelFriendMsg getDelFriend() {
		return delFriend;
	}

	public void setDelFriend(DelFriendMsg delFriend) {
		this.delFriend = delFriend;
	}

	public AgreeFriendMsg getAgreeFriend() {
		return agreeFriend;
	}

	public void setAgreeFriend(AgreeFriendMsg agreeFriend) {
		this.agreeFriend = agreeFriend;
	}

	public ApplyJoinGroupMsg getApplyJoinGroup() {
		return applyJoinGroup;
	}

	public void setApplyJoinGroup(ApplyJoinGroupMsg applyJoinGroup) {
		this.applyJoinGroup = applyJoinGroup;
	}

	public NoticeManagerMsg getNoticeManager() {
		return noticeManager;
	}

	public void setNoticeManager(NoticeManagerMsg noticeManager) {
		this.noticeManager = noticeManager;
	}

	public InvitedJoinGroupMsg getInvitedJoinGroup() {
		return invitedJoinGroup;
	}

	public void setInvitedJoinGroup(InvitedJoinGroupMsg invitedJoinGroup) {
		this.invitedJoinGroup = invitedJoinGroup;
	}

	public NoticeApplicantMsg getNoticeApplicant() {
		return noticeApplicant;
	}

	public void setNoticeApplicant(NoticeApplicantMsg noticeApplicant) {
		this.noticeApplicant = noticeApplicant;
	}

	public QuitGroupMsg getQuitGroup() {
		return quitGroup;
	}

	public void setQuitGroup(QuitGroupMsg quitGroup) {
		this.quitGroup = quitGroup;
	}

	public EditGroupMsg getEditGroup() {
		return editGroup;
	}

	public void setEditGroup(EditGroupMsg editGroup) {
		this.editGroup = editGroup;
	}

	public DisbandGroupMsg getDisbandGroup() {
		return disbandGroup;
	}

	public void setDisbandGroup(DisbandGroupMsg disbandGroup) {
		this.disbandGroup = disbandGroup;
	}

	public short getCmd() {
		return cmd;
	}

	public void setCmd(short cmd) {
		this.cmd = cmd;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}	

}

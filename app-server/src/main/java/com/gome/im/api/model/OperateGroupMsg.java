package com.gome.im.api.model;


import com.gome.im.api.global.Command;

import java.io.Serializable;

/**
 * 群相关操作
 */
public class OperateGroupMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	private short cmd = Command.CMD_OPERATE_GROUP; // 命令字
	private int optType;// 操作类型；1:加入群，2:退群，3:修改群，4:解散群
	private int receiverType;//消息接收者类型；1:全部成员，2:管理员
	private String groupId; // 群组ID
	private String groupName; // 群组名称
	private String groupImage; // 群组图片
	private JoinGroupMsg joinGroup;// 加入群
	private QuitGroupMsg quitGroup;// 退群
	private EditGroupMsg editGroup;// 修改群
	private DisbandGroupMsg disbandGroup; // 解散群

	public OperateGroupMsg() {
		this.cmd = Command.CMD_OPERATE_GROUP;
	}
	
	public short getCmd() {
		return cmd;
	}

	public void setCmd(short cmd) {
		this.cmd = cmd;
	}

	public int getOptType() {
		return optType;
	}

	public void setOptType(int optType) {
		this.optType = optType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupImage() {
		return groupImage;
	}

	public void setGroupImage(String groupImage) {
		this.groupImage = groupImage;
	}

	public int getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(int receiverType) {
		this.receiverType = receiverType;
	}

	public JoinGroupMsg getJoinGroup() {
		return joinGroup;
	}

	public void setJoinGroup(JoinGroupMsg joinGroup) {
		this.joinGroup = joinGroup;
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

}

package com.gome.im.api.global;

/**
 * 命令字定义
 */
public class Command {
	// 心跳
	public final static short CMD_HEARTBEAT = 0x0001;
	// 用户登录
	public final static short CMD_USER_LOGIN = 0x0002;
	// 用户登出
	public final static short CMD_USER_LOGOUT = 0x0003;
	// 用户登出
	public final static short CMD_USER_KICK = 0x0004;
	// 强制关闭用户连接
	public final static short CMD_CLOSE_CHANNEL = 0x0005;

	// 添加好友
	public final static short CMD_ADD_FRIEND = 0x0100;
	// 删除好友
	public final static short CMD_DEL_FRIEND = 0x0101;
	// 是否同意对方加为好友
	public final static short CMD_AGREE_FRIEND = 0x0102;
	// 创建群组
	public final static short CMD_OPERATE_GROUP = 0x0103;
	// 审核加入的群成员
	public final static short CMD_AUDIT_MEMBER = 0x0104;
	// 是否同意用户加入群
	public final static short CMD_AGREE_MEMBER = 0x0105;
	// 客户端拉取获取聊天群组
	public final static short CMD_LIST_GROUP = 0x0106;

	// 客户端发送IM消息，包括group消息和单聊消息
	public final static short CMD_IM_SEND_MSG = 0x0201;
	// 客户端拉取离线消息或增量消息同步
	public final static short CMD_IM_OFFLINE_MSG = 0x0202;
}

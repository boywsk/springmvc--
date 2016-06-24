package com.gome.im.upload.global;

/**
 * 常量、枚举等定义
 */
public class Constant {

	/**
	 * 群组容量
	 * 
	 */
	public static int GROUP_CAPACITY = 200;

	/**
	 * 好友状态
	 * 0 未通过， 1 通过状态， 2拒绝状态
	 */
	public enum FRIEND_STATUS {
		E_FRIEND_STATUS_NOT(0),//未通过
		E_FRIEND_STATUS_OK(1),//通过状态
		E_FRIEND_STATUS_REFUSE(2);//拒绝状态

		public int value;
		FRIEND_STATUS(int value) {
			this.value = value;
		}
	}

	/**
	 * 用户与群的关系状态 0 未通过， 1 通过状态， 2拒绝状态
	 */
	public enum GROUP_STATUS {
		E_GROUP_STATUS_NOT(0),//未通过
		E_GROUP_STATUS_OK(1);//通过状态
		public int value;
		GROUP_STATUS(int value) {
			this.value = value;
		}
	}

	/**
	 * 用户在群中是否置顶
	 * 0 未置顶， 1 置顶
	 */
	public enum GROUP_STICKIE {
		E_GROUP_STICKIE_NOT(0),//未置顶
		E_GROUP_STICKIE_OK(1);//置顶

		public int value;
		GROUP_STICKIE(int value) {
			this.value = value;
		}
	}

	/**
	 * 用户是否屏蔽群消息
	 * 0 未屏蔽， 1 屏蔽
	 */
	public enum GROUP_SHIELD {
		E_GROUP_SHIELD_NOT(0),//未屏蔽
		E_GROUP_SHIELD_OK(1);//屏蔽

		public int value;
		GROUP_SHIELD(int value) {
			this.value = value;
		}
	}

	/**
	 * 用户加入群是否需要审核
	 * 0 入群不需要审核， 1 入群需要审核
	 */
	public enum GROUP_AUDIT {
		E_GROUP_AUDIT_NOT(0),//不需要审核
		E_GROUP_AUDIT_NEED(1);//需要审核

		public int value;
		GROUP_AUDIT(int value) {
			this.value = value;
		}
	}

	/**
	 * 群的删除状态
	 * 0:未删除， 1:已删除
	 */
	public enum GROUP_DEL {
		E_GROUP_DEL_NOT(0),//未删除
		E_GROUP_DEL_OK(1);//删除

		public int value;
		GROUP_DEL(int value) {
			this.value = value;
		}
	}

	/**
	 * 群成员身份
	 */
	public enum GROUP_MEMBER_IDENTITY {
		E_MEMBER_ORDINARY(0), // 普通成员
		E_MEMBER_CREATOR(1), // 创建者
		E_MEMBER_MANAGER(2); // 管理员

		public int value;
		GROUP_MEMBER_IDENTITY(int value) {
			this.value = value;
		}
	}

	/**
	 * 群组类型 1 单聊 ，2 群聊 ，3 系统信息， 4 小秘书
	 */
	public enum CHAT_TYPE {
		E_CHAT_TYP_SINGLE(1), // 单聊
		E_CHAT_TYP_GROUP(2), // 群聊
		E_CHAT_TYP_SYS(3), // 系统信息
		E_CHAT_TYP_HELPER(4); // 小秘书

		public int value;
		CHAT_TYPE(int value) {
			this.value = value;
		}
	}

	// 消息类型
	public enum MESSAGE_TYPE {
		E_MESSAG_TYPE_TEXT(1), // 文本
		E_MESSAG_TYPE_VOICE(2), // 语音
		E_MESSAG_TYPE_IMG(3), // 图片
		E_MESSAG_TYPE_VIDEO(4), // 视频
		E_MESSAG_TYPE_POSITION(5), // 位置
		E_MESSAG_TYPE_ATTACH(6), // 附件
		E_MESSAG_TYPE_CARD(7), // 名片
		E_MESSAG_TYPE_SYS(8), // 系统消息
		// E_MESSAG_TYPE_NOTICE(9), // 通知消息
		E_MESSAG_TYPE_SHARE(9), // 分享/转发(通过url)
		E_MESSAG_TYPE_GOODS(10), // 商品
		E_MESSAG_TYPE_SHOP(11), // 商店
		E_MESSAG_TYPE_GROUP_OPT(12); // 群组操作通知

		public int value;
		MESSAGE_TYPE(int value) {
			this.value = value;
		}
	}

	// 群组操作类型
	// 1:加入群，2:退群，3:修改群，4:解散群
	public enum GROUP_OPT_TYPE {
		E_OPT_TYPE_JOIN(1), // 加入群
		E_OPT_TYPE_QUIT(2), // 退群
		E_OPT_TYPE_EDIT(3), // 修改群
		E_OPT_TYPE_DISB(4); // 解散群

		public int value;
		GROUP_OPT_TYPE(int value) {
			this.value = value;
		}
	}

	// 群组加入类型
	public enum GROUP_JOIN_TYPE {
		E_JOIN_TYPE_INVITE(1), // 邀请加入群
		E_JOIN_TYPE_QR(2), // 扫二维码加入群
		E_JOIN_TYPE_SEARCH(3); // 通过群名/号加入群

		public int value;
		GROUP_JOIN_TYPE(int value) {
			this.value = value;
		}
	}

	/**
	 * 文件类型 1 图片 2 音频 3 视频
	 */
	public enum FILE_TYPE {
		IMAGE(1), // 图片
		VOICE(2), // 音频
		VIDEO(3);// 视频
		
		public int value;
		FILE_TYPE(int value) {
			this.value = value;
		}
	}

	/**
	 * 文件格式 (后缀扩展名)
	 */
	public enum FILE_SUFFIX {
		JPG("jpg"), PNG("png"), GIF("gif"), AMR("amr"), AVI("avi");

		public String value;
		FILE_SUFFIX(String value) {
			this.value = value;
		}
	}

	// 群消息接收类型
	public enum GROUP_MESG_RECEIVE_TYPE {
		E_MESG_RECEIVE_TYPE_ALL(1), // 全部
		E_MESG_RECEIVE_TYPE_MANAGER(2); // 管理员

		public int value;
		GROUP_MESG_RECEIVE_TYPE(int value) {
			this.value = value;
		}
	}

	// API发送群消息类型 //消息类型；1:文本、2:语音、3:图片、4:视频、5:位置、6:附件、7:名片、8:系统消息、
	//9:分享/转发(通过url)、10:商品、11:店铺、99:消息透传
	public enum GROUP_MSG_TYPE {
		TEXT(1), // 文本
		VOICE(2), // 语音
		IMAGE(3), // 图片
		VIDEO(4), // 视频
		LOCATION(5), // 位置
		ATTACHMENT(6), // 附件
		CARD(7), // 名片
		SYSTEM(8), // 系统消息
		SHARE(9), // 分享/转发(通过url)
		GOODS(10), // 商品
		SHOP(11), // 店铺
		TRANSIMIT(99); // 消息透传
		public int value;
		GROUP_MSG_TYPE(int value) {
			this.value = value;
		}
	}
}

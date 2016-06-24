package cn.com.gome.manage.mongodb.model;

import java.io.Serializable;
import java.util.List;

import cn.com.gome.manage.pojo.*;
import com.alibaba.fastjson.JSON;

/**
 * 聊天消息
 */
public class GroupMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	private String msgId;	//消息id
	private int msgType;	// 1:文本、2:语音、3:图片、4:附件、5:分享/转发(通过url)、...
	private String msgBody;	// 消息体
	private long senderId;	// 发送者id
	private String senderName;	// 发送者名称
	private String senderRemark;// 消息发送者在该群中的昵称
	private String groupId;	//群组id
	private int groupType;	// 群组类型，1:单聊，2:群聊
	private String groupName;	//群组名称
	private String groupImg;//群组图片
	private long sendTime;	//发送服务器时间
	private long msgSeqId;	//自增计数
	private String msgUrl;//消息url
//	private String url;    //转发和分享链接URL
	private List<Attachment> msgAttch; //附件列表
	private MsgCard card;//名片
	private MsgShare share;//分享
	private MsgGoods goods;	//商品
	private MsgShop shop;//店铺
	private MsgTopic topic;	//话题
	private OperateGroup optGroup;//群组操作
	private String extra;		//扩展信息

	private String formatSendTime;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getMsgUrl() {
		return msgUrl;
	}

	public void setMsgUrl(String msgUrl) {
		this.msgUrl = msgUrl;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderRemark() {
		return senderRemark;
	}

	public void setSenderRemark(String senderRemark) {
		this.senderRemark = senderRemark;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getGroupType() {
		return groupType;
	}

	public void setGroupType(int groupType) {
		this.groupType = groupType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupImg() {
		return groupImg;
	}

	public void setGroupImg(String groupImg) {
		this.groupImg = groupImg;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public long getMsgSeqId() {
		return msgSeqId;
	}

	public void setMsgSeqId(long msgSeqId) {
		this.msgSeqId = msgSeqId;
	}

//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}

	public List<Attachment> getMsgAttch() {
		return msgAttch;
	}

	public void setMsgAttch(List<Attachment> msgAttch) {
		this.msgAttch = msgAttch;
	}

	public MsgCard getCard() {
		return card;
	}

	public void setCard(MsgCard card) {
		this.card = card;
	}

	public MsgShare getShare() {
		return share;
	}

	public void setShare(MsgShare share) {
		this.share = share;
	}

	public MsgGoods getGoods() {
		return goods;
	}

	public void setGoods(MsgGoods goods) {
		this.goods = goods;
	}

	public MsgShop getShop() {
		return shop;
	}

	public void setShop(MsgShop shop) {
		this.shop = shop;
	}

	public MsgTopic getTopic() {
		return topic;
	}

	public void setTopic(MsgTopic topic) {
		this.topic = topic;
	}

	public OperateGroup getOptGroup() {
		return optGroup;
	}

	public void setOptGroup(OperateGroup optGroup) {
		this.optGroup = optGroup;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	public String toString() {
		return JSON.toJSONString(this);
	}

	public String getFormatSendTime() {
		return formatSendTime;
	}

	public void setFormatSendTime(String formatSendTime) {
		this.formatSendTime = formatSendTime;
	}
}

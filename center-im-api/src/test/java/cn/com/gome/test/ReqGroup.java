package cn.com.gome.test;

import java.io.Serializable;
import java.util.List;

/**
 * 群业务 RequestBody中model的基类
 */
public class ReqGroup extends BaseRequest implements Serializable {

	private long uid; // 群组创建者id
	private String nickName;//群组创建者昵称
	private String groupId; // 群组id
	private String groupName; // 群组名称
	private String desc; // 群组描述
	private String content;//申请加入群输入的内容
	private List<Long> memberIds;// 成员id列表

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Long> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}
}

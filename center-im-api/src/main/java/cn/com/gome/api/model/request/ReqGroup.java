package cn.com.gome.api.model.request;

import cn.com.gome.api.model.Member;
import cn.com.gome.api.model.GroupExtra;

import java.io.Serializable;
import java.util.List;

/**
 * 群业务 RequestBody中model的基类
 */
public class ReqGroup extends BaseRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String appId;
	private String groupId; // 群组id
	private long imUserId; // 群组创建者的imUserId
	private List<Long> memberIds;// 成员id列表
	List<Member> members;
	private int capacity; // 群组容量
	private GroupExtra extra;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public long getImUserId() {
		return imUserId;
	}

	public void setImUserId(long imUserId) {
		this.imUserId = imUserId;
	}


	public List<Long> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}


	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public GroupExtra getExtra() {
		return extra;
	}

	public void setExtra(GroupExtra extra) {
		this.extra = extra;
	}
}

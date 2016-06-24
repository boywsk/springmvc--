package com.gome.im.api.tools;


import com.gome.im.api.db.model.Group;
import com.gome.im.api.db.model.GroupMember;
import com.gome.im.api.model.response.RspGroup;
import com.gome.im.api.model.response.RspGroupMember;

/**
 * 群组对象转换工具类
 */
public class GroupTools {
	
	/**
	 * 群组对象转response对象
	 * @param group
	 * @return
	 */
	public static RspGroup group2Rsp(Group group) {
		RspGroup rspGroup = new RspGroup();
		rspGroup.setGroupId(group.getGroupId());
		rspGroup.setUid(group.getUid());
		rspGroup.setGroupName(group.getGroupName());
		rspGroup.setAvatar(group.getAvatar());
		rspGroup.setDesc(group.getGroupDesc());
		rspGroup.setCapacity(group.getCapacity());
		rspGroup.setIsAudit(group.getIsAudit());
		rspGroup.setqRCode(group.getqRcode());
//		rspGroup.setType(group.getType());
		return rspGroup;
	}
	
	/**
	 * 群组成员对象转response对象
	 * @param member
	 * @return
	 */
	public static RspGroupMember member2Rsp(GroupMember member) {
		RspGroupMember rspMember = new RspGroupMember();
//		rspMember.setGroupId(member.getGroupId());
		rspMember.setUid(member.getUid());
		rspMember.setNickName(member.getNickName());
		rspMember.setJoinTime(member.getJoinTime());
		return rspMember;
	}
}

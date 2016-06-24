package cn.com.gome.api.tools;

import cn.com.gome.api.model.Group;
import cn.com.gome.api.model.GroupMember;
import cn.com.gome.api.model.response.RspGroup;
import cn.com.gome.api.model.response.RspGroupMember;

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
		rspGroup.setName(group.getName());
//		rspGroup.setAvatar(group.getAvatar());
//		rspGroup.setDesc(group.getDesc());
		rspGroup.setCapacity(group.getCapacity());
//		rspGroup.setIsAudit(group.getIsAudit());
//		rspGroup.setqRCode(group.getqRCode());
//		rspGroup.setLastMsg(group.getLastMsg());
		rspGroup.setType(group.getType());
		
		return rspGroup;
	}
	
	/**
	 * 群组成员对象转response对象
	 * @param member
	 * @return
	 */
	public static RspGroupMember member2Rsp(GroupMember member) {
		RspGroupMember rspMember = new RspGroupMember();
		rspMember.setGroupId(member.getGroupId());
		rspMember.setUid(member.getUid());
		rspMember.setNickName(member.getNickName());
//		rspMember.setNickNameSpell(member.getNickNameSpell());
//		rspMember.setNickNameHeadChar(member.getNickNameHeadChar());
//		rspMember.setSelfMark(member.getSelfMark());
//		rspMember.setSelfMarkSpell(member.getSelfMarkSpell());
//		rspMember.setSelfMarkHeadChar(member.getSelfMarkHeadChar());
		rspMember.setJoinTime(member.getJoinTime());
//		rspMember.setUpdateTime(member.getUpdateTime());
		return rspMember;
	}
}

package com.gomeplus.im.api.dao;


import com.gomeplus.im.api.model.GroupMember;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wangshikai on 2016/2/22.
 */
public interface GroupMemberMapper {

    public void saveGroupMemberBatch(List<GroupMember> groupMemberList);

    public void updateGroupMember(GroupMember groupMember);

    public GroupMember getGroupMember(HashMap<String, Object> map);

    public List<GroupMember> listGroupMember(HashMap<String, Object> map);

    public List<GroupMember> memberGroups(long uid);

    public void delGroupMember(HashMap<String, Object> map);

    public void delAllGroupMember(String groupId);
}

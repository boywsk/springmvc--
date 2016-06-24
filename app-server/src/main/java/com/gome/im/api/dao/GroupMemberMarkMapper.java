package com.gome.im.api.dao;

import com.gome.im.api.db.model.GroupMemberMark;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wangshikai on 2016/2/22.
 */
public interface GroupMemberMarkMapper {

    public void saveGroupMemberMark(GroupMemberMark groupMemberMark);

    public void updateGroupMemberMark(GroupMemberMark groupMemberMark);

    public GroupMemberMark getGroupMemberMark(HashMap<String,Object> map);

    public List<GroupMemberMark> listGroupMemberMark(HashMap<String,Object> map);

    public void delGroupMemberMark(HashMap<String,Object> map);

    public void delAllGroupMemberMark(String groupId);
}

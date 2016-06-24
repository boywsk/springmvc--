package com.gome.im.api.dao;

import com.gome.im.api.db.model.FriendGroup;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wangshikai on 2016/2/22.
 */
public interface FriendGroupMapper {
    public int addFriendGroup(FriendGroup friendGroup);

    public int updateFriendGroup(FriendGroup friendGroup);

    public FriendGroup getFriendGroup(HashMap<String,Object> parm);

    public List<FriendGroup> listPersonalGroup(HashMap<String,Object> parm);

    public List<FriendGroup> listFriendGroup(HashMap<String,Object> parm);

    public int delFriendGroup(HashMap<String,Object> parm);
}

package com.gome.im.api.dao;

import com.gome.im.api.db.model.Friend;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wangshikai on 2016/2/22.
 */
public interface FriendMapper {
    public void saveFriend(Friend friend);

    public void updateFriend(Friend friend);

    public Friend getFriend(HashMap<String,Object> parm);

    public List<Friend> listFriend(HashMap<String,Object> parm);

    public List<Friend> allFriends(HashMap<String,Object> parm);

    public void delFriend(HashMap<String,Object> parm);
}

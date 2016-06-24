package com.gomeplus.im.api.dao;


import com.gomeplus.im.api.model.Friend;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lijinpeng on 2016/6/6.
 */
public interface FriendMapper {
    public void saveFriend(Friend friend);

    public int updateFriend(Friend friend);
    public int updateFriendStatus(Friend friend);

    public int updateNickNameByFUid(Friend friend);
    
    public int updateFriendByGroupId(HashMap<String, Object> parm);

    public Friend getFriend(HashMap<String, Object> parm);

    public List<Friend> listFriend(HashMap<String, Object> parm);
    
    public List<Friend> getFriendsByGroupId(HashMap<String, Object> parm);

    public List<Friend> allFriends(HashMap<String, Object> parm);

    public int deleteFriend(HashMap<String, Object> parm);
}

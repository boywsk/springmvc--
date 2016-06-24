package com.gomeplus.im.api.dao;

import java.util.List;

import com.gomeplus.im.api.model.FriendGroup;

/**
 * 好友分组
 * Created by wangshikai on 2016/2/22.
 */
public interface FriendGroupMapper {
	
    public int saveFriendGroup(FriendGroup friendGroup);

    public int updateFriendGroupById(FriendGroup friendGroup);
    
    public int deleteFriendGroupById(long id);
    
    public int deleteFriendGroupByUserId(long userId);
    
    public FriendGroup getFriendGroupById(long id);
    
    public List<FriendGroup> getFriendGroupByUserId(long userId);
    
}

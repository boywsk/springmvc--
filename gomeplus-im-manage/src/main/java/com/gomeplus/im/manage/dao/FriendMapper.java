package com.gomeplus.im.manage.dao;

import com.gomeplus.im.manage.model.Friend;

import java.util.List;
import java.util.Map;

/**
 * Created by wangshikai on 2016/2/22.
 */
public interface FriendMapper {

    public List<Friend> getFriendsByPage(Map<String, Object> parm);

    public int countFriend(long userId);

}

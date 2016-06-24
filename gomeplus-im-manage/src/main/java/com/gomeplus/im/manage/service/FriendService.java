package com.gomeplus.im.manage.service;

import com.gomeplus.im.manage.model.Friend;
import com.gomeplus.im.manage.pageSupport.PageInfo;

import java.util.List;

/**
 * Created by wangshikai on 2016/6/3.
 */
public interface FriendService {

    public List<Friend> getFriendsByUserId(long userId,PageInfo pageInfo);

}

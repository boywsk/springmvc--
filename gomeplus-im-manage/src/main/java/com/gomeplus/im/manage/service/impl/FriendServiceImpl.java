package com.gomeplus.im.manage.service.impl;

import com.gomeplus.im.manage.dao.FriendMapper;
import com.gomeplus.im.manage.model.Friend;
import com.gomeplus.im.manage.pageSupport.PageInfo;
import com.gomeplus.im.manage.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshikai on 2016/6/3.
 */
@Service
public class FriendServiceImpl implements FriendService{

    @Autowired
    private FriendMapper friendMapper;

    @Override
    public List<Friend> getFriendsByUserId(long userId,PageInfo pageInfo) {
        Map<String,Object> parm = new HashMap<>();
        parm.put("userId",userId);
        parm.put("start",(pageInfo.getCurrentPage()-1)*pageInfo.getPageSize());
        parm.put("end",pageInfo.getCurrentPage()*pageInfo.getPageSize());
        int count = friendMapper.countFriend(userId);
        pageInfo.setTotalResult(count);
        pageInfo.calculate();
        return friendMapper.getFriendsByPage(parm);
    }
}

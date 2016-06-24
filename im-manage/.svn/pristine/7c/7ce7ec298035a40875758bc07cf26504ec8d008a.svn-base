package cn.com.gome.manage.mongodb.service;

import cn.com.gome.manage.mongodb.dao.FriendDao;
import cn.com.gome.manage.mongodb.model.Friend;
import cn.com.gome.manage.pageSupport.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshikai on 2015/12/29.
 */
public class FriendService {
    private static Logger log = LoggerFactory.getLogger(FriendService.class);

    private FriendDao friendDao = new FriendDao();

    /***
     * 获取好友列表
     * @param uid
     * @param time
     * @return
     */
    public List<Friend> getFriendList(long uid,long time,PageInfo pageInfo){
        List<Friend> friendList = new ArrayList<Friend>();
        friendList = friendDao.queryFriends(uid,time,pageInfo);
        return friendList;
    }

    /***
     * 获取两个指定用户的关系
     * @param uid
     * @param fid
     * @return
     */
    public List<Friend> getRelation(long uid,long fid){
        List<Friend> friendList = new ArrayList<Friend>();
        friendList = friendDao.queryFriendsById(uid,fid);
        return friendList;
    }

}

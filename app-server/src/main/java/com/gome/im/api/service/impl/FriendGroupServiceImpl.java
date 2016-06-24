package com.gome.im.api.service.impl;

import com.gome.im.api.dao.FriendGroupMapper;
import com.gome.im.api.dao.FriendMapper;
import com.gome.im.api.db.model.Friend;
import com.gome.im.api.db.model.FriendGroup;
import com.gome.im.api.model.ResultModel;
import com.gome.im.api.model.request.ReqFriendGroup;
import com.gome.im.api.service.FriendGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FriendGroupServiceImpl implements FriendGroupService {
    Logger log = LoggerFactory.getLogger(FriendGroupServiceImpl.class);

    @Autowired
    private FriendGroupMapper friendGroupMapper;

    @Autowired
    private FriendMapper friendMapper;

    public ResultModel<Object> addFriendGroup(ReqFriendGroup req) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "添加朋友圈失败", req);
        try {
            long time = System.currentTimeMillis();
            FriendGroup friendGroup = new FriendGroup();
            friendGroup.setUid(req.getUid());
            friendGroup.setNickName(req.getNickName());
            friendGroup.setContent(req.getContent());
            friendGroup.setContentType(req.getContentType());
            friendGroup.setExtra(req.getExtra());
            friendGroup.setCreateTime(time);
            friendGroup.setUpdateTime(time);
            int code = friendGroupMapper.addFriendGroup(friendGroup);
            if (code > 0) {
                resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "添加成功", new HashMap<>());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    @Override
    public ResultModel<Object> delFriendGroup(ReqFriendGroup req) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "删除失败", req);
        try {
            HashMap<String, Object> parms = new HashMap<>();
            parms.put("id", req.getId());
            parms.put("uid", req.getUid());
            int code = friendGroupMapper.delFriendGroup(parms);
            if (code > 0) {
                resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "删除成功", parms);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    @Override
    public ResultModel<Object> personalFriendGroup(ReqFriendGroup req) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "失败", req);
        try {
            HashMap<String, Object> parms = new HashMap<>();
            parms.put("uid", req.getUid());
            List<FriendGroup> list = friendGroupMapper.listPersonalGroup(parms);
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    @Override
    public ResultModel<Object> listFriendGroup(ReqFriendGroup req) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "失败", req);
        try {
            long beforeDay = req.getBeforeDay();
            long endTime = req.getEndTime();
            long nowTime = System.currentTimeMillis();
            if(beforeDay <= 0){
                return  new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", req);
            }
            long dayTime = 1*24*3600*1000;
            HashMap<String, Object> parms = new HashMap<>();
            parms.put("uid", req.getUid());
            List<Friend> list = friendMapper.allFriends(parms);
            List<FriendGroup> friendGroups = new ArrayList<>();
            long startTime = nowTime - beforeDay*dayTime; //某天前时间
            if(endTime <= startTime){
                endTime = nowTime;
            }
            parms.put("uid", req.getUid());
            parms.put("startTime",startTime);  //获取好友从某天前开始发布的朋友圈
            parms.put("endTime",endTime);  //获取好友从某天前开始发布的朋友圈
            List<FriendGroup> friendGroupList = friendGroupMapper.listFriendGroup(parms);
            friendGroups.addAll(friendGroupList);
            for(Friend friend : list){
                parms.put("uid", friend.getFriendUid());
                parms.put("startTime",startTime);  //获取好友从某天前开始发布的朋友圈
                parms.put("endTime",endTime);  //获取好友从某天前开始发布的朋友圈
                friendGroupList = friendGroupMapper.listFriendGroup(parms);
                if(friendGroupList != null){
                    friendGroups.addAll(friendGroupList);
                }
            }
            Collections.sort(friendGroups, new Comparator<FriendGroup>() {
                @Override
                public int compare(FriendGroup o1, FriendGroup o2) {
                    if(o1.getCreateTime() >= o2.getCreateTime()){
                        return -1;
                    }else{
                        return 1;
                    }
                }
            });
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "成功", friendGroups);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }


}

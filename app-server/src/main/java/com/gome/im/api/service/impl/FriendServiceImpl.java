package com.gome.im.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.gome.im.api.dao.FriendMapper;
import com.gome.im.api.dao.UserMapper;
import com.gome.im.api.db.model.Friend;
import com.gome.im.api.db.model.User;
import com.gome.im.api.global.Constant;
import com.gome.im.api.model.ResultModel;
import com.gome.im.api.model.request.ReqFriend;
import com.gome.im.api.model.response.RspFriend;
import com.gome.im.api.service.FriendService;
import com.gome.im.api.threadPool.ThreadPool;
import com.gome.im.api.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    Logger log = LoggerFactory.getLogger(FriendServiceImpl.class);

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加好友
     */
    public ResultModel<Object> addFriend(ReqFriend reqFriend) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "申请好友异常", new HashMap<>());
        try {
            if (reqFriend == null) {
                log.error("param reqFriend is null");
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数异常", new HashMap<>());
            }
            final long uid = reqFriend.getUid();
            final long fid = reqFriend.getFriendUid();
            User user1 = userMapper.getUserInfo(uid);
            User user2 = userMapper.getUserInfo(fid);
            if(user1 == null || user2 == null){
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "用户不存在", new HashMap<>());
            }
            // 检查参数 是否正常
            ResultModel<Object> result = checkId(uid, fid);
            if (result != null) {
                return result;
            }
            // 检查请求参数，检查好友申请信息是否已经存在
            HashMap<String, Object> parm = new HashMap<>();
            parm.put("uid", uid);
            parm.put("friendUid", fid);
            Friend friend = friendMapper.getFriend(parm);
            if (friend != null) {
//                int status = friend.getStatus();
//                if (status == 1) {
//                    return new ResultModel<Object>(ResultModel.RESULT_OK, "已是好友", new HashMap<>());
//                } else {
//                    return new ResultModel<Object>(ResultModel.RESULT_OK, "好友申请已发出，等待审核通过", new HashMap<>());
//                }
            }else{
                friend = new Friend();
                long time = System.currentTimeMillis();
                friend.setUid(uid);
                friend.setFriendUid(fid);
                friend.setStatus(Constant.FRIEND_STATUS.E_FRIEND_STATUS_OK.value);
                friend.setFriendNickName(reqFriend.getNickName());
                friend.setCreateTime(time);
                friend.setUpdateTime(time);
                friendMapper.saveFriend(friend);
                parm.put("uid", fid);
                parm.put("friendUid", uid);
                Friend friend2 = friendMapper.getFriend(parm);
                if (friend2 == null) {
                    //保存双向好友关系
                    friend2 = new Friend();
                    friend2.setUid(fid);
                    friend2.setFriendUid(uid);
                    friend2.setStatus(Constant.FRIEND_STATUS.E_FRIEND_STATUS_NOT.value);
                    String friendName = user1.getNickName();
                    friend2.setFriendNickName(friendName);
                    friend2.setCreateTime(time);
                    friend2.setUpdateTime(time);
                    friendMapper.saveFriend(friend2);
                }
                final String senderNickName = reqFriend.getNickName();
                ThreadPool pool = ThreadPool.getInstance();
                //发送个人消息，通知对方申请加好友
                pool.addTask(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            long senderImUserId = userMapper.getUserInfo(uid).getImUserId();
                            long receiveImUserId = userMapper.getUserInfo(fid).getImUserId();
                            String content = senderNickName + "想加你为好友，请审核";
                            String json = HttpUtil.sendSimpleCmdMsg(senderImUserId, senderNickName, false, receiveImUserId, content, Constant.MSG_CONTENT_TYPE.AUDIT_FRIEND.value, false);
                            if (JSON.parseObject(json).getString("code").equals("1")) {
                                //失败重试一次
                                HttpUtil.sendSimpleCmdMsg(senderImUserId, senderNickName, false, receiveImUserId, content, Constant.MSG_CONTENT_TYPE.AUDIT_FRIEND.value, false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            return new ResultModel<Object>(ResultModel.RESULT_OK, "好友申请成功", new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 好友申请审核
     */
    public ResultModel<Object> auditFriend(ReqFriend reqFriend) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "审核异常", new HashMap<>());
        try {
            if (reqFriend == null) {
                log.error("param reqFriend is null");
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "审核异常", new HashMap<>());
            }
            final long uid = reqFriend.getUid();
            final long fid = reqFriend.getFriendUid();
            if (uid == fid) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "已是好友", new HashMap<>());
            }

            int status = reqFriend.getStatus();
            // 检查参数 是否正常
            ResultModel<Object> result = checkId(uid, fid);
            if (result != null) {
                return result;
            }
            //审核通过，判断是否已经是好友
            if (status == Constant.FRIEND_STATUS.E_FRIEND_STATUS_OK.value) {
                HashMap<String, Object> parm = new HashMap<>();
                parm.put("uid", uid);
                parm.put("friendUid", fid);
                Friend friend = friendMapper.getFriend(parm);
                if (friend != null && friend.getStatus() != Constant.FRIEND_STATUS.E_FRIEND_STATUS_OK.value) {
                    friend.setStatus(status);
                    friend.setUpdateTime(System.currentTimeMillis());
                    friendMapper.updateFriend(friend);
                    ThreadPool pool = ThreadPool.getInstance();
                    pool.addTask(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                User user = userMapper.getUserInfo(fid);
                                long senderImUserId = user.getImUserId();
                                String senderNickName = user.getNickName();
                                long receiveImUserId = userMapper.getUserInfo(uid).getImUserId();
                                String content = senderNickName + "已加你为好友";
                                String json = HttpUtil.sendSimpleMsg(senderImUserId, senderNickName, false, receiveImUserId, content,Constant.MSG_CONTENT_TYPE.AUDIT_FRIEND_RESULT.value,true);
                                if (JSON.parseObject(json).getString("code").equals("1")) {
                                    //失败重试一次
                                    HttpUtil.sendSimpleMsg(senderImUserId, senderNickName, false, receiveImUserId, content,Constant.MSG_CONTENT_TYPE.AUDIT_FRIEND_RESULT.value,true);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    return new ResultModel<Object>(ResultModel.RESULT_OK, "审核成功", new HashMap<>());
                }
            }
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "审核成功", new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 删除好友关系
     */
    public ResultModel<Object> delFriend(ReqFriend reqFriend) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "删除好友异常", new HashMap<>());
        try {
            if (reqFriend == null) {
                log.error("param reqFriend is null");
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            final long uid = reqFriend.getUid();
            final long fid = reqFriend.getFriendUid();
            // 检查参数是否异常
            ResultModel<Object> result = checkId(uid, fid);
            if (result != null) {
                return result;
            }
            //删除好友关系
            HashMap<String, Object> parm = new HashMap<>();
            parm.put("uid", uid);
            parm.put("friendUid", fid);
            friendMapper.delFriend(parm);
            // 通过mq将向接入层发消息
            ThreadPool pool = ThreadPool.getInstance();
            pool.addTask(new Runnable() {
                @Override
                public void run() {
                    try {
                        User user = userMapper.getUserInfo(uid);
                        long senderImUserId = user.getImUserId();
                        String senderNickName = user.getNickName();
                        long receiveImUserId = userMapper.getUserInfo(fid).getImUserId();
                        String content = senderNickName + "将你移除好友列表";
                        String json = HttpUtil.sendSimpleCmdMsg(senderImUserId, senderNickName, false, receiveImUserId, content, Constant.MSG_CONTENT_TYPE.REMOVE_FRIEND.value, true);
                        if (JSON.parseObject(json).getString("code").equals("1")) {
                            //失败重试一次
                            HttpUtil.sendSimpleCmdMsg(senderImUserId, senderNickName, false, receiveImUserId, content,Constant.MSG_CONTENT_TYPE.REMOVE_FRIEND.value,true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "删除成功", new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 修改好友备注
     */
    @Override
    public ResultModel<Object> setMark(ReqFriend reqFriend) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "修改好友备注失败", new HashMap<>());
        try {
            long uid = reqFriend.getUid();
            long fid = reqFriend.getFriendUid();
            String mark = reqFriend.getMark();
            // 检查参数是否异常
            ResultModel<Object> result = checkId(uid, fid);
            if (result != null) {
                return result;
            }
            if (mark == null || "".equals(mark)) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "备注不能为空", new HashMap<>());
            }
            HashMap<String, Object> parm = new HashMap<>();
            parm.put("uid", uid);
            parm.put("friendUid", fid);
            Friend friend = friendMapper.getFriend(parm);
            if (friend != null) {
                friend.setMark(mark);
                friend.setUpdateTime(System.currentTimeMillis());
                friendMapper.updateFriend(friend);
                resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "修改好友备注成功", new HashMap<>());
            }else{
                resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "修改好友备注失败，非好友", new HashMap<>());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 获取好友列表
     */
    public ResultModel<Object> listFriend(ReqFriend reqFriend) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "获取好友列表失败", new HashMap<>());
        List<Friend> friends = new ArrayList<>();
        List<RspFriend> rsp = new ArrayList<>();
        try {
            HashMap<String,Object> parm = new HashMap<>();
            parm.put("uid",reqFriend.getUid());
            parm.put("start",0);
            parm.put("size",200);
            friends = friendMapper.listFriend(parm);
            for(Friend f : friends){
                RspFriend rspFriend = new RspFriend();
                rspFriend.setFriendUid(f.getFriendUid());
                //rspFriend.setFriendNickName(f.getFriendNickName());
                rspFriend.setMark(f.getMark());
                rspFriend.setStatus(f.getStatus());
                rspFriend.setUpdateTime(f.getUpdateTime());
                rsp.add(rspFriend);
            }
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK,"获取好友列表成功",rsp);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 检查用户id 和 好友id 是否异常
     *
     * @param uid 用户id
     * @param fid 好友id
     * @return 返回null，则需要继续处理。返回非null，则将数据作为返回值下发
     */
    public ResultModel<Object> checkId(long uid, long fid) {
        if (uid < 0L || fid < 0L) {
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "用户id异常", new HashMap<>());
        }
        return null;
    }

}

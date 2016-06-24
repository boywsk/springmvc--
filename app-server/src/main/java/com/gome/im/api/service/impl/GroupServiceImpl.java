package com.gome.im.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.gome.im.api.dao.GroupMapper;
import com.gome.im.api.dao.GroupMemberMapper;
import com.gome.im.api.dao.GroupMemberMarkMapper;
import com.gome.im.api.dao.UserMapper;
import com.gome.im.api.db.model.Group;
import com.gome.im.api.db.model.GroupMember;
import com.gome.im.api.db.model.GroupMemberMark;
import com.gome.im.api.db.model.User;
import com.gome.im.api.global.Constant;
import com.gome.im.api.model.Member;
import com.gome.im.api.model.ResultModel;
import com.gome.im.api.model.request.ReqGroup;
import com.gome.im.api.model.response.RspGroup;
import com.gome.im.api.model.response.RspGroupMember;
import com.gome.im.api.model.response.RspMemberMark;
import com.gome.im.api.service.GroupService;
import com.gome.im.api.threadPool.ThreadPool;
import com.gome.im.api.tools.GroupTools;
import com.gome.im.api.utils.HttpUtil;
import com.gome.im.api.utils.StringUtils;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupMemberMapper groupMemberMapper;
    @Autowired
    private GroupMemberMarkMapper groupMemberMarkMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 创建群组
     */
    public ResultModel<Object> addGroup(final ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "创建失败", new HashMap<>());
        try {
            final String groupId = StringUtils.getUuid();
            long uid = reqGroup.getUid();
            User user = userMapper.getUserInfo(uid);
//            final String content = reqGroup.getContent();
            final String nikeName = reqGroup.getNickName();
            if (user == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "创建失败,用户不存在", new HashMap<>());
            }
            final long imUserId = user.getImUserId();
            String groupName = reqGroup.getGroupName();
            String desc = reqGroup.getDesc();
            String avatar = reqGroup.getAvatar();
            int capacity = reqGroup.getCapacity();
            if (capacity <= 0) {//暂时默认200
                capacity = 200;
            }
            int isAudit = reqGroup.getIsAudit();
            List<Member> members = reqGroup.getMembers();
            if(members == null){
                members = new ArrayList<>();
            }
            final List<Member> members2 = members;
            List<GroupMember> groupMemberList = new ArrayList<>();
            ThreadPool pool = ThreadPool.getInstance();
            final String finalGroupName = groupName;
            pool.addTask(new Runnable() {
                @Override
                public void run() {
                    try {
                        String content = nikeName + "创建群组";
                        String json = HttpUtil.notifyAddGroup(imUserId, groupId, content,nikeName,members2, finalGroupName);
                        if (JSON.parseObject(json).getString("code").equals("1")) {
                            //失败重试一次
                            HttpUtil.notifyAddGroup(imUserId, groupId, content,nikeName,members2,finalGroupName);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                    }
                }
            });
            long time = System.currentTimeMillis();
            int status = 1;
            GroupMember owner = new GroupMember();
            owner.setUid(uid);
            owner.setNickName(nikeName);
            owner.setGroupId(groupId);
            owner.setStickies(0);//不置顶
            owner.setIsShield(0); //不屏蔽群消息
            owner.setStatus(status);
            owner.setJoinTime(time);
            groupMemberList.add(0, owner);
            //保存群成员
            for (Member m : members) {
                GroupMember member = new GroupMember();
                member.setUid(m.getUid());
                member.setNickName(m.getUserName());
                member.setGroupId(groupId);
                member.setStickies(0);//不置顶
                member.setIsShield(0); //不屏蔽群消息
                member.setStatus(status);
                member.setJoinTime(time);
                groupMemberList.add(member);
            }
            groupMemberMapper.saveGroupMemberBatch(groupMemberList);
            //保存群组信息
            Group group = new Group();
            group.setGroupId(groupId);
            group.setUid(uid);
            group.setCapacity(capacity);
            group.setIsAudit(isAudit);
            group.setStickies(0);
            group.setIsDele(0);
            StringBuilder builder = new StringBuilder();
            if (Strings.isNullOrEmpty(groupName)) {
                for (int i = 0; i < groupMemberList.size(); i++) {
                    GroupMember member = groupMemberList.get(i);
                    if (i != 1) {
                        String nickName = member.getNickName();
                        if (builder.length() > 0) {
                            builder.append("、");
                            builder.append(nickName);
                        } else {
                            builder.append(nickName);
                        }
                    }
                }
                groupName = builder.substring(0, 60) + "......";
            }
            group.setGroupDesc(desc);
            group.setType(2);
            group.setGroupName(groupName);
            group.setqRcode(reqGroup.getqRCode());
            group.setAvatar(avatar);
            group.setCreateTime(time);
            group.setUpdateTime(time);
            groupMapper.saveGroup(group);
            RspGroup repGroup = GroupTools.group2Rsp(group);
            repGroup.setType(2);
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "创建成功", repGroup);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 加入群
     */
    public ResultModel<Object> joinGroup(ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "加入失败", new HashMap<>());
        try {
            final String groupId = reqGroup.getGroupId();
            final long senderId = reqGroup.getUid();
            final String nickName = reqGroup.getNickName();
            long time = System.currentTimeMillis();
            final Group group = groupMapper.getGroup(groupId);
            if (group == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            int isAudit = group.getIsAudit();
            int status = Constant.GROUP_STATUS.E_GROUP_STATUS_NOT.value;
            boolean isSave = false;
            HashMap<String, Object> parmMap = new HashMap<>();
            parmMap.put("groupId", groupId);
            parmMap.put("uid", senderId);
            GroupMember member = groupMemberMapper.getGroupMember(parmMap);
            if (member == null) {
                isSave = true;
                //不需要审核
                if (isAudit == Constant.GROUP_AUDIT.E_GROUP_AUDIT_NOT.value) {
                    status = Constant.GROUP_STATUS.E_GROUP_STATUS_OK.value;
                }
            } else {
                int oldStatus = member.getStatus();
                //等待审核或者已经是成员
                if (oldStatus == Constant.GROUP_STATUS.E_GROUP_STATUS_NOT.value) {
                    return new ResultModel<Object>(ResultModel.RESULT_OK, "等待管理员审核", new HashMap<>());
                } else if (oldStatus == Constant.GROUP_STATUS.E_GROUP_STATUS_OK.value) {
                    return new ResultModel<Object>(ResultModel.RESULT_OK, "已经加入该群", new HashMap<>());
                }
            }
            if (isSave) {
                member = new GroupMember();
                member.setGroupId(groupId);
                member.setUid(senderId);
                member.setNickName(nickName);
                member.setStatus(status);
                member.setJoinTime(time);
                member.setStickies(0);
                member.setIsShield(0);
                member.setInitSeq(0);
                member.setReadSeq(0);
                List<GroupMember> list = new ArrayList<>();
                list.add(member);
                groupMemberMapper.saveGroupMemberBatch(list);
            }
            ThreadPool pool = ThreadPool.getInstance();
            //需要审核;通知管理员审核;保存系统消息
            if (isAudit == Constant.GROUP_AUDIT.E_GROUP_AUDIT_NEED.value) {
                //发送个人消息，通知群创建者
                pool.addTask(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            long senderImUserId = userMapper.getUserInfo(senderId).getImUserId();
                            long receiveImUserId = userMapper.getUserInfo(group.getUid()).getImUserId();
                            String content = nickName + "想加入群组:" + group.getGroupName() + "，请审核";
                            String json = HttpUtil.sendSimpleMsg(senderImUserId, nickName, false, receiveImUserId, content,Constant.MSG_CONTENT_TYPE.AUDIT_GROUP.value,false);
                            if (JSON.parseObject(json).getString("code").equals("1")) {
                                //失败重试一次
                                HttpUtil.sendSimpleMsg(senderId, nickName, false, receiveImUserId, content,Constant.MSG_CONTENT_TYPE.AUDIT_GROUP.value,false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error(e.getMessage());
                        }
                    }
                });
                return new ResultModel<Object>(ResultModel.RESULT_OK, "等待管理员审核", new HashMap<>());
            } else {//不需要审核全群通知;存离线消息
                // //发送群组消息，通知全群
                pool.addTask(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //调用加入群组接口
                            long senderImUserId = userMapper.getUserInfo(senderId).getImUserId();
                            String content = nickName + "加入群组:" + group.getGroupName();
                            List<Member> memberList = new ArrayList<Member>();
                            Member m = new Member();
                            m.setImUserId(senderImUserId);
                            m.setUserName(nickName);
                            memberList.add(m);
                            String json = HttpUtil.joinGroup(groupId, memberList,content,group.getGroupName(),nickName,senderImUserId);
                            if (JSON.parseObject(json).getString("code").equals("1")) {
                                //失败重试一次
                                HttpUtil.joinGroup(groupId, memberList,content,group.getGroupName(),nickName,senderImUserId);
                            }
//                            json = HttpUtil.sendGroupMsg(senderImUserId, nickName, false, groupId,group.getGroupName(), content);
//                            if(JSON.parseObject(json).getString("code").equals("1")){
//                                //失败重试一次
//                                HttpUtil.sendGroupMsg(senderImUserId, nickName, false, groupId, group.getGroupName(), content);
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error(e.getMessage());
                        }
                    }
                });
            }
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "加入成功", group);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 邀请加入群
     */
    public ResultModel<Object> inviteGroup(ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "邀请失败", new HashMap<>());
        try {
            final List<Member> members = reqGroup.getMembers();
            if (members == null || members.size() <= 0) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            long time = System.currentTimeMillis();
            final String groupId = reqGroup.getGroupId();
            final long senderId = reqGroup.getUid();
            final String senderName = reqGroup.getNickName();
            final Group group = groupMapper.getGroup(groupId);
            if (group == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            int isAudit = group.getIsAudit();
            HashMap<String, Object> parmMap = new HashMap<>();
            parmMap.put("groupId", groupId);
            parmMap.put("uid", senderId);
            GroupMember member = groupMemberMapper.getGroupMember(parmMap);
            if (member == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            final List<GroupMember> groupMemberList = new ArrayList<GroupMember>();
            //不需要审核;管理员邀请加入
            if (isAudit == Constant.GROUP_AUDIT.E_GROUP_AUDIT_NOT.value || group.getUid() == senderId) {
                int status = Constant.GROUP_STATUS.E_GROUP_STATUS_OK.value;
                for (Member m : members) {
                    GroupMember groupMember = new GroupMember();
                    groupMember.setUid(m.getUid());
                    groupMember.setNickName(m.getUserName());
                    groupMember.setGroupId(groupId);
                    groupMember.setStickies(0);//不置顶
                    groupMember.setIsShield(0); //不屏蔽群消息
                    groupMember.setStatus(status);
                    groupMember.setJoinTime(time);
                    parmMap.put("uid", m.getUid());
                    GroupMember oldMember = groupMemberMapper.getGroupMember(parmMap);
                    if(oldMember == null){
                        groupMemberList.add(groupMember);
                    }
                }
                groupMemberMapper.saveGroupMemberBatch(groupMemberList);
                log.info("members size=[{}]", members.size());
                if (members.size() > 0) {
                    ThreadPool pool = ThreadPool.getInstance();
                    pool.addTask(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String content = "加入群组";
                                String json = HttpUtil.joinGroup(groupId, members,content,group.getGroupName(),senderName,senderId);
                                if (JSON.parseObject(json).getString("code").equals("1")) {
                                    //失败重试一次
                                    HttpUtil.joinGroup(groupId, members,content,group.getGroupName(),senderName,senderId);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                return new ResultModel<Object>(ResultModel.RESULT_OK, "邀请成功", new HashMap<>());
            } else {//需要审核
                //普通成员邀请；发消息给管理审核
                int status = Constant.GROUP_STATUS.E_GROUP_STATUS_NOT.value;
                for (Member m : members) {
                    GroupMember groupMember = new GroupMember();
                    groupMember.setUid(m.getUid());
                    groupMember.setNickName(m.getUserName());
                    groupMember.setGroupId(groupId);
                    groupMember.setStickies(0);//不置顶
                    groupMember.setIsShield(0); //不屏蔽群消息
                    groupMember.setStatus(status);
                    groupMember.setJoinTime(time);
                    groupMemberList.add(member);
                }
                groupMemberMapper.saveGroupMemberBatch(groupMemberList);
                for (final GroupMember m : groupMemberList) {
                    final String name = m.getNickName();
                    final String message = senderName + "邀请" + name + "加入群聊等待您的审核";
                    final long uid = m.getUid();
                    final long receiveImUserId = userMapper.getUserInfo(group.getUid()).getImUserId();
                    ThreadPool pool = ThreadPool.getInstance();
                    //发送个人消息，通知群创建者
                    pool.addTask(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                long senderImUserId = userMapper.getUserInfo(uid).getImUserId();
                                String content = name + "想加入群组:" + group.getGroupName() + "，请审核";
                                String json = HttpUtil.sendSimpleMsg(senderImUserId, name, false, receiveImUserId, message,Constant.MSG_CONTENT_TYPE.AUDIT_GROUP.value,false);
                                if (JSON.parseObject(json).getString("code").equals("1")) {
                                    //失败重试一次
                                    HttpUtil.sendSimpleMsg(senderId, name, false, receiveImUserId, message,Constant.MSG_CONTENT_TYPE.AUDIT_GROUP.value,false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.error(e.getMessage());
                            }
                        }
                    });
                }
            }
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "邀请成功", new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultModel;
    }

    /**
     * 退出群
     */
    public ResultModel<Object> quitGroup(final ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "退出群失败", new HashMap<>());
        try {
            final String groupId = reqGroup.getGroupId();
            final long uid = reqGroup.getUid();
            final Group group = groupMapper.getGroup(groupId);
            if (group == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            if(uid == group.getUid()){
                //群主退出时，调用解散群接口
                disbandGroup(reqGroup);
            }
            HashMap<String, Object> parmMap = new HashMap<>();
            parmMap.put("groupId", groupId);
            parmMap.put("uid", uid);
            GroupMember member = groupMemberMapper.getGroupMember(parmMap);
            if (member == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            groupMemberMapper.delGroupMember(parmMap);

            //删除群组内每个剩余的成员备注数组内删除的成员的备注
            HashMap<String, Object> parm = new HashMap<>();
            parm.put("groupId", groupId);
            parm.put("markedUid", uid);
            groupMemberMarkMapper.delGroupMemberMark(parm);

//            String contenet = reqGroup.getContent();
            //消息全群广播
            ThreadPool pool = ThreadPool.getInstance();
            pool.addTask(new Runnable() {
                @Override
                public void run() {
                    try {
                        //调用退出群组接口
                        User user = userMapper.getUserInfo(uid);
                        long senderImUserId = user.getImUserId();
                        String senderName = user.getNickName();
                        String content = senderName + "退出群组:" + group.getGroupName();
                        String json = HttpUtil.quitGroup(senderImUserId, groupId, content,reqGroup.getNickName(),group.getGroupName());
                        if (JSON.parseObject(json).getString("code").equals("1")) {
                            //失败重试一次
                            HttpUtil.quitGroup(senderImUserId, groupId, content,reqGroup.getNickName(),group.getGroupName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                    }
                }
            });
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "操作成功", reqGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultModel;
    }

    /**
     * 踢人
     */
    public ResultModel<Object> kickGroup(final ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "踢人失败", new HashMap<>());
        try {
            final String groupId = reqGroup.getGroupId();
            List<Member> members = reqGroup.getMembers();
            if (members == null || members.size() <= 0) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            final Group group = groupMapper.getGroup(groupId);
            if (group == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            long uid = reqGroup.getUid();
            HashMap<String, Object> parmMap = new HashMap<>();
            parmMap.put("groupId", groupId);
            parmMap.put("uid", uid);
            GroupMember sender = groupMemberMapper.getGroupMember(parmMap);
            if (sender == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            if (group.getUid() != sender.getUid()) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "无权限", new HashMap<>());
            }

            //删除群组内每个剩余的成员备注数组内删除的成员的备注
            HashMap<String, Object> parm = new HashMap<>();
            parm.put("groupId", groupId);
            for (Member member : members) {
                parmMap.put("uid", member.getUid());
                groupMemberMapper.delGroupMember(parmMap);
                parm.put("markedUid", member.getUid());
                groupMemberMarkMapper.delGroupMemberMark(parm);
            }
            log.info("members size=[{}]", members.size());
            if (members.size() > 1) {
                ThreadPool pool = ThreadPool.getInstance();
                final List<Long> imUserIdList = new ArrayList<>();
                String content = "";
                for(Member m : members){
                    User user = userMapper.getUserInfo(m.getUid());
                    imUserIdList.add(user.getImUserId());
                    content += m.getUserName();
                }
                final String finalContent = content +"被移除群组";
                pool.addTask(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            long createrImUserId = userMapper.getUserInfo(group.getUid()).getImUserId();
                            String json = HttpUtil.kickGroup(createrImUserId,groupId,imUserIdList, finalContent,reqGroup.getNickName(),group.getGroupName());
                            if (JSON.parseObject(json).getString("code").equals("1")) {
                                //失败重试一次
                                HttpUtil.kickGroup(createrImUserId, groupId, imUserIdList, finalContent,reqGroup.getNickName(),group.getGroupName());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "操作成功", reqGroup);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 修改群信息
     */
    public ResultModel<Object> editGroup(ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "修改失败", new HashMap<>());
        try {
            final String groupId = reqGroup.getGroupId();
            final Group group = groupMapper.getGroup(groupId);
            if (group == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            final long uid = reqGroup.getUid();
            HashMap<String, Object> parmMap = new HashMap<>();
            parmMap.put("groupId", groupId);
            parmMap.put("uid", uid);
            final GroupMember sender = groupMemberMapper.getGroupMember(parmMap);
            if (sender == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
//            if (sender.getUid() != group.getUid()) {
//                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "无权限", new HashMap<>());
//            }
            //修改群信息
            group.setGroupName(reqGroup.getGroupName());
            group.setGroupDesc(reqGroup.getDesc());
            group.setAvatar(reqGroup.getAvatar());
            //group.setqRCode(reqGroup.getqRCode());
            group.setCapacity(reqGroup.getCapacity());
            group.setIsAudit(reqGroup.getIsAudit());
            groupMapper.updateGroup(group);
            ThreadPool pool = ThreadPool.getInstance();
            pool.addTask(new Runnable() {
                @Override
                public void run() {
                    try {
//                        调用更新群组信息接口
//                        User creater = userMapper.getUserInfo(group.getUid());
//                        long createrImUserId = creater.getImUserId();
//                        String createrName = creater.getNickName();
//                        String json = HttpUtil.editGroup(createrImUserId,groupId,content,createrName,group.getGroupName(),group.getDesc());
//                        if (JSON.parseObject(json).getString("code").equals("1")) {
//                            //失败重试一次
//                            HttpUtil.editGroup(createrImUserId, groupId, content, createrName, group.getGroupName(), group.getDesc());
//                        }

                        //只需要发送群组消息，通知群组信息更新即可
                        User user = userMapper.getUserInfo(sender.getUid());
                        long senderImUserId = user.getImUserId();
                        String senderNickName = user.getNickName();
                        String content = senderNickName + "更新了群组信息";
                        String json = HttpUtil.sendGroupMsg(senderImUserId, senderNickName, false, groupId,group.getGroupName(), content,Constant.MSG_CONTENT_TYPE.UPDATE_GROUP.value);
                        if (JSON.parseObject(json).getString("code").equals("1")) {
                            //失败重试一次
                            HttpUtil.sendGroupMsg(senderImUserId, senderNickName, false, groupId, group.getGroupName(), content,Constant.MSG_CONTENT_TYPE.UPDATE_GROUP.value);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                    }
                }
            });
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "修改成功", reqGroup);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 解散群
     */
    public ResultModel<Object> disbandGroup(final ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "操作失败", new HashMap<>());
        try {
            final String groupId = reqGroup.getGroupId();
            final Group group = groupMapper.getGroup(groupId);
            if (group == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            long uid = reqGroup.getUid();
            HashMap<String, Object> parmMap = new HashMap<>();
            parmMap.put("groupId", groupId);
            parmMap.put("uid", uid);
            GroupMember sender = groupMemberMapper.getGroupMember(parmMap);
            if (sender == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            if (sender.getUid() != group.getUid()) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "无权限", new HashMap<>());
            }

            List<Long> memberIds = new ArrayList<>();
            HashMap<String, Object> parm = new HashMap<>();
            parm.put("groupId", groupId);
            parm.put("start", 0);
            parm.put("size", 200);
            List<GroupMember> groupMemberList = groupMemberMapper.listGroupMember(parm);
            for (GroupMember m : groupMemberList) {
                memberIds.add(m.getUid());
            }
            groupMapper.delGroup(groupId);
            groupMemberMapper.delAllGroupMember(groupId);
            groupMemberMarkMapper.delAllGroupMemberMark(groupId);
            log.info("memberIds size=[{}]", memberIds.size());
            if (memberIds.size() > 0) {
                //全群发消息通知
                ThreadPool pool = ThreadPool.getInstance();
                pool.addTask(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            long createrImUserId = userMapper.getUserInfo(group.getUid()).getImUserId();
                            String content = "群组解散";
                            String json = HttpUtil.disbandGroup(createrImUserId,groupId,content,reqGroup.getNickName(),group.getGroupName());
                            if (JSON.parseObject(json).getString("code").equals("1")) {
                                //失败重试一次
                                HttpUtil.disbandGroup(createrImUserId, groupId, content,reqGroup.getNickName(),group.getGroupName());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error(e.getMessage());
                        }
                    }
                });
            }
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "群组解散成功", reqGroup);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 审核群成员
     *
     * @param reqGroup
     * @return
     */
    public ResultModel<Object> auditMember(ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "审核失败", new HashMap<>());
        try {
            String groupId = reqGroup.getGroupId();
            List<Member> members = reqGroup.getMembers();
            if (members == null || members.size() <= 0) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            final Group group = groupMapper.getGroup(groupId);
            if (group == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            final long uid = reqGroup.getUid();
            String nickName = reqGroup.getNickName();
            HashMap<String, Object> parmMap = new HashMap<>();
            parmMap.put("groupId", groupId);
            parmMap.put("uid", uid);
            GroupMember sender = groupMemberMapper.getGroupMember(parmMap);
            if (sender == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            if (sender.getUid() != group.getUid()) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "无权限", new HashMap<>());
            }
            int status = reqGroup.getStatus();
            String content = "加入群组:"+group.getGroupName();
            ThreadPool pool = ThreadPool.getInstance();
            if(status == 1){
                //如果审核通过，调用api加入将该通过的成员加入群组
                try {
                    String json = HttpUtil.joinGroup(groupId, members,content,group.getGroupName(),nickName,uid);
                    if (JSON.parseObject(json).getString("code").equals("1")) {
                        //失败重试一次
                        HttpUtil.joinGroup(groupId, members,content,group.getGroupName(),nickName,uid);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            }else{
                content = content+"审核未通过";
            }
            User user = userMapper.getUserInfo(uid);
            final long senderImUserId = user.getImUserId();
            final String senderUserName = user.getUserName();
            for (final Member m : members) {
                HashMap<String, Object> parm = new HashMap<>();
                parm.put("groupId", groupId);
                parm.put("uid", m.getUid());
                GroupMember groupMember = groupMemberMapper.getGroupMember(parm);
                if (groupMember == null) {
                    continue;
                }
                if (status != groupMember.getStatus()) {
                    groupMember.setStatus(status);
                    groupMemberMapper.updateGroupMember(groupMember);
                }
                //发送个人消息，通知群创建者审核
                final String finalContent = content;
                pool.addTask(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            long receiverImUserId = userMapper.getUserInfo(m.getUid()).getImUserId();
                            String json = HttpUtil.sendSimpleMsg(senderImUserId, senderUserName, false, receiverImUserId, finalContent,Constant.MSG_CONTENT_TYPE.AUDIT_GROUP_RESULT.value,false);
                            if (JSON.parseObject(json).getString("code").equals("1")) {
                                //失败重试一次
                                HttpUtil.sendSimpleMsg(senderImUserId, senderUserName, false, receiverImUserId, finalContent,Constant.MSG_CONTENT_TYPE.AUDIT_GROUP_RESULT.value,false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error(e.getMessage());
                        }
                    }
                });
            }
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "操作成功", new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 获取群组信息
     */
    public ResultModel<Object> getGroup(ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "获取失败", new HashMap<>());
        try {
            String groupId = reqGroup.getGroupId();
            long uid = reqGroup.getUid();
            long time = reqGroup.getTime();
            Group group = groupMapper.getGroup(groupId);
            if (group == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            HashMap<String, Object> parmMap = new HashMap<>();
            parmMap.put("groupId", groupId);
            parmMap.put("uid", uid);
            GroupMember member = groupMemberMapper.getGroupMember(parmMap);
            if (member == null) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
            }
            RspGroup repGroup = GroupTools.group2Rsp(group);
//          repGroup.setInitSeq(member.getInitSeq());
//          repGroup.setReadSeq(member.getReadSeq());
            repGroup.setStickies(member.getStickies());
            repGroup.setIsShield(member.getIsShield());
            HashMap<String, Object> parm = new HashMap<>();
            parm.put("groupId", groupId);
            parm.put("start", 0);
            parm.put("size", 200);
            List<GroupMember> groupMemberList = groupMemberMapper.listGroupMember(parm);
            parm.put("uid",uid);
            List<GroupMemberMark> listMemberMark = groupMemberMarkMapper.listGroupMemberMark(parm);
            List<RspGroupMember> list = new ArrayList<>();
            for(GroupMember m : groupMemberList){
                RspGroupMember rspMember = GroupTools.member2Rsp(m);
                for(GroupMemberMark memberMark : listMemberMark){
                    if(m.getUid() == memberMark.getMarkedUid()){
                        rspMember.setMark(memberMark.getMark());
                        break;
                    }
                }
                list.add(rspMember);
            }
            repGroup.setMembers(list);
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "获取群组信息成功", repGroup);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 获取群组列表信息
     */
    public ResultModel<Object> listGroup(ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "获取失败", new HashMap<>());
        try {
            List<RspGroup> list = new ArrayList<RspGroup>();
            long uid = reqGroup.getUid();
            List<GroupMember> groupMemberList = groupMemberMapper.memberGroups(uid);
            for (GroupMember member : groupMemberList) {
                Group group = groupMapper.getGroup(member.getGroupId());
                RspGroup repGroup = GroupTools.group2Rsp(group);
                repGroup.setStickies(member.getStickies());
                repGroup.setIsShield(member.getIsShield());
                list.add(repGroup);
            }
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 修改群成员备注
     */
    public ResultModel<Object> editMemberMark(ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "修改失败", new HashMap<>());
        try {
            String groupId = reqGroup.getGroupId();
            long uid = reqGroup.getUid();
            long markId = reqGroup.getMarkedUid();
            String mark = reqGroup.getMark();
            HashMap<String, Object> parm = new HashMap<>();
            parm.put("groupId", groupId);
            parm.put("uid", uid);
            parm.put("markedUid", markId);
            GroupMemberMark memberMark = groupMemberMarkMapper.getGroupMemberMark(parm);
            if (memberMark == null) {
                memberMark = new GroupMemberMark();
                memberMark.setGroupId(groupId);
                memberMark.setUid(uid);
                memberMark.setMarkedUid(markId);
                memberMark.setMark(mark);
                memberMark.setCreateTime(System.currentTimeMillis());
                groupMemberMarkMapper.saveGroupMemberMark(memberMark);
            } else {
                if (!mark.equals(memberMark.getMark())) {
                    memberMark.setMark(mark);
                    groupMemberMarkMapper.updateGroupMemberMark(memberMark);
                }
            }
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "修改成功", reqGroup);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 获取群组成员
     *
     * @param reqGroup
     * @return
     */
    public ResultModel<Object> listGroupMember(ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "获取失败", new HashMap<>());
        try {
            List<RspGroupMember> rspMembers = new ArrayList<RspGroupMember>();
            List<RspMemberMark> rspMemberMarks = new ArrayList<>();
            String groupId = reqGroup.getGroupId();
            long uid = reqGroup.getUid();
            HashMap<String, Object> parm = new HashMap<>();
            parm.put("groupId", groupId);
            parm.put("start", 0);
            parm.put("size", 200);
            List<GroupMember> groupMemberList = groupMemberMapper.listGroupMember(parm);
            boolean isGroupMember = false;
            for (GroupMember member : groupMemberList) {
                if (member.getUid() == uid) {
                    isGroupMember = true;
                }
                RspGroupMember rspMember = GroupTools.member2Rsp(member);
                rspMembers.add(rspMember);
            }
            parm.put("uid",uid);
            List<GroupMemberMark> listMemberMark = groupMemberMarkMapper.listGroupMemberMark(parm);
            for(GroupMemberMark groupMemberMark : listMemberMark){
                RspMemberMark rspMemberMark = new RspMemberMark();
                rspMemberMark.setMarkedUid(groupMemberMark.getMarkedUid());
                rspMemberMark.setMark(groupMemberMark.getMark());
                rspMemberMarks.add(rspMemberMark);
            }
            if (!isGroupMember) {
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "无权限获取信息", new HashMap<>());
            }
            HashMap<String,Object> rsp = new HashMap<>();
            rsp.put("groupId",groupId);
            rsp.put("members",rspMembers);
            rsp.put("memberMarks",rspMemberMarks);
            resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "操作成功", rsp);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 设置屏蔽群消息
     */
    public ResultModel<Object> shieldGroup(ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "设置失败", new HashMap<>());
        try {
            String groupId = reqGroup.getGroupId();
            long uid = reqGroup.getUid();
            int isShield = reqGroup.getIsShield();
            HashMap<String, Object> parmMap = new HashMap<>();
            parmMap.put("groupId", groupId);
            parmMap.put("uid", uid);
            GroupMember groupMember = groupMemberMapper.getGroupMember(parmMap);
            if (groupMember != null) {
                if (groupMember.getIsShield() != isShield) {
                    groupMember.setIsShield(isShield);
                    groupMemberMapper.updateGroupMember(groupMember);
                }
                resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "设置成功", new HashMap<>());
            } else {
                resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "成员不存在", new HashMap<>());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

    /**
     * 设置群置顶
     */
    public ResultModel<Object> sickiesGroup(ReqGroup reqGroup) {
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "设置失败", new HashMap<>());
        try {
            String groupId = reqGroup.getGroupId();
            long uid = reqGroup.getUid();
            int stickies = reqGroup.getStickies();
            HashMap<String, Object> parmMap = new HashMap<>();
            parmMap.put("groupId", groupId);
            parmMap.put("uid", uid);
            GroupMember groupMember = groupMemberMapper.getGroupMember(parmMap);
            if (groupMember != null) {
                if (groupMember.getStickies() != stickies) {
                    groupMember.setStickies(stickies);
                    groupMemberMapper.updateGroupMember(groupMember);
                }
                resultModel = new ResultModel<Object>(ResultModel.RESULT_OK, "设置成功", new HashMap<>());
            } else {
                resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "成员不存在", new HashMap<>());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultModel;
    }

}

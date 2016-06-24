package cn.com.gome.api.service.impl;

import cn.com.gome.api.dao.GroupDao;
import cn.com.gome.api.global.Command;
import cn.com.gome.api.global.Constant;
import cn.com.gome.api.model.Group;
import cn.com.gome.api.model.GroupMsg;
import cn.com.gome.api.model.MsgProtocol;
import cn.com.gome.api.model.ResultModel;
import cn.com.gome.api.model.request.ReqMessage;
import cn.com.gome.api.service.MessageService;
import cn.com.gome.api.service.UserInfoService;
import cn.com.gome.api.threadPool.ThreadPool;
import cn.com.gome.api.worker.SendGroupMsgWorker;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by wangshikai on 2015/12/21.
 */
@Service
public class MessageServiceImpl implements MessageService {
    private static Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    //private final static GroupDao groupDao = new GroupDao();

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public ResultModel<Object> notifyMessage(ReqMessage reqMessage,String appId,boolean isPersist) {
        long senderId = reqMessage.getSenderId();
        boolean isValid = userInfoService.checkImUserId(appId,senderId);
        if(!isValid){
            log.error("senderId未注册,不合法,senderId:{}",senderId);
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误,senderId未注册,senderId="+senderId,new HashMap<>());
        }
        if (!checkMsg(reqMessage) || !StringUtils.isNotEmpty(appId)) {
            log.error("参数值错误,检查消息参数必填项!");
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数值错误!",new HashMap<>());
        }
        MsgProtocol msgProtocol = new MsgProtocol();
        GroupMsg groupMsg = new GroupMsg();
        msgProtocol.setAppId(appId);
        msgProtocol.setCmd(Command.CMD_IM_SEND_MSG);
        msgProtocol.setIsPersist(isPersist);
        groupMsg.setGroupType(reqMessage.getGroupType());
        groupMsg.setMsgId(cn.com.gome.api.utils.StringUtils.getUuid());
        int msgType = reqMessage.getMsgType();
        if(msgType == 0){
            msgType = Constant.GROUP_MSG_TYPE.TEXT.value;
        }
        groupMsg.setMsgType(msgType);//透传类型
        groupMsg.setSenderId(senderId);
        groupMsg.setSenderName(reqMessage.getSenderName());
        groupMsg.setSendTime(System.currentTimeMillis());
        groupMsg.setMsgBody(reqMessage.getMsgBody());
        groupMsg.setExtra(reqMessage.getExtra());
        String groupId = "";
        Group group = new Group();
        group.setName(reqMessage.getGroupName());
        if (reqMessage.getGroupType() == 1) {
            boolean containSelf = reqMessage.getSenderReceiveMsg();
            msgProtocol.setContainSelf(containSelf);
            groupId = reqMessage.getSenderId() + "_" + reqMessage.getReceiveId();
            if (reqMessage.getSenderId() > reqMessage.getReceiveId()) {
                groupId = reqMessage.getReceiveId() + "_" + reqMessage.getSenderId();
            }
            groupMsg.setGroupId(groupId);
            group.setType(Constant.CHAT_TYPE.E_CHAT_TYP_SINGLE.value);
        } else if (reqMessage.getGroupType() == 2) {
            groupId = reqMessage.getGroupId();
            groupMsg.setGroupId(groupId);
            groupMsg.setGroupName(reqMessage.getGroupName());
            group.setType(Constant.CHAT_TYPE.E_CHAT_TYP_GROUP.value);
        }
//        if(isPersist) {
//            Group oldGroup = groupDao.getGroupById(appId, groupId);
//            long time = System.currentTimeMillis();
//            if (oldGroup == null) {
//                group.setSeq(0L);
//                group.setIsDele(Constant.GROUP_DEL.E_GROUP_DEL_NOT.value);
//                group.setCreateTime(time);
//                group.setGroupId(groupId);
//                group.setUpdateTime(time);
//                groupDao.saveOrUpdateGroupById(appId, groupId, group);
//            }
//        }
        groupMsg.setSponserId(reqMessage.getSponserId());
        groupMsg.setSenderType(reqMessage.getSenderType());
        msgProtocol.setGroupMsg(groupMsg);
        //异步发送到MQ 转到IM服务
        SendGroupMsgWorker sendGroupMsgWorker = new SendGroupMsgWorker(msgProtocol);
        ThreadPool.getInstance().addTask(sendGroupMsgWorker);
        log.info("notifyMessage success,appId:{},senderId:{}", appId, senderId);
        return new ResultModel<Object>(ResultModel.RESULT_OK, "发送消息成功!",new HashMap<>());
    }

    @Override
    public ResultModel<Object> broadcastMessage(ReqMessage reqMessage,String appId) {
        long senderId = reqMessage.getSenderId();
        boolean isValid = userInfoService.checkImUserId(appId,senderId);
        if(!isValid){
            log.error("senderId未注册,不合法,senderId:{}",senderId);
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误,senderId未注册,senderId="+senderId,new HashMap<>());
        }
        if (reqMessage == null || reqMessage.getSenderId() < 0 || !StringUtils.isNotEmpty(reqMessage.getSenderName()) || !StringUtils.isNotEmpty(appId)) {
            log.info("参数值错误,检查消息参数必填项!");
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数值错误!",new HashMap<>());
        }
        MsgProtocol msgProtocol = new MsgProtocol();
        GroupMsg groupMsg = new GroupMsg();
        msgProtocol.setAppId(appId);
        msgProtocol.setCmd(Command.CMD_BROADCAST_IM_MSG);//全量广播类型
        msgProtocol.setIsPersist(reqMessage.getIsPersist());
        groupMsg.setGroupType(Constant.GROUP_TYPE.PERSON.value);
        groupMsg.setMsgId(cn.com.gome.api.utils.StringUtils.getUuid());
        int msgType = reqMessage.getMsgType();
        if(msgType == 0){
            msgType = Constant.GROUP_MSG_TYPE.TEXT.value;
        }
        groupMsg.setMsgType(msgType);//透传类型
        groupMsg.setSenderId(senderId);
        groupMsg.setSenderName(reqMessage.getSenderName());
        groupMsg.setSendTime(System.currentTimeMillis());
        groupMsg.setMsgBody(reqMessage.getMsgBody());
        groupMsg.setExtra(reqMessage.getExtra());
        groupMsg.setSponserId(reqMessage.getSponserId());
        groupMsg.setSenderType(reqMessage.getSenderType());
        msgProtocol.setGroupMsg(groupMsg);
        //异步发送到MQ 转到IM服务
        SendGroupMsgWorker sendGroupMsgWorker = new SendGroupMsgWorker(msgProtocol);
        ThreadPool.getInstance().addTask(sendGroupMsgWorker);
        log.info("broadcastMessage success,appId:{},senderId:{}", appId, senderId);
        return new ResultModel<Object>(ResultModel.RESULT_OK, "发送消息成功!",new HashMap());
    }

    public boolean checkMsg(ReqMessage reqMessage) {
        if(reqMessage == null){
            return false;
        }
        if (reqMessage.getSenderId() <= 0 || !StringUtils.isNotEmpty(reqMessage.getSenderName())) {
            return false;
        }
        if(reqMessage.getGroupType() != 1 && reqMessage.getGroupType() != 2 ){
            return false;
        }
        if(reqMessage.getGroupType() == 1){
            //单聊
            if(reqMessage.getReceiveId() <= 0){
                return false;
            }
        }
        if(reqMessage.getGroupType() == 2){
            //群聊
            if(!StringUtils.isNotEmpty(reqMessage.getGroupId()) || !StringUtils.isNotEmpty(reqMessage.getGroupName())){
                return false;
            }
        }
        return true;
    }
}

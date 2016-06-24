package com.gome.im.platform.service.impl;

import com.gome.im.platform.dao.GroupMemberDao;
import com.gome.im.platform.model.GroupMember;
import com.gome.im.platform.model.ResultModel;
import com.gome.im.platform.model.request.ReqBlockedMsg;
import com.gome.im.platform.service.BlockMsgService;
import com.gome.im.platform.utils.JedisClusterClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.HashMap;

/**
 * Created by wangshikai on 2016/6/12.
 */
@Service
public class BlockMsgServiceImpl implements BlockMsgService{
    private static Logger LOG = LoggerFactory.getLogger(BlockMsgServiceImpl.class);

    private GroupMemberDao groupMemberDao = new GroupMemberDao();
    @Override
    public ResultModel<Object> blockedMsg(String appId,long imUserId,String token,ReqBlockedMsg req) {
        if(req == null){
            LOG.error("blockedMsg,参数错误，参数为空,appId={},imUserId={}",appId,imUserId);
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误，参数为空", new HashMap<>());
        }
        if(!isValidParm(req)){
            LOG.error("blockedMsg,参数错误，参数不合法，请检查,appId={},imUserId={}",appId,imUserId);
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误，参数为空", new HashMap<>());
        }
        ResultModel<Object> resultModel = AddressServiceImpl.checkUser(appId, imUserId, token);
        if(resultModel != null){
            return resultModel;
        }
        String groupId = req.getGroupId();
        int isMsgBlocked = req.getIsMsgBlocked();
        try {
            //查询mongodb groupMember存在
            GroupMember member = groupMemberDao.getGroupMemberByUid(appId,groupId,imUserId);
            if(member == null){
                LOG.error("blockedMsg,设置消息免打扰的群组成员不存在,appId={},imUserId={},groupId={}",appId,imUserId,groupId);
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误,不存在该成员", new HashMap<>());
            }
            //设置mongodb和redis 屏蔽或取消屏蔽
            groupMemberDao.updateMsgBlocked(appId,groupId,imUserId,isMsgBlocked);
            JedisCluster jedisCluster = JedisClusterClient.INSTANCE.getJedisCluster();
            String key = appId + "_" + groupId +"_msgBlocked";
            if(isMsgBlocked == 1){
                //设置免打扰
                jedisCluster.hset(key, key + "_" + imUserId,String.valueOf(1));
            }else if(isMsgBlocked  == 0){
                //取消免打扰
                jedisCluster.hdel(key, key + "_" + imUserId);
            }
        } catch (Exception e) {
            LOG.error("blockedMsg error:appId={},imUserId={},groupId={},isMsgBlocked={},error:{}",appId,imUserId,groupId,isMsgBlocked,e);
        }
        LOG.info("blockedMsg success,appId={},imUserId={},groupId={},isMsgBlocked={}",appId,imUserId,groupId,isMsgBlocked);
        return new ResultModel<Object>(ResultModel.RESULT_OK, "设置成功", new HashMap<>());
    }

    public boolean isValidParm(ReqBlockedMsg req){
        String groupId = req.getGroupId();
        if(StringUtils.isEmpty(groupId)){
            return false;
        }
        int isMsgBlocked = req.getIsMsgBlocked();
        if(isMsgBlocked < 0 || isMsgBlocked > 1){
            return false;
        }
        return true;
    }
}

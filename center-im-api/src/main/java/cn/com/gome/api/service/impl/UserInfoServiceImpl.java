package cn.com.gome.api.service.impl;

import cn.com.gome.api.dao.IncrementIdDao;
import cn.com.gome.api.dao.UserInfoDao;
import cn.com.gome.api.global.Global;
import cn.com.gome.api.model.ReqUser;
import cn.com.gome.api.model.ResultModel;
import cn.com.gome.api.model.UserInfo;
import cn.com.gome.api.model.request.ReqUserInfo;
import cn.com.gome.api.model.response.RespUserInfo;
import cn.com.gome.api.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by wangshikai on 2015/12/21.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private final static UserInfoDao userInfoDao = new UserInfoDao();
    private final static IncrementIdDao incrementIdDao = new IncrementIdDao();

    @Override
    public ResultModel<Object> register(ReqUserInfo reqUserInfo,String appId,long tokenExpiresTime) {
        ReqUser user = reqUserInfo.getReqUser();
        if (user == null) {
            log.error("register,参数错误");
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
        }
        if(!StringUtils.isNotEmpty(appId)){
            log.error("register,参数错误");
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
        }
        if (!checkParms(user)) {
            log.error("register,参数错误");
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
        }
        String uid = user.getUid();
        UserInfo info = userInfoDao.getUserInfoByThirdUid(appId, uid);
        if(info != null){
            RespUserInfo respUserInfo = getRespUserInfo(info);
            log.info("register,已注册,appId:{},uid:{}", appId, uid);
            return new ResultModel<Object>(ResultModel.RESULT_OK, "已注册", respUserInfo);
        }
        long time = System.currentTimeMillis();
        UserInfo userInfo = new UserInfo();
        userInfo.setThirdUid(uid);
        String token = cn.com.gome.api.utils.StringUtils.getUuid();
        long tokenExpires = time + tokenExpiresTime;// Global.TOKEN_EXPIRES_TIME
        long imUserId = 0;
        try {
            int useUid = reqUserInfo.getUseUid();
            if(useUid == 0){
                log.info("register,使用我们生成的imUserId,useUid参数值为0,appId:{},useUid:{},uid:{}",appId,useUid,uid);
                imUserId = incrementIdDao.getId(appId);
            }else if(useUid == 1){
                log.info("register,appId:{},useUid:{},uid:{}",appId,useUid,uid);
                imUserId = Long.parseLong(uid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("register,error thirdUid not long!appId:{},thirdUid={}",appId,uid);
        }
        userInfo.setUid(imUserId);
        userInfo.setToken(token);
        userInfo.setTokenExpires(tokenExpires);
        userInfo.setCreateTime(time);
        userInfo.setUpdateTime(time);
        boolean success = false;
        try {
            success = userInfoDao.saveUserInfo(appId,userInfo);
        } catch (Exception e) {
            log.error("register error:{},appId:{},thirdUid:{}", e.getMessage(),appId,uid);
        }
        if (success) {
            RespUserInfo respUserInfo = getRespUserInfo(userInfo);
            log.info("register success,appId:{},uid:{}",appId,uid);
            return new ResultModel<Object>(ResultModel.RESULT_OK, "操作成功", respUserInfo);
        } else {
            log.info("register failed,appId:{},uid:{}",appId,uid);
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "操作失败", new HashMap<>());
        }
    }

    @Override
    public ResultModel<Object> updateUserInfo(ReqUserInfo reqUserInfo,String appId) {
        ReqUser user = reqUserInfo.getReqUser();
        if (user == null) {
            log.error("updateUserInfo,参数错误");
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
        }
//        String appId = reqUserInfo.getAppId();
        if(!StringUtils.isNotEmpty(appId)){
            log.error("updateUserInfo,参数错误");
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
        }
        if (!checkParms(user)) {
            log.error("updateUserInfo,参数错误");
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
        }
        String uid = user.getUid();
        UserInfo info = userInfoDao.getUserInfoByThirdUid(appId, uid);
        if(info == null){
            log.error("updateUserInfo,参数错误");
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误,用户未注册,thridUid="+uid, new HashMap<>());
        }
        long time = System.currentTimeMillis();
        boolean success = false;
        UserInfo userInfo = new UserInfo();
        try {
            userInfo.setThirdUid(uid);
            String token = cn.com.gome.api.utils.StringUtils.getUuid();
            long tokenExpires = time + Global.TOKEN_EXPIRES_TIME;
            userInfo.setToken(token);
            userInfo.setTokenExpires(tokenExpires);
            userInfo.setUpdateTime(time);
            success = userInfoDao.updateUserInfo(appId,userInfo);
        } catch (Exception e) {
            log.error("updateUserInfo error:{},appId:{},thirdUid:{}", e.getMessage(),appId,uid);
        }
        if (success) {
            RespUserInfo respUserInfo = getRespUserInfo(userInfo);
            log.info("updateUserInfo success,appId:{},thirdUid:{}",appId,uid);
            return new ResultModel<Object>(ResultModel.RESULT_OK, "操作成功", respUserInfo);
        } else {
            log.info("updateUserInfo failed,appId:{},thirdUid:{}",appId,uid);
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "操作失败", new HashMap<>());
        }
    }

    public boolean checkParms(ReqUser user) {
        if (user == null) {
            return false;
        }
        return StringUtils.isNotEmpty(user.getUid());
    }

    public RespUserInfo getRespUserInfo(UserInfo info) {
        RespUserInfo respUserInfo = new RespUserInfo();
        respUserInfo.setAppId(info.getAppId());
        respUserInfo.setUid(info.getThirdUid());
        respUserInfo.setImUserId(info.getUid());
        respUserInfo.setToken(info.getToken());
        respUserInfo.setTokenExpires(info.getTokenExpires());
        return respUserInfo;
    }

    @Override
    public boolean checkImUserId(String appId, long imUserId) {
        boolean valid = false;
        try {
            UserInfo userInfo = userInfoDao.getUserInfoByImUserId(appId, imUserId);
            if(userInfo != null){
                valid = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }

}

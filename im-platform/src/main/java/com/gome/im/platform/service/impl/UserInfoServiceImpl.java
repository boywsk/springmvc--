package com.gome.im.platform.service.impl;

import com.gome.im.platform.dao.UserInfoDao;
import com.gome.im.platform.global.Global;
import com.gome.im.platform.model.ResultModel;
import com.gome.im.platform.model.UserInfo;
import com.gome.im.platform.model.response.RspTokenInfo;
import com.gome.im.platform.service.UserInfoService;
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

    @Override
    public ResultModel<Object> getUserToken(long imUserId, String appId) {
        if (imUserId <= 0) {
            log.error("getUserToken,参数错误imUserId:{}",imUserId);
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
        }
        if (!StringUtils.isNotEmpty(appId)) {
            log.error("getUserToken,参数错误appId:{}",appId);
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
        }
        UserInfo info = userInfoDao.getUserInfoByImUserId(appId, imUserId);
        if (info == null) {
            log.error("getUserToken,操作失败，用户未注册,appId:{},imUserId:{}",appId,imUserId);
            return new ResultModel<Object>(ResultModel.USER_ERROR, "操作失败，用户未注册", new HashMap<>());
        }
        long time = System.currentTimeMillis();
        UserInfo userInfo = new UserInfo();
        String token = com.gome.im.platform.utils.StringUtils.getUuid();
        long tokenExpires = time + Global.TOKEN_EXPIRES_TIME;
        userInfo.setUid(imUserId);
        userInfo.setToken(token);
        userInfo.setTokenExpires(tokenExpires);
        userInfo.setUpdateTime(time);
        boolean success = false;
        try {
            success = userInfoDao.updateUserInfo(appId, userInfo);
        } catch (Exception e) {
            log.error("getUserToken error:{}, appId:{},imUserId:{}", e, appId, imUserId);
        }
        if (success) {
            RspTokenInfo tokenInfo = new RspTokenInfo();
            tokenInfo.setAppId(appId);
            tokenInfo.setImUserId(imUserId);
            tokenInfo.setToken(userInfo.getToken());
            tokenInfo.setTokenExpires(userInfo.getTokenExpires());
            log.info("getUserToken success,appId:{},imUserId:{},token:{}", appId, imUserId, userInfo.getToken());
            return new ResultModel<Object>(ResultModel.RESULT_OK, "操作成功", tokenInfo);
        } else {
            log.info("getUserToken failed,appId:{},imUserId:{}",appId,imUserId);
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "操作失败", new HashMap<>());
        }
    }

    @Override
    public ResultModel<Object> clearApnsToken(String appId, long imUserId, String token) {
        ResultModel<Object> resultModel = AddressServiceImpl.checkUser(appId, imUserId, token);
        if(resultModel != null){
            return resultModel;
        }
        try {
            userInfoDao.clearApnsToken(appId, imUserId);
            log.info("clearApnsToken success,appId:{},uid:{}",appId,imUserId);
            return new ResultModel<Object>(ResultModel.RESULT_OK, "清理成功", new HashMap<>());
        } catch (Exception e) {
            log.error("clearApnsToken error,appId:{},uid:{}",appId,imUserId);
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE,"清理失败", new HashMap<>());
        }
    }

}

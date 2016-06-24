package com.gome.im.platform.service;


import com.gome.im.platform.model.ResultModel;

/**
 * Created by wangshikai on 2015/12/21.
 */
public interface UserInfoService {

    /**
     *添加用户信息
     * @param imUserId
     * @return
     */
    ResultModel<Object> getUserToken(long imUserId,String appId);

    /**
     * 清理用户apnsToken
     * @param appId
     * @param imUserId
     * @param token
     * @return
     */
    ResultModel<Object> clearApnsToken(String appId,long imUserId,String token);


}

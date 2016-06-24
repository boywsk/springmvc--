package cn.com.gome.api.service;

import cn.com.gome.api.model.ResultModel;
import cn.com.gome.api.model.request.ReqUserInfo;

/**
 * Created by wangshikai on 2015/12/21.
 */
public interface UserInfoService {

    /**
     *添加用户信息
     * @param info
     * @return
     */
    ResultModel<Object> register(ReqUserInfo info,String appId,long tokenExpiresTime);

    /**
     *更新用户信息
     * @param info
     * @return
     */
    ResultModel<Object> updateUserInfo(ReqUserInfo info,String appId);

    /**
     * 检查操作用户  imUserId 的合法性
     * @param appId
     * @param imUserId
     * @return
     */
    public boolean checkImUserId(String appId,long imUserId);

}

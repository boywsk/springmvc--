package com.gome.im.platform.service;


import com.gome.im.platform.model.ResultModel;

/**
 * Created by wangshikai on 2015/12/21.
 */
public interface AddressService {

    /**
     *用户获取服务器地址列表
     * @param imUserId
     * @return
     */
    ResultModel<Object> getAddress(long imUserId,String token,String appId);


}

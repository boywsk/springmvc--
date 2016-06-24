package com.gome.im.platform.service;


import com.gome.im.platform.model.ResultModel;
import com.gome.im.platform.model.request.ReqBlockedMsg;

/**
 * Created by wangshikai on 2015/6/12.
 */
public interface BlockMsgService {

    /**
     *添加用户信息
     * @param req
     * @return
     */
    ResultModel<Object> blockedMsg(String appId,long imUserId,String token,ReqBlockedMsg req);


}

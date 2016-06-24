package cn.com.gome.api.service;

import cn.com.gome.api.model.ResultModel;
import cn.com.gome.api.model.request.ReqMessage;

/**
 * Created by wangshikai on 2015/12/21.
 */
public interface MessageService {
    /**
     *发送BS通知消息接口
     * @param reqMessage
     * @return
     */
    ResultModel<Object> notifyMessage(ReqMessage reqMessage,String appId,boolean isPersisit);

    /**
     *发送全量广播消息接口
     * @param reqMessage
     * @return
     */
    ResultModel<Object> broadcastMessage(ReqMessage reqMessage,String appId);
}

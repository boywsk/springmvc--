package cn.com.gome.api.worker;

import cn.com.gome.api.model.MsgProtocol;
import cn.com.gome.api.utils.MQSender;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发送群组消息
 */
public class SendGroupMsgWorker implements Runnable {
    Logger log = LoggerFactory.getLogger(SendGroupMsgWorker.class);
    private MsgProtocol msg;

    public SendGroupMsgWorker(MsgProtocol msg) {
        this.msg = msg;
    }

    public void run() {
        try{
            String msgJson = JSON.toJSONString(msg);
            MQSender.getInstance().sendMsg(msgJson);
        }catch (Exception e){
            log.error("error:"+e+"\n\t msg:"+ msg);
        }
    }

}

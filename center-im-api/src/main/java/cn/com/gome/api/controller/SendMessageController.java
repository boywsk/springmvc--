package cn.com.gome.api.controller;

import cn.com.gome.api.model.ResultModel;
import cn.com.gome.api.model.request.ReqMessage;
import cn.com.gome.api.service.MessageService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangshikai on 2015/11/26.
 */
@Controller
@RequestMapping("message")
public class SendMessageController {
    private static Logger log = LoggerFactory.getLogger(SendMessageController.class);

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "notifyMessage", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> notifyMessage(@RequestBody ReqMessage reqMessage,@RequestParam String appId) {
        log.info("notifyMessage:"+"\tparms:"+ JSON.toJSONString(reqMessage));
        ResultModel<Object> result = null;
        try {
            result = messageService.notifyMessage(reqMessage,appId,true);
        } catch (Exception e) {
            log.error("SendMessageController method=notifyMessage error:" + e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "notifyCommandMsg", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> notifyCommandMsg(@RequestBody ReqMessage reqMessage,@RequestParam String appId) {
        log.info("notifyCommandMsg:"+"\tparms:"+ JSON.toJSONString(reqMessage));
        ResultModel<Object> result = null;
        try {
            result = messageService.notifyMessage(reqMessage,appId,false);
        } catch (Exception e) {
            log.error("SendMessageController method=notifyCommandMsg error:" + e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "broadcastMessage", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> broadcastMessage(@RequestBody ReqMessage reqMessage,@RequestParam String appId) {
        log.info("broadcastMessage:"+"\tparms:"+ JSON.toJSONString(reqMessage));
        ResultModel<Object> result = null;
        try {
            result = messageService.broadcastMessage(reqMessage,appId);
        } catch (Exception e) {
            log.error("SendMessageController method=broadcastMessage error:" + e.getMessage());
        }
        return result;
    }
}

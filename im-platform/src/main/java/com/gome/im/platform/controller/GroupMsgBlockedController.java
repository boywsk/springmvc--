package com.gome.im.platform.controller;

import com.alibaba.fastjson.JSON;
import com.gome.im.platform.model.ResultModel;
import com.gome.im.platform.model.request.ReqBlockedMsg;
import com.gome.im.platform.service.BlockMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 设置消息免打扰接口
 * Created by wangshikai on 2016/6/12.
 */
@Controller
@RequestMapping("blocked")
public class GroupMsgBlockedController {
    private Logger log = LoggerFactory.getLogger(GroupMsgBlockedController.class);

    @Autowired
    private BlockMsgService blockMsgService;

    @RequestMapping(value = "msgBlocked", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> msgBlocked(@RequestBody ReqBlockedMsg req,@RequestParam String appId,@RequestParam long imUserId,@RequestParam String token) {
        log.info("msgBlocked parms: appId={},imUserId={},token={},parm={}", appId,imUserId,token,JSON.toJSONString(req));
        return blockMsgService.blockedMsg(appId,imUserId,token,req);
    }

}

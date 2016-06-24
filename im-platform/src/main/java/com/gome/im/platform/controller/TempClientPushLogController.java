package com.gome.im.platform.controller;

import com.alibaba.fastjson.JSON;
import com.gome.im.platform.model.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by wangshikai on 2016/6/1.
 */
@Controller
@RequestMapping("pushLog")
public class TempClientPushLogController {
    private Logger log = LoggerFactory.getLogger(TempClientPushLogController.class);

    @RequestMapping(value = "clientLog", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> pushLog(@RequestBody Object logObject,@RequestParam String appId,@RequestParam long imUserId) {
        log.info("clientPushLog: appId={}, imUserId={},logContent={}", appId, imUserId, JSON.toJSONString(logObject));
        return new ResultModel<Object>(0,"操作成功",new HashMap<>());
    }

}

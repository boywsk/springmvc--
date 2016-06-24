package com.gomeplus.im.api.controller;

import com.gomeplus.im.api.global.Global;
import com.gomeplus.im.api.request.response.ResultModel;
import com.gomeplus.im.api.service.CreateDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangshikai on 2016/6/23.
 */
@Controller
@RequestMapping("applyApp")
public class CreateDatabaseController {
    private static final Logger logger = LoggerFactory.getLogger(CreateDatabaseController.class);

    @Autowired
    private CreateDatabaseService createDatabaseService;

    @RequestMapping(value = "createDatabase", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel<Object> createDatabase(@RequestParam long appId) {
        logger.info("testCreateDatabase appId:{}",appId);
        Map<String,Object> parm = new HashMap<>();
        parm.put("dbName", Global.DATABASE_PREFIX + appId);
        return createDatabaseService.createDatabase(parm);
    }

}

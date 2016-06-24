package com.gome.im.api.controller;

import com.alibaba.fastjson.JSON;
import com.gome.im.api.model.ResultModel;
import com.gome.im.api.model.request.ReqFriend;
import com.gome.im.api.service.FriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangshikai on 2016/2/29.
 */
@Controller
@RequestMapping("friend")
public class FriendController {
    Logger log = LoggerFactory.getLogger(FriendController.class);
    @Autowired
    private FriendService friendService;

    /**
     * 申请添加好友
     * @param reqFriend
     */
    @RequestMapping(value = "addFriend", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> addFriend(@RequestBody ReqFriend reqFriend) {
        log.info("addFriend");
        log.info("parms:"+ JSON.toJSONString(reqFriend));
        return friendService.addFriend(reqFriend);
    }

    /**
     * 好友申请审核
     * @param reqFriend
     */
    @RequestMapping(value = "auditFriend", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> auditFriend(@RequestBody ReqFriend reqFriend) {
        log.info("auditFriend");
        log.info("parms:"+ JSON.toJSONString(reqFriend));
        return friendService.auditFriend(reqFriend);
    }

    /**
     * 删除好友关系
     * @param reqFriend
     */
    @RequestMapping(value = "delFriend", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> delFriend(@RequestBody ReqFriend reqFriend) {
        log.info("delFriend");
        log.info("parms:"+ JSON.toJSONString(reqFriend));
        return friendService.delFriend(reqFriend);
    }

    /**
     * 修改好友备注
     * @param reqFriend
     */
    @RequestMapping(value = "editMark", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> editMark(@RequestBody ReqFriend reqFriend) {
        log.info("setMark");
        log.info("parms:"+ JSON.toJSONString(reqFriend));
        return friendService.setMark(reqFriend);
    }

    /**
     * 获取好友关系列表
     * @param reqFriend
     */
    @RequestMapping(value = "listFriend", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> listFriend(@RequestBody ReqFriend reqFriend) {
        log.info("listFriend");
        log.info("parms:"+ JSON.toJSONString(reqFriend));
        return friendService.listFriend(reqFriend);
    }
}

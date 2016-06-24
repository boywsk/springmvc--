package com.gome.im.api.controller;

import com.alibaba.fastjson.JSON;
import com.gome.im.api.model.ResultModel;
import com.gome.im.api.model.request.ReqFriendGroup;
import com.gome.im.api.service.FriendGroupService;
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
@RequestMapping("friendGroup")
public class FriendGroupController {
    Logger log = LoggerFactory.getLogger(FriendGroupController.class);
    @Autowired
    private FriendGroupService friendGroupService;

    /**
     * 发表朋友圈说说
     * @param reqFriendGroup
     */
    @RequestMapping(value = "addFriendGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> addFriendGroup(@RequestBody ReqFriendGroup reqFriendGroup) {
        log.info("addFriendGroup:"+ JSON.toJSONString(reqFriendGroup));
        return friendGroupService.addFriendGroup(reqFriendGroup);
    }

    /**
     * 删除朋友圈说说
     * @param reqFriendGroup
     */
    @RequestMapping(value = "delFriendGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> delFriendGroup(@RequestBody ReqFriendGroup reqFriendGroup) {
        log.info("delFriendGroup:"+ JSON.toJSONString(reqFriendGroup));
        return friendGroupService.delFriendGroup(reqFriendGroup);
    }

    /**
     * 查看个人自己发布过的说说历史
     * @param reqFriendGroup
     */
    @RequestMapping(value = "personalFriendGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> personalFriendGroup(@RequestBody ReqFriendGroup reqFriendGroup) {
        log.info("personalFriendGroup:"+ JSON.toJSONString(reqFriendGroup));
        return friendGroupService.personalFriendGroup(reqFriendGroup);
    }

    /**
     * 查看所有好友发布过的朋友圈
     * @param reqFriendGroup
     */
    @RequestMapping(value = "listFriendGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> listFriendGroup(@RequestBody ReqFriendGroup reqFriendGroup) {
        log.info("listFriendGroup:"+ JSON.toJSONString(reqFriendGroup));
        return friendGroupService.listFriendGroup(reqFriendGroup);
    }

}

package com.gomeplus.im.api.controller;

import com.alibaba.fastjson.JSON;
import com.gomeplus.im.api.request.ReqGroup;
import com.gomeplus.im.api.request.ReqGroupMember;
import com.gomeplus.im.api.request.response.ResultModel;
import com.gomeplus.im.api.service.GroupService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by wangshikai on 2016/2/29.
 */
@Controller
@RequestMapping("group")
public class GroupController {
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Autowired
    private GroupService groupService;

    /**
     * 创建群
     * @param reqGroup
     */
    @RequestMapping(value = "addGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> addGroup(@RequestBody ReqGroup reqGroup,@RequestParam String appId,@RequestParam long userId) {
        logger.info("addGroup");
        logger.info("parms:"+JSON.toJSONString(reqGroup));
        ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE, "创建失败", new HashMap<>());
        try {
            resultModel = groupService.addGroup(reqGroup,appId,userId);
            return resultModel;
        }catch (Exception e){
            logger.error("创建群失败异常",e);
        }
        return resultModel;
    }

    /**
     * 用户主动加入到群组
     * @param reqGroupMember
     */
    @RequestMapping(value = "joinGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> joinGroup(@RequestBody ReqGroupMember reqGroupMember,@RequestParam String appId,@RequestParam long userId) {
        logger.info("joinGroup");
        logger.info("parms:"+JSON.toJSONString(reqGroupMember));
        return groupService.joinGroup(reqGroupMember,appId,userId);
    }

    /**
     * 邀请用户加入到群组
     * @param reqGroup
     */
    @RequestMapping(value = "inviteGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> inviteGroup(@RequestBody ReqGroup reqGroup,@RequestParam String appId,@RequestParam long userId) {
        logger.info("inviteGroup");
        logger.info("parms:"+JSON.toJSONString(reqGroup));
        return groupService.inviteGroup(reqGroup,appId,userId);
    }



    /**
     * 退出群
     * @param reqGroup
     */
    @RequestMapping(value = "quitGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> quitGroup(@RequestBody ReqGroup reqGroup,@RequestParam String appId,@RequestParam long userId) {
        logger.info("quitGroup");
        logger.info("parms:"+JSON.toJSONString(reqGroup));
        return groupService.quitGroup(reqGroup,appId,userId);
    }

    /**
     * 踢人
     * @param reqGroup
     */
    @RequestMapping(value = "kickGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> kickGroup(@RequestBody ReqGroup reqGroup,@RequestParam String appId,@RequestParam long userId) {
        logger.info("kickGroup");
        logger.info("parms:"+JSON.toJSONString(reqGroup));
        return groupService.kickGroup(reqGroup,appId,userId);
    }

    /**
     * 修改群信息
     * @param reqGroup
     */
    @RequestMapping(value = "editGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> editGroup(@RequestBody ReqGroup reqGroup,@RequestParam String appId,@RequestParam long userId) {
        logger.info("editGroup");
        logger.info("parms:"+JSON.toJSONString(reqGroup));
        return groupService.editGroup(reqGroup,appId,userId);
    }

    /**
     * 解散群
     * @param reqGroup
     */
    @RequestMapping(value = "disbandGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> disbandGroup(@RequestBody ReqGroup reqGroup,@RequestParam String appId,@RequestParam long userId) {
        logger.info("disbandGroup");
        logger.info("parms:"+JSON.toJSONString(reqGroup));
        return groupService.disbandGroup(reqGroup,appId,userId);
    }

    /**
     * 审核群成员
     * @param reqGroup
     */
    @RequestMapping(value = "auditMember", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> auditMember(@RequestBody ReqGroup reqGroup,@RequestParam String appId,@RequestParam long userId) {
        logger.info("auditMember");
        logger.info("parms:"+JSON.toJSONString(reqGroup));
        return groupService.auditMember(reqGroup,appId,userId);
    }

    /**
     * 获取群组信息
     * @param reqGroup
     */
    @RequestMapping(value = "getGroupInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> getGroupInfo(@RequestBody ReqGroup reqGroup,@RequestParam String appId,@RequestParam long userId) {
        logger.info("getGroup");
        logger.info("getGroupInfo:"+JSON.toJSONString(reqGroup));
        return groupService.getGroupInfo(reqGroup,appId,userId);
    }
    /**
     * 获取群组信息
     * @param reqGroup
     */
    @RequestMapping(value = "getGroupMembers", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> getGroupMemebers(@RequestBody ReqGroup reqGroup,@RequestParam String appId,@RequestParam long userId) {
    	logger.info("getGroup");
    	logger.info("parms:"+JSON.toJSONString(reqGroup));
    	return groupService.getGroupMembers(reqGroup,appId,userId);
    }

    /**
     * 获取群组列表信息
     * @param reqGroup
     */
    @RequestMapping(value = "listGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> listGroup(@RequestParam String appId,@RequestParam long userId) {
        logger.info("listGroup");
        logger.info("parms:"+JSON.toJSONString(appId));
        return groupService.listGroup(appId,userId);
    }

    /**
     * 设置、修改群备注
     * @param reqGroup
     */
    @RequestMapping(value = "editMemberMark", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> editMemberMark(@RequestBody ReqGroup reqGroup,@RequestParam String appId,@RequestParam long userId) {
        logger.info("editpMemberMark");
        logger.info("parms:"+JSON.toJSONString(reqGroup));
        return groupService.editMemberMark(reqGroup,appId,userId);
    }

    /**
     * 获取群组列表信息
     * @param reqGroup
     */
    @RequestMapping(value = "listGroupMember", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> listGroupMember(@RequestBody ReqGroup reqGroup,@RequestParam String appId,@RequestParam long userId) {
        logger.info("listGroupMember");
        logger.info("parms:"+JSON.toJSONString(reqGroup));
        return groupService.listGroupMember(reqGroup,appId,userId);
    }

    /**
     * 设置屏蔽群消息
     * @param reqGroup
     */
    @RequestMapping(value = "shieldGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> shieldGroup(@RequestBody ReqGroup reqGroup,@RequestParam String appId,@RequestParam long userId) {
        logger.info("shieldGroup");
        logger.info("parms:"+JSON.toJSONString(reqGroup));
        return groupService.shieldGroup(reqGroup,appId,userId);
    }

    /**
     * 设置群置顶
     * @param reqGroup
     */
    @RequestMapping(value = "sickiesGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> sickiesGroup(@RequestBody ReqGroup reqGroup,@RequestParam String appId,@RequestParam long userId) {
        logger.info("sickiesGroup");
        logger.info("parms:"+JSON.toJSONString(reqGroup));
        return groupService.sickiesGroup(reqGroup,appId,userId);
    }
}

package com.gomeplus.im.api.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gomeplus.im.api.model.Friend;
import com.gomeplus.im.api.request.ReqFriend;
import com.gomeplus.im.api.request.response.ResultModel;
import com.gomeplus.im.api.service.FriendService;

@Controller
@RequestMapping("friend")
public class FriendController {
	private static final Logger logger = LoggerFactory.getLogger(FriendController.class);
	
	@Autowired
	private FriendService friendService;
	
	/**
	 * 申请添加好友
	 * 
	 * @param reqFriend
	 * @return
	 */
	@RequestMapping(value = "applyAddFriend", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> applayAddFriend(@RequestBody ReqFriend reqFriend,@RequestParam String appId,@RequestParam long userId) {
		logger.info("addFriend,parms:{},appId:{}" + JSON.toJSONString(reqFriend),appId);
		return friendService.applyAddFriend(reqFriend,appId,userId);
	}

	/**
	 * 好友申请审核
	 * 
	 * @param reqFriend
	 */
	@RequestMapping(value = "auditFriend", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> auditFriend(@RequestBody ReqFriend reqFriend,@RequestParam String appId,@RequestParam long userId) {
		logger.info("auditFriend,parms:" + JSON.toJSONString(reqFriend));
		try {
			return friendService.auditFriend(reqFriend,appId,userId);
		} catch (Exception e) {
			logger.error("好友申请审核失败");
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE,"好友申请审核失败", new HashMap<>());
		}
	}
	
	/**
	 * 修改好友备注
	 * 
	 * @param reqFriend
	 */
	@RequestMapping(value = "editMark", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> editMark(@RequestBody ReqFriend reqFriend,@RequestParam String appId,@RequestParam long userId) {
		logger.info("editMark,parms:" + JSON.toJSONString(reqFriend));
		return friendService.updateMark(reqFriend,appId,userId);
	}

	
	/**
	 * 删除好友关系
	 * 
	 * @param reqFriend
	 */
	@RequestMapping(value = "deleteFriend", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> deleteFriend(@RequestBody ReqFriend reqFriend,@RequestParam String appId,@RequestParam long userId) {
		logger.info("delFriend,parms:" + JSON.toJSONString(reqFriend));
		try {
			return friendService.deleteFriend(reqFriend,appId,userId);
		} catch (Exception e) {
			logger.error("删除好友失败");
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE,"删除好友失败", new HashMap<>());
		}
	}

	
	/**
	 * 分页获取好友关系列表
	 * 
	 * @param reqFriend
	 */
	@RequestMapping(value = "listFriends", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> listFriends(@RequestBody(required=false) ReqFriend reqFriend,@RequestParam String appId,@RequestParam long userId) {
		logger.info("listFriend,parms:" + JSON.toJSONString(appId));
		return friendService.listFriends(reqFriend,appId,userId);
	}
	
	/**
	 * 获取某个组好友关系列表
	 * 
	 * @param reqFriend
	 */
	@RequestMapping(value = "getGroupFriends", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> getGroupFriends(@RequestBody ReqFriend reqFriend,@RequestParam String appId,@RequestParam long userId) {
		logger.info("listFriend,parms:" + JSON.toJSONString(reqFriend));
		return friendService.getFriendsByGroupId(reqFriend,appId,userId);
	}
	/**
	 * 获取所有好友关系列表
	 * 
	 * @param reqFriend
	 */
	@RequestMapping(value = "getAllFriends", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> getAllFriends(@RequestParam String appId,long userId) {
		logger.info("listFriend,parms:" + JSON.toJSONString(appId));
		return friendService.getAllFriends(appId,userId);
	}
	
	
	/**
	 * 更新好友分组
	 * @param reqFriend
	 */
	@RequestMapping(value = "updateFriendGroup", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> updateFriendGroup(@RequestBody ReqFriend reqFriend,@RequestParam String appId,@RequestParam long userId) {
		logger.info("listFriend,parms:" + JSON.toJSONString(reqFriend));
		return friendService.updateFriendGroup(reqFriend,appId,userId);
	}
	
	
	
	/**
	 * 更新用户信息测试
	 * @param reqFriend
	 */
	@RequestMapping(value = "updateFriend", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> updateFriend(@RequestBody Friend friend,@RequestParam String appId) {
		logger.info("listFriend,parms:" + JSON.toJSONString(friend));
		return friendService.updateFriend(friend,appId);
	}
	
	
	
	
}

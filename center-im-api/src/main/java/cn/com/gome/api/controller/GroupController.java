package cn.com.gome.api.controller;

import cn.com.gome.api.model.ResultModel;
import cn.com.gome.api.model.request.ReqGroup;
import cn.com.gome.api.service.GroupService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("group")
public class GroupController {
	Logger log = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	private GroupService groupService;

	/**
	 * 创建群
	 * @param reqGroup
	 */
	@RequestMapping(value = "addGroup", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> addGroup(@RequestBody ReqGroup reqGroup,@RequestParam String appId) {
		log.info("addGroup , parms:"+JSON.toJSONString(reqGroup));
		ResultModel<Object> result = null;
		try {
			if(!checkReqGroupParms(reqGroup)){
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误,请检查",new HashMap<>());
			}
			result = groupService.addGroup(reqGroup,appId);
		} catch (Exception e) {
			log.error("GroupController method=addGroup error:" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 用户加入群
	 * @param reqGroup
	 */
	@RequestMapping(value = "joinGroup", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> joinGroup(@RequestBody ReqGroup reqGroup,@RequestParam String appId) {
		log.info("joinGroup , parms:"+JSON.toJSONString(reqGroup));
		ResultModel<Object> result = null;
		try {
			if(!checkReqGroupParms(reqGroup)){
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误,请检查",new HashMap<>());
			}
			result = groupService.joinGroup(reqGroup,appId);
		} catch (Exception e) {
			log.error("GroupController method=joinGroup error:" + e.getMessage());
		}
		return result;
	}

	
	/**
	 * 用户退出群
	 * @param reqGroup
	 */
	@RequestMapping(value = "quitGroup", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> quitGroup(@RequestBody ReqGroup reqGroup,@RequestParam String appId) {
		log.info("quitGroup , parms:"+JSON.toJSONString(reqGroup));
		ResultModel<Object> result = null;
		try {
			if(!checkReqGroupParms(reqGroup)){
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误,请检查",new HashMap<>());
			}
			result = groupService.quitGroup(reqGroup,appId);
		} catch (Exception e) {
			log.error("GroupController method=quitGroup error:" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 群主踢人
	 * @param reqGroup
	 */
	@RequestMapping(value = "kickGroup", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> kickGroup(@RequestBody ReqGroup reqGroup,@RequestParam String appId) {
		log.info("kickGroup , parms:"+JSON.toJSONString(reqGroup));
		ResultModel<Object> result = null;
		try {
			if(!checkReqGroupParms(reqGroup)){
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误,请检查",new HashMap<>());
			}
			result = groupService.kickGroup(reqGroup,appId);
		} catch (Exception e) {
			log.error("GroupController method=kickGroup error:" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 解散群
	 * @param reqGroup
	 */
	@RequestMapping(value = "disbandGroup", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> disbandGroup(@RequestBody ReqGroup reqGroup,@RequestParam String appId) {
		log.info("disbandGroup , parms:"+JSON.toJSONString(reqGroup));
		ResultModel<Object> result = null;
		try {
			if(!checkReqGroupParms(reqGroup)){
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误,请检查",new HashMap<>());
			}
			result = groupService.disbandGroup(reqGroup,appId);
		} catch (Exception e) {
			log.error("GroupController method=disbandGroup error:" + e.getMessage());
		}
		return result;
	}

	private boolean checkReqGroupParms(ReqGroup reqGroup){
		if(reqGroup == null){
			return false;
		}
		if(!StringUtils.isNotEmpty(reqGroup.getGroupId())){
			return false;
		}
		return true;
	}
}

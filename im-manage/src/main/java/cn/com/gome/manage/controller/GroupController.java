package cn.com.gome.manage.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.com.gome.manage.mongodb.model.AppSearchModel;
import cn.com.gome.manage.mongodb.model.Group;
import cn.com.gome.manage.mongodb.model.GroupMember;
import cn.com.gome.manage.mongodb.service.GroupService;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pageSupport.PageInfoFactory;
import cn.com.gome.manage.pojo.AppInfo;
import cn.com.gome.manage.pojo.User;
import cn.com.gome.manage.service.AppService;
import cn.com.gome.manage.utils.Constant;
import cn.com.gome.manage.utils.HttpUtil;
import cn.com.gome.manage.utils.ParamUtil;

@Controller
@RequestMapping("/group")
public class GroupController {
	@Autowired
	private AppService appService;
	private GroupService groupService = new GroupService();
	
	@RequestMapping(value="group", method = RequestMethod.GET)
	public ModelAndView group(HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView("group/group");		
		User user = (User) request.getSession().getAttribute("loginInfo");
		modelAndView.addObject("type", user.getType());
		if(user.getType().equals(Constant.USER_TYPE.E_USER_TYPE_APP.value)){
			AppSearchModel appSearchModel = new AppSearchModel();
			appSearchModel.setUserId(Long.toString(user.getId()));
			appSearchModel.setType(user.getType());
			PageInfo pageInfo = PageInfoFactory.getPageInfo(10, 1);
			List<AppInfo> appInfo = appService.getAppInfo(pageInfo, appSearchModel);
			//List<AppInfo> appInfo = (List<AppInfo>) request.getSession().getAttribute("appIdList");
			modelAndView.addObject("appIdList", appInfo);
		}
		return modelAndView;
	}
	
	/**
     * 获取所有群组信息--Cb-whf-20160219
     * 默认显示：isDele为0，即未删除群组，已删除或未有标识群组不显示
     * @return
     */
	@RequestMapping(value="allGroupList", method = RequestMethod.GET)
	public void allGroupList(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("group/group");
		User user = (User) request.getSession().getAttribute("loginInfo");
		modelAndView.addObject("type", user.getType());
		int pageNo =  ParamUtil.getIntParams(request, "page", 1);
		int pageSize =  ParamUtil.getIntParams(request, "pagesize", 10);
		String appId = ParamUtil.getParams(request, "appId", "");
		String startTime = ParamUtil.getParams(request, "startTime", "");
		String endTime = ParamUtil.getParams(request, "endTime", "");
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		if(user.getType().equals(Constant.USER_TYPE.E_USER_TYPE_APP.value)){
			AppSearchModel appSearchModel = new AppSearchModel();
			appSearchModel.setUserId(Long.toString(user.getId()));
			appSearchModel.setType(user.getType());
			PageInfo pageInfoForApp = PageInfoFactory.getPageInfo(10, 1);
			List<AppInfo> appInfo = appService.getAppInfo(pageInfoForApp, appSearchModel);
			//List<AppInfo> appInfo = (List<AppInfo>) request.getSession().getAttribute("appIdList");
			modelAndView.addObject("appIdList", appInfo);
		}
		List<Group> list = groupService.getAllGroupList(appId,startTime,endTime,pageInfo);
		if(list != null){
			//格式化群组创建时间
			for(Group group : list){
				long createTime = group.getCreateTime();
				long updateTime = group.getUpdateTime();
				try{
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String date = formatter.format(new Date(createTime));
					group.setFormateCreateTime(date);
					group.setFormateUpdateTime(formatter.format(updateTime));
				}catch (Exception e){
					group.setFormateCreateTime(String.valueOf(createTime));
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Rows", list);
			map.put("Total",pageInfo.getTotalResult()); //数据总数
			HttpUtil.writeResult(response, map);
		}
		
	}

	@RequestMapping(value="groupList", method = RequestMethod.GET)
	public ModelAndView groupList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("group/group");
		User user = (User) request.getSession().getAttribute("loginInfo");
		String userId = ParamUtil.getParams(request, "userId", "");
		String appId = ParamUtil.getParams(request, "appId", "");
		int pageNo =  ParamUtil.getIntParams(request, "page", 1);
		int pageSize =  ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);		
		if(user.getType().equals(Constant.USER_TYPE.E_USER_TYPE_APP.value)){
			AppSearchModel appSearchModel = new AppSearchModel();
			appSearchModel.setUserId(Long.toString(user.getId()));
			appSearchModel.setType(user.getType());
			PageInfo pageInfoForApp = PageInfoFactory.getPageInfo(10, 1);
			List<AppInfo> appInfo = appService.getAppInfo(pageInfoForApp, appSearchModel);
			//List<AppInfo> appInfo = (List<AppInfo>) request.getSession().getAttribute("appIdList");
			modelAndView.addObject("appIdList", appInfo);
		}
		modelAndView.addObject("type", user.getType());
		List<Group> list = groupService.getGroupList(appId,user.getType(),userId,0,pageInfo);
		//格式化群组创建时间
		for(Group group : list){
			long createTime = group.getCreateTime();
			try{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = formatter.format(new Date(createTime));
				group.setFormateCreateTime(date);
			}catch (Exception e){
				group.setFormateCreateTime(String.valueOf(createTime));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Rows", list);
		map.put("Total",pageInfo.getTotalResult()); //数据总数
		HttpUtil.writeResult(response, map);
		return modelAndView;
	}
	
	/**
	 * 获取群组成员列表
	 * @param groupId,appId
	 */

	@RequestMapping(value="memberList", method = RequestMethod.GET)
	public ModelAndView memberList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("group/group");
		User user = (User) request.getSession().getAttribute("loginInfo");
		String groupId = ParamUtil.getParams(request, "groupId", "");
		String appId = ParamUtil.getParams(request, "appId", "");
		int pageNo =  ParamUtil.getIntParams(request, "page", 1);
		int pageSize =  ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		if(user.getType().equals(Constant.USER_TYPE.E_USER_TYPE_APP.value)){
			AppSearchModel appSearchModel = new AppSearchModel();
			appSearchModel.setUserId(Long.toString(user.getId()));
			appSearchModel.setType(user.getType());
			PageInfo pageInfoForApp = PageInfoFactory.getPageInfo(10, 1);
			List<AppInfo> appInfo = appService.getAppInfo(pageInfoForApp, appSearchModel);
			//List<AppInfo> appInfo = (List<AppInfo>) request.getSession().getAttribute("appIdList");
			modelAndView.addObject("appIdList", appInfo);
		}		
		modelAndView.addObject("type", user.getType());
		List<GroupMember> list = groupService.getGroupMembers(appId,user.getType(),groupId, 0, 1,pageInfo);
		//格式化群组创建时间
		for(GroupMember member : list){
			long joinTime = member.getJoinTime();
			long updateTime = member.getUpdateTime();
			try{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = formatter.format(new Date(joinTime));
				member.setFormatUpdateTime(formatter.format(new Date(updateTime)));
				member.setFormatJoinTime(date);
			}catch (Exception e){
				member.setFormatJoinTime(String.valueOf(joinTime));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Rows", list);
		map.put("Total",pageInfo.getTotalResult()); //数据总数
		HttpUtil.writeResult(response, map);
		return modelAndView;
	}	
	
	/**
	 * 获取群组信息
	 * @param groupId,appId
	 */

	@RequestMapping(value="getGroupInfo", method = RequestMethod.GET)
	public ModelAndView getGroupInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("group/group");
		User user = (User) request.getSession().getAttribute("loginInfo");
		String groupId = ParamUtil.getParams(request, "groupId", "");
		String appId = ParamUtil.getParams(request, "appId", "");
		int pageNo =  ParamUtil.getIntParams(request, "page", 1);
		int pageSize =  ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		if(user.getType().equals(Constant.USER_TYPE.E_USER_TYPE_APP.value)){
			AppSearchModel appSearchModel = new AppSearchModel();
			appSearchModel.setUserId(Long.toString(user.getId()));
			appSearchModel.setType(user.getType());
			PageInfo pageInfoForApp = PageInfoFactory.getPageInfo(10, 1);
			List<AppInfo> appInfo = appService.getAppInfo(pageInfoForApp, appSearchModel);
			modelAndView.addObject("appIdList", appInfo);
		}		
		modelAndView.addObject("type", user.getType());
		List<Group> list = new ArrayList<Group>();
		Group group = groupService.getGroupInfo(appId, groupId,pageInfo);
		list.add(group);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Rows", list);
		map.put("Total",pageInfo.getTotalResult()); //数据总数
		HttpUtil.writeResult(response, map);
		return modelAndView;
	}	
	
	
	
}

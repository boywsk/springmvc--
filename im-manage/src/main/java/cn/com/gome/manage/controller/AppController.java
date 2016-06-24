package cn.com.gome.manage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSON;

import cn.com.gome.manage.global.Global;
import cn.com.gome.manage.mongodb.model.AppSearchModel;
import cn.com.gome.manage.mongodb.model.AppSysAccount;
import cn.com.gome.manage.mongodb.model.request.ReqUser;
import cn.com.gome.manage.mongodb.model.request.ReqUserInfo;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pageSupport.PageInfoFactory;
import cn.com.gome.manage.pojo.AppInfo;
import cn.com.gome.manage.pojo.User;
import cn.com.gome.manage.service.AppService;
import cn.com.gome.manage.utils.Constant;
import cn.com.gome.manage.utils.HttpUtil;
import cn.com.gome.manage.utils.Md5Util;
import cn.com.gome.manage.utils.ParamUtil;
import cn.com.gome.manage.utils.StringUtil;

@Controller
@RequestMapping("/app")
public class AppController {
	@Autowired
	private AppService appService;
	Logger log = LoggerFactory.getLogger(AppController.class);

	@RequestMapping(value = "app", method = RequestMethod.GET)
	public String app(HttpServletRequest request, Model model) {
		return "app/app";
	}

	/**
	 * 用于申请appId appKey
	 * @param reqAppInfo
	 */
	@RequestMapping(value = "addAppInfo", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView addAppInfo(HttpServletRequest request, HttpServletResponse response) {
		int pageNo = ParamUtil.getIntParams(request, "page", 1);
		int pageSize = ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		AppSearchModel appSearchModel = new AppSearchModel();
		User user = (User) request.getSession().getAttribute("loginInfo");
		if (user != null) {
			String userId = ParamUtil.getParams(request, "userId", "");
			if(user.getType().equals(Constant.USER_TYPE.E_USER_TYPE_IM.value)){
				appSearchModel.setType(Constant.USER_TYPE.E_USER_TYPE_IM.value);
				if(StringUtils.isNotEmpty(userId)){
					appSearchModel.setUserId(userId);				
				}
			}else{
				appSearchModel.setType(user.getType());
				appSearchModel.setUserId(Long.toString(user.getId()));
			}
			appSearchModel.setUserId(Long.toString(user.getId()));
			List<AppInfo> appInfo = appService.getAppInfo(pageInfo, appSearchModel);
			if (appInfo.size() < 3) {// 根据userId判断是否已经达到极限APPID个数
				String appId = ParamUtil.getParams(request, "appId", "");
				appSearchModel.setAppId(appId);
				//List<AppInfo> appInfoByAppId = appService.getAppInfo(pageInfo, appSearchModel);
				int appIdCount = appService.getAPPIDCount(appId);
				if (appIdCount > 0) {// 判断APPID是否被注册
					return new ModelAndView("app/app", "message", "APPID已被注册！");
				} else {
					String appName = ParamUtil.getParams(request, "appName", "");
					String appDesc = ParamUtil.getParams(request, "appDesc", "");
					AppInfo app = new AppInfo();
					app.setuId(Long.toString(user.getId()));
					app.setAppId(appId);
					app.setAppName(appName);
					app.setAppDesc(appDesc);
					app.setUserName(user.getName());
					log.info("addAppInfo , parms:" + JSON.toJSONString(app));
					appService.saveAppInfo(app);
					return new ModelAndView("app/allAppInfoList");
				}
			} else {
				return new ModelAndView("app/app", "message", "已申请3个AppId");
			}
		}
		return new ModelAndView(new RedirectView("../app/appInfoList.do"));
	}

	@RequestMapping(value = "appInfoList", method = RequestMethod.GET)
	public String appInfoList(HttpServletRequest request, Model model) {
		return "app/appInfoList";
	}

	/**
	 * 获取App信息
	 * @return
	 */
	@RequestMapping(value = "getAppInfo", method = RequestMethod.POST)
	public ModelAndView getAppInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("app/allAppInfoList");
		AppSearchModel appSearchModel = new AppSearchModel();
		int pageNo = ParamUtil.getIntParams(request, "page", 1);
		int pageSize = ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);

		User user = (User) request.getSession().getAttribute("loginInfo");
		modelAndView.addObject("type",user.getType());
		log.info("getAppInfoByUid , parms:" + user.getId());

		String appId = ParamUtil.getParams(request, "appId", "");
		String appName = ParamUtil.getParams(request, "appName", "");
		appSearchModel.setAppId(appId);
		appSearchModel.setAppName(appName);
		

		String userId = ParamUtil.getParams(request, "userId", "");
		if(user.getType().equals(Constant.USER_TYPE.E_USER_TYPE_IM.value)){
			appSearchModel.setType(Constant.USER_TYPE.E_USER_TYPE_IM.value);
			String userName = ParamUtil.getParams(request, "userName", "");
			appSearchModel.setUserName(userName);
			if(StringUtils.isNotEmpty(userId)){
				appSearchModel.setUserId(userId);				
			}
		}else{
			appSearchModel.setType(user.getType());
			appSearchModel.setUserId(Long.toString(user.getId()));
		}		
		List<AppInfo> appInfo = appService.getAppInfo(pageInfo, appSearchModel);
		//格式化时间
		for(AppInfo appInfoList: appInfo){
			long createTime = Long.parseLong(appInfoList.getCreateTime());
			try{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = formatter.format(createTime);
				appInfoList.setCreateTime(date);
			}catch(Exception e){
				log.error(e.toString());
				appInfoList.setCreateTime(String.valueOf(createTime));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Rows", appInfo);
		map.put("Total", pageInfo.getTotalResult()); // 数据总数
		HttpUtil.writeResult(response, map);
		return modelAndView;
	}

	/**
	 * 跳转至全部App信息显示页面
	 */
	@RequestMapping(value = "allAppInfoList", method = RequestMethod.GET)
	public ModelAndView allAppInfoList(HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView("app/allAppInfoList");
		User user = (User) request.getSession().getAttribute("loginInfo");
		modelAndView.addObject("type",user.getType());
		return modelAndView;
	}

	/**
	 * 编辑App信息，不涉及数据库查询，传递四个参数appId、appName、appKey、appDesc
	 */
	@RequestMapping(value = "editAppInfo", method = RequestMethod.GET)
	public ModelAndView editAppInfo(HttpServletRequest request, HttpServletResponse response) {
		AppInfo appInfo = new AppInfo();
		appInfo.setAppId(request.getParameter("appId"));
		appInfo.setAppName(request.getParameter("appName"));
		appInfo.setAppKey(request.getParameter("appKey"));
		appInfo.setAppDesc(request.getParameter("appDesc"));
		return new ModelAndView("app/editAppInfo", "appInfo", appInfo);
	}

	/**
	 * 修改App信息appName appKey appDesc
	 */
	@RequestMapping(value = "updateAppInfo", method = RequestMethod.GET)
	public ModelAndView updateAppInfo(HttpServletRequest request, HttpServletResponse response) {
		AppInfo appInfo = new AppInfo();
		appInfo.setAppId(request.getParameter("appId"));
		appInfo.setAppName(request.getParameter("appName"));
		appInfo.setAppKey(request.getParameter("appKey"));
		appInfo.setAppDesc(request.getParameter("appDesc"));
		appService.updateAppInfo(appInfo);
		return new ModelAndView("app/editAppInfo", "message", "修改成功！");
	}

	/**
	 * 跳转至App系统账号申请 页面 参数：appId、uId、uName、uDesc
	 */
	@RequestMapping(value = "appSysAccount", method = RequestMethod.GET)
	public ModelAndView appSysAccount(HttpServletRequest request, Model model) {
		int pageNo = ParamUtil.getIntParams(request, "page", 1);
		int pageSize = ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		AppSearchModel appSearchModel = new AppSearchModel();
		User user = (User) request.getSession().getAttribute("loginInfo");
		appSearchModel.setUserId(Long.toString(user.getId()));
		appSearchModel.setType(user.getType());
		List<AppInfo> appInfo = appService.getAppInfo(pageInfo, appSearchModel);
		ModelAndView modelAndView = new ModelAndView("app/appSysAccount");
		modelAndView.addObject("appIdList", appInfo);
		modelAndView.addObject("type", user.getType());
		return modelAndView;
	}

	/**
	 * App系统账号增加 参数：appId、uId、uName、uDesc
	 */
	@RequestMapping(value = "addAppSysAccount", method = RequestMethod.POST)
	public ModelAndView addAppSysAccount(HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView("app/appSysAccount");
		String appId = ParamUtil.getParams(request, "appId", "");
		//配置分页信息
		int pageNo = ParamUtil.getIntParams(request, "page", 1);
		int pageSize = ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		//获取appId列表
		AppSearchModel appSearchModel = new AppSearchModel();
		User user = (User) request.getSession().getAttribute("loginInfo");
		appSearchModel.setUserId(Long.toString(user.getId()));
		appSearchModel.setType(user.getType());
		modelAndView.addObject("type",user.getType());
		List<AppInfo> list = appService.getAppInfo(pageInfo, appSearchModel);
		modelAndView.addObject("appIdList", list);
		
		String uId = ParamUtil.getParams(request, "uId", "");
		if(StringUtils.isNotEmpty(uId)){
			String[] uIdList= uId.split(",");
			if(uIdList.length >1){
				String[] uNameList = null;
				String[] uDescList = null;
				String uNameMany = ParamUtil.getParams(request, "uName", "");
				if(StringUtils.isNotEmpty(uNameMany)){
					uNameList = uNameMany.split(",");
				}
				String uDecsMany = ParamUtil.getParams(request, "uDesc", "");
				if(StringUtils.isNotEmpty(uDecsMany)){
					uDescList = uDecsMany.split(",");
				}
				String resultMessage = "运行成功！";
				for(int i=0; i<uIdList.length; i++){
					long uid = checkUid(uIdList[i]);
					if(uid == 0){
						log.info("Message:此appId:["+appId+"]下，uId：["+uIdList[i]+"]，uId格式不正确！");
						resultMessage = resultMessage+"<br>uId：["+uIdList[i]+"]，uId格式不正确！";
						continue;
					}
					if(uid > 2000000000){
						//判断appId下,是否已经注册该uId
						List<AppSysAccount> asaList = appService.getAppSysAccountByAppId(appId,uIdList[i]);
						if(asaList.size()>0){
							log.info("Message:此appId:["+appId+"]下，uId：["+uIdList[i]+"]已被注册！");
							resultMessage = resultMessage+"<br>uId：["+uIdList[i]+"]已被注册！";
							continue;
						}
						//uId没有被注册的情况下，获取appKey
						String appKey = "";
						for (AppInfo app : list) {
							if (StringUtils.equals(appId, app.getAppId())) {
								appKey = app.getAppKey();
								break;
							}
						}
					String code = registerAppSystemAccount(appId,uIdList[i],appKey);
					if (StringUtils.equals(code, "0")) {
						AppSysAccount asa = new AppSysAccount();
						asa.setAppId(appId);
						asa.setuId(uIdList[i]);
						if(uNameList != null){
							if(StringUtils.isNotEmpty(uNameList[i])){
								asa.setuName(uNameList[i]);
							}
						}
						if(uDescList != null){
							if(StringUtils.isNotEmpty(uDescList[i])){
								asa.setuDesc(uDescList[i]);
							}
						}
						appService.saveAppSysAccount(asa);
						log.info("Message:此appId:["+appId+"]下，uId：["+uIdList[i]+"]，注册成功！");
						resultMessage = resultMessage+"<br>uId：["+uIdList[i]+"]，注册成功！";
						} else {
							log.info("Message:此appId:["+appId+"]下，uId：["+uIdList[i]+"]，注册失败！");
							resultMessage = resultMessage+"<br>uId：["+uIdList[i]+"]，注册失败！";
							continue;
						}
					}else{
						log.info("Message:此appId:["+appId+"]下，uId：["+uIdList[i]+"]不符合格式，应大于2000000000！");
						resultMessage = resultMessage+"<br>uId：["+uIdList[i]+"]不符合格式，应大于2000000000！";
						continue;
					}
				}
				modelAndView.addObject("message", resultMessage);
			}else{
				long uid = checkUid(uId);
				if(uid == 0){
					modelAndView.addObject("message", "uId格式不正确！");
					log.info("Message:此appId:["+appId+"]下，uId：["+uId+"]，uId格式不正确！");
					return modelAndView;
				}
				if(uid > 2000000000){
					//判断appId下,是否已经注册该uId
					List<AppSysAccount> asaList = appService.getAppSysAccountByAppId(appId,uId);
					if(asaList.size()>0){
						modelAndView.addObject("appIdList", list);
						modelAndView.addObject("message", "此appId:["+appId+"]下，uId:["+uId+"]已被注册！");
						return modelAndView;
					}
					//uId没有被注册的情况下，获取appKey
					String appKey = "";
					for (AppInfo app : list) {
						if (StringUtils.equals(appId, app.getAppId())) {
							appKey = app.getAppKey();
							break;
						}
					}
					String code = registerAppSystemAccount(appId,uId,appKey);
					modelAndView.addObject("appIdList", list);
					//判断是否成功，0成功则存库，否则返注册失败
					if (StringUtils.equals(code, "0")) {
						AppSysAccount asa = new AppSysAccount();
						asa.setAppId(appId);
						asa.setuId(uId);
						asa.setuName(ParamUtil.getParams(request, "uName", ""));
						asa.setuDesc(ParamUtil.getParams(request, "uDesc", ""));
						appService.saveAppSysAccount(asa);
						ModelAndView modelAndView2 = new ModelAndView("app/appSysAccountList");
						modelAndView2.addObject("appIdList", list);
						modelAndView2.addObject("type",user.getType());
						return modelAndView2;
					} else {
						modelAndView.addObject("message", "注册失败");
					}
				}else{
					modelAndView.addObject("message", "uId不符合格式，应大于2000000000！");
				}
				return modelAndView;
			}
		}else{
			modelAndView.addObject("message", "uId不能为空！");
		}
		return modelAndView;
	}
	/**
	 * 判断uId
	 */
	public long checkUid(String uId){
		long uid = 0;
		try{
			uid =Long.parseLong(uId);
		}catch(Exception e){
			return 0;
		}
		return uid;
	}
	/**
	 * 注册
	 */
	public String registerAppSystemAccount(String appId, String uId, String appKey){
		//配置、组装调用接口参数
		ReqUserInfo reqUserInfo = new ReqUserInfo();
		ReqUser reqUser = new ReqUser();
		reqUser.setUid(uId);
		reqUserInfo.setOpt(1);
		reqUserInfo.setUseUid(1);
		reqUserInfo.setReqUser(reqUser);
		String url = Global.CENTER_IM_API_URL + "user/register.json";
		long timestamp = System.currentTimeMillis();
		String signature = "";
		try {
			signature = Md5Util.md5Encode(appId + "|" + timestamp + "|" + appKey);
		} catch (Exception e) {
			log.error(e.toString());
		}
		String traceId  = StringUtil.getUuid();
		try{
			url = url+"?appId="+appId+"&timestamp="+timestamp+"&signature="+signature+"&traceId="+traceId;
			log.info(url);
		}catch(Exception e){
			log.error(e.toString());
		}
		String json = HttpUtil.httpRequest(url, JSON.toJSONString(reqUserInfo), "post");
		log.info(json);
		String code = JSON.parseObject(json).getString("code");
		return code;
	}
	/**
	 * 跳转至App系统账号 显示页面
	 * 增加APPId列表
	 */
	@RequestMapping(value = "appSysAccountList", method = RequestMethod.GET)
	public ModelAndView AppSysAccountList(HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView("app/appSysAccountList");
		int pageNo = ParamUtil.getIntParams(request, "page", 1);
		int pageSize = ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		//获取appId列表
		AppSearchModel appSearchModel = new AppSearchModel();
		User user = (User) request.getSession().getAttribute("loginInfo");
		modelAndView.addObject("type",user.getType());
		if(StringUtils.equals(user.getType(), Constant.USER_TYPE.E_USER_TYPE_IM.value)){
			appSearchModel.setType(user.getType());
		}else{
			appSearchModel.setUserId(Long.toString(user.getId()));
			appSearchModel.setType(user.getType());
		}
		List<AppInfo> list = appService.getAppInfo(pageInfo, appSearchModel);
		modelAndView.addObject("appIdList", list);
		return modelAndView;
	}
	/**
	 * App系统账号 查询显示
	 * 参数：appId
	 */
	@RequestMapping(value = "displayAppSysAccount", method = RequestMethod.POST)
	public ModelAndView displayAppSysAccount(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("app/appSysAccountList");
		int pageNo = ParamUtil.getIntParams(request, "page", 1);
		int pageSize = ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		PageInfo pageInfoForAPPID = PageInfoFactory.getPageInfo(pageSize, pageNo);
		String appId = ParamUtil.getParams(request, "appId", "");
		//获取appId列表
		AppSearchModel appSearchModel = new AppSearchModel();
		User user = (User) request.getSession().getAttribute("loginInfo");
		if(StringUtils.equals(user.getType(), Constant.USER_TYPE.E_USER_TYPE_IM.value)){
			appSearchModel.setType(user.getType());
		}else{
			appSearchModel.setUserId(Long.toString(user.getId()));
			appSearchModel.setType(user.getType());
		}
		List<AppInfo> list = appService.getAppInfo(pageInfoForAPPID, appSearchModel);
		modelAndView.addObject("appIdList", list);
		modelAndView.addObject("type", user.getType());
		String uId = ParamUtil.getParams(request, "uId", "");
		String uName = ParamUtil.getParams(request, "uName", "");
		List<AppSysAccount> asaList = appService.displayAppSysAccountByAppId(pageInfo, appId, uId, uName);
		//格式化创建时间
		for(AppSysAccount asa : asaList){
			long createTime = Long.parseLong(asa.getCreateTime());
			try{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = formatter.format(new Date(createTime));
				asa.setCreateTime(date);
			}catch(Exception e){
				log.error(e.toString());
				asa.setCreateTime(String.valueOf(createTime));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Rows", asaList);
		map.put("Total", pageInfo.getTotalResult()); // 数据总数
		HttpUtil.writeResult(response, map);
		return modelAndView;
	}
	/**
	 * App系统账号 清理数据库后用于同步数据
	 * 参数：appId
	 */
	@RequestMapping(value = "registerAllAppSysAccount", method = RequestMethod.POST)
	public ModelAndView registerAllAppSysAccount(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("app/appSysAccount");
		String appId = ParamUtil.getParams(request, "appId", "");
		AppSearchModel appSearchModel = new AppSearchModel();
		User user = (User) request.getSession().getAttribute("loginInfo");
		//modelAndView.addObject("type", user.getType());
		if(user.getType().equals(Constant.USER_TYPE.E_USER_TYPE_IM.value)){
			appSearchModel.setType(user.getType());
		}else{
			appSearchModel.setUserId(Long.toString(user.getId()));
			appSearchModel.setType(user.getType());
		}
		List<AppInfo> list = appService.getAllAppInfo(appSearchModel);
		String uId = "";
		String uName = "";
		List<AppSysAccount> asaList = appService.displayAllAppSysAccountByAppId(appId, uId, uName);
		String appKey = "";
		for (AppInfo app : list) {
			if (appId.equals(app.getAppId())) {
				appKey = app.getAppKey();
				break;
			}
		}
		log.info("Total re-register："+asaList.size());
		String resultMessage = "运行成功！总注册数量："+asaList.size();
		for (AppSysAccount appSysAccount : asaList) {
			appId = appSysAccount.getAppId();
			uId = appSysAccount.getuId();
			//配置、组装调用接口参数
			ReqUserInfo reqUserInfo = new ReqUserInfo();
			ReqUser reqUser = new ReqUser();
			reqUser.setUid(uId);
			reqUserInfo.setOpt(1);
			reqUserInfo.setUseUid(1);
			reqUserInfo.setReqUser(reqUser);
			String url = Global.CENTER_IM_API_URL + "user/register.json";
			long timestamp = System.currentTimeMillis();
			String signature = "";
			try {
				signature = Md5Util.md5Encode(appId + "|" + timestamp + "|" + appKey);
			} catch (Exception e) {
				resultMessage = resultMessage+"<br>uId:"+uId+":注册失败，signature,参数加密失败！";
				continue;
			}
			String traceId  = StringUtil.getUuid();
			url = url+"?appId="+appId+"&timestamp="+timestamp+"&signature="+signature+"&traceId="+traceId;
			String jsonCheck = HttpUtil.httpRequest(url, JSON.toJSONString(reqUserInfo), "post");
			log.info(jsonCheck);
			if(StringUtils.isNotEmpty(jsonCheck)){
				resultMessage = resultMessage+"<br>uId:"+uId+":"+JSON.parseObject(jsonCheck).getString("message");
			}else{
				resultMessage = resultMessage+"<br>uId:"+uId+":注册失败，调用SDK失败！";
				continue;
			}			
		}
		modelAndView.addObject("message", resultMessage);
		log.info(resultMessage);
		return modelAndView;
	}
	/*
	 * public void delAppInfo(String uId);
	 */
}

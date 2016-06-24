package cn.com.gome.manage.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.servlet.ModelAndView;

import cn.com.gome.manage.mongodb.model.AppSearchModel;
import cn.com.gome.manage.mongodb.model.FileModel;
import cn.com.gome.manage.mongodb.model.file.FileCount;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pageSupport.PageInfoFactory;
import cn.com.gome.manage.pojo.AppInfo;
import cn.com.gome.manage.pojo.User;
import cn.com.gome.manage.service.AppService;
import cn.com.gome.manage.service.FileService;
import cn.com.gome.manage.utils.Constant;
import cn.com.gome.manage.utils.HttpUtil;
import cn.com.gome.manage.utils.ParamUtil;

@Controller
@RequestMapping("/file")
public class FileController {
	Logger log = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private AppService appService;
	@Autowired
	private FileService fileService;
	@RequestMapping(value="upload", method = RequestMethod.GET)
	public ModelAndView upload(HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView("file/upload");
		return modelAndView;
	}
	@RequestMapping(value="preStatistics",method = RequestMethod.GET)
	public ModelAndView preStatistics(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("file/preStatistics");
		/*PrintWriter out = null;
		String startDate = ParamUtil.getParams(request, "startDate", "");
		String endDate = ParamUtil.getParams(request, "endDate", "");
		int pageNo = ParamUtil.getIntParams(request, "page", 1);
		int pageSize = ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		List<FileCount> fileCount = fileService.getFileCountInfo(pageInfo, startDate, endDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Rows", fileCount);
		map.put("Total", pageInfo.getTotalResult()); // 数据总数
		String json = JSON.toJSONString(map);
		try {
			response.setHeader("Content-type", "text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.setContentLength(json.getBytes("UTF-8").length);
			out = response.getWriter();
			out.write(json);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}*/
		return modelAndView;
	}
	
	@RequestMapping(value="preStatisticsList",method = RequestMethod.GET)
	public ModelAndView preStatisticsList(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("file/preStatistics");
		String startDate = ParamUtil.getParams(request, "startDate", "");
		String endDate = ParamUtil.getParams(request, "endDate", "");
		int pageNo = ParamUtil.getIntParams(request, "page", 1);
		int pageSize = ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		List<FileCount> fileCount = fileService.getFileCountInfo(pageInfo, startDate, endDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Rows", fileCount);
		map.put("Total", pageInfo.getTotalResult()); // 数据总数
		HttpUtil.writeResult(response, map);
		return modelAndView;
	}
	
	@RequestMapping(value="display", method = RequestMethod.GET)
	public ModelAndView display(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("file/display");
		String filePath = ParamUtil.getParams(request, "filePath", "");
		if(filePath.contains("_small")){				//头像
			modelAndView.addObject("type","avatar");
			modelAndView.addObject("smallAvatarPath",filePath);
			modelAndView.addObject("largeAvatarPath",filePath.replace("_small", "_large"));
		}else if(filePath.contains("_Small")){			//图片
			modelAndView.addObject("type","image");
			modelAndView.addObject("smallimagePath",filePath);
			modelAndView.addObject("middleimagePath",filePath.replace("_Small", "_Middle"));
			modelAndView.addObject("originimagePath",filePath.replace("_Small", ""));
		}else if(filePath.contains("_vedio") || filePath.contains("_img")){			//视频
			modelAndView.addObject("type","vedio");
			modelAndView.addObject("vedioPrtScrPath",filePath);
			modelAndView.addObject("vedioPath",filePath);
			filePath = filePath.replace("_img.jpg", "_vedio.mp4");
			try {
				response.sendRedirect(filePath);
			} catch (IOException e) {
				log.info(e.toString());
			}
		}else if(filePath.toUpperCase().contains(".AMR") || filePath.toUpperCase().contains(".WAV")){	//语音
			modelAndView.addObject("type","voice");
			modelAndView.addObject("filePath",filePath);
			try {
				response.sendRedirect(filePath);
			} catch (IOException e) {
				log.info(e.toString());
			}
		}
		return modelAndView;
	}
	@RequestMapping(value="listUserFiles", method = RequestMethod.GET)
	public ModelAndView listUserFiles(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("file/listUserFiles");
		String uid = ParamUtil.getParams(request, "uid", "");
		
		String searchType = ParamUtil.getParams(request, "searchType", "");//头像、图片、音频、视频，分数对应数据库表名。若表名有改动后期修改或拼接。
		if(StringUtils.isEmpty(searchType)){
			searchType = "t_image_url";
			return modelAndView;
		}
		
		/*if(StringUtils.isEmpty(uid)){
			return modelAndView;
		}*/
		String startTime = ParamUtil.getParams(request, "startTime", "");
    	String endTime = ParamUtil.getParams(request, "endTime", "");
		
		int pageNo = ParamUtil.getIntParams(request, "page", 1);
		int pageSize = ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		
		User user = (User) request.getSession().getAttribute("loginInfo");
		modelAndView.addObject("type",user.getType());
		log.info("listUserFiles, parms:" + user.getId());
		
		AppSearchModel appSearchModel = new AppSearchModel();
		if(user.getType().equals(Constant.USER_TYPE.E_USER_TYPE_IM.value)){
			appSearchModel.setType(Constant.USER_TYPE.E_USER_TYPE_IM.value);
			if(StringUtils.isNotEmpty(uid)){
				appSearchModel.setUserId(uid);				
			}
		}else{
			appSearchModel.setType(user.getType());
			appSearchModel.setUserId(Long.toString(user.getId()));
			PageInfo pageInfoForApp = PageInfoFactory.getPageInfo(10, 1);
			List<AppInfo> appInfo = appService.getAppInfo(pageInfoForApp, appSearchModel);
			modelAndView.addObject("appIdList", appInfo);
		}
		FileModel fileModel = new FileModel();		
		fileModel.setUid(uid);
		fileModel.setSearchType(searchType);
		fileModel.setStartTime(startTime);
		fileModel.setEndTime(endTime);
		List<FileModel> fileList = fileService.getFileInfo(pageInfo, fileModel);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Rows", fileList);
		map.put("Total", pageInfo.getTotalResult()); // 数据总数
		HttpUtil.writeResult(response, map);
		return modelAndView;
	}
}
package cn.com.gome.manage.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.com.gome.manage.mongodb.model.AppSearchModel;
import cn.com.gome.manage.mongodb.model.GroupMsg;
import cn.com.gome.manage.mongodb.model.MsgStats;
import cn.com.gome.manage.mongodb.service.MessageService;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pageSupport.PageInfoFactory;
import cn.com.gome.manage.pojo.AppInfo;
import cn.com.gome.manage.pojo.Echarts;
import cn.com.gome.manage.pojo.Series;
import cn.com.gome.manage.pojo.User;
import cn.com.gome.manage.service.impl.AppServiceImpl;
import cn.com.gome.manage.utils.HttpUtil;
import cn.com.gome.manage.utils.ParamUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;

@Controller
@RequestMapping("/message")
public class MessageController {
	private MessageService messageService = new MessageService();
	private AppServiceImpl appService = new AppServiceImpl();

	@RequestMapping(value="groupMsgList", method = RequestMethod.GET)
	public ModelAndView groupMsg(HttpServletRequest request, Model model) {
		int pageNo =  ParamUtil.getIntParams(request, "page", 1);
		int pageSize =  ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		//增加appID列表
		AppSearchModel appSearchModel = new AppSearchModel();
		User user =(User)request.getSession().getAttribute("loginInfo");
		appSearchModel.setUserId(Long.toString(user.getId()));
		appSearchModel.setType(user.getType());
    	List<AppInfo> appInfo = appService.getAppInfo(pageInfo,appSearchModel);
    	ModelAndView modelAndView = new ModelAndView("message/groupMsg");
        modelAndView.addObject("appIdList", appInfo);  
        modelAndView.addObject("type",user.getType());
        return modelAndView;
	}
	/*public String groupMsg(HttpServletRequest request, model model) {
		return "message/groupMsg";
	}*/

	@RequestMapping(value="searchGroupMsg", method = RequestMethod.GET)
	public void groupMsgList(HttpServletRequest request, HttpServletResponse response) {
		String appId = ParamUtil.getParams(request, "appId", "");
		String groupId = ParamUtil.getParams(request, "groupId", "");
		String senderId = ParamUtil.getParams(request, "senderId", "");
		String senderName = ParamUtil.getParams(request, "senderName", "");

		String startTime = ParamUtil.getParams(request, "startTime", "");
		String endTime = ParamUtil.getParams(request, "endTime", "");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*long startTime = 0;
		long endTime = 0;
		try {
			startTime = formatter.parse(startDate).getTime();
			endTime = formatter.parse(endDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		int pageNo =  ParamUtil.getIntParams(request, "page", 1);
		int pageSize =  ParamUtil.getIntParams(request, "pagesize", 10);

		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize,pageNo);

		List<GroupMsg> messageList = messageService.getGroupMsgById(appId,groupId,senderId,senderName,startTime,endTime,pageInfo);

		//格式化群组创建时间
		for(GroupMsg groupMsg : messageList){
			long sendTime = groupMsg.getSendTime();
			try{
				String date = formatter.format(new Date(sendTime));
				groupMsg.setFormatSendTime(date);
			}catch (Exception e){
				groupMsg.setFormatSendTime(String.valueOf(sendTime));
			}
		}
		PrintWriter out = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Rows", messageList);
		map.put("Total",pageInfo.getTotalResult()); //数据总数
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
			if( out != null) {
				out.close();
			}
		}
	}

	@RequestMapping(value="groupMsgStatistics", method = RequestMethod.GET)
	public ModelAndView statistics(HttpServletRequest request, Model model) {
		int pageNo =  ParamUtil.getIntParams(request, "page", 1);
		int pageSize =  ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		//增加appID列表
		AppSearchModel appSearchModel = new AppSearchModel();
		User user =(User)request.getSession().getAttribute("loginInfo");
		appSearchModel.setUserId(Long.toString(user.getId()));
		appSearchModel.setType(user.getType());
    	List<AppInfo> appInfo = appService.getAppInfo(pageInfo,appSearchModel);
    	//ModelAndView modelAndView = new ModelAndView("message/groupMsg");  
    	ModelAndView modelAndView = new ModelAndView("message/groupMsgStatistics");  
        modelAndView.addObject("appIdList", appInfo);
        modelAndView.addObject("type",user.getType());
        return modelAndView;
		//return "message/groupMsgStatistics";
	}
	/*public String statistics(HttpServletRequest request, model model) {

		return "message/groupMsgStatistics";
	}*/


	@RequestMapping(value="getData", method = RequestMethod.POST)
	public void getData(HttpServletRequest request, HttpServletResponse response) {

		String time = ParamUtil.getParams(request, "time", "0");
		//TODO   根据时间请求当天的统计数据
		MsgStats msgStats = null;
		if(time!= null && !time.isEmpty()){
			msgStats = messageService.getMsgStats(time);
		}

		if(msgStats == null){
			msgStats = new MsgStats();
			msgStats.setDay(time);
			List<Long> list = new ArrayList<>();
			for(int i = 0 ;i<24;i++){
				list.add((long)0);
			}
			msgStats.setGroupMsgCounts(list);
			msgStats.setSingleMsgCounts(list);
		}

		List<Series> series=new ArrayList<Series>();
		List<String> legend=new ArrayList<String>(Arrays.asList(new String[]{"多人会话消息量","单人会话消息量"}));

		//x轴显示数据
		List<String> axis=new ArrayList<String>(Arrays.asList(new String[]{"0点","1点","2点","3点","4点","5点","6点",
				"7点","8点","9点","10点","11点","12点","13点","14点","15点","16点","17点","18点","19点","20点","21点","22点","23点"}));

		List<Long> groupMsgList = msgStats.getGroupMsgCounts();
		List<Long> msgList = msgStats.getSingleMsgCounts();

//		//y轴显示数据
//		List<Integer> groupMsgList = new ArrayList<>();
//		List<Integer> msgList = new ArrayList<>();
//
//		//----------构造数据test----------------------test-------------------------------------------
//		Random random = new Random();
//		for(int i=0;i<24;i++){
//			groupMsgList.add(i+10000+random.nextInt(1000));
//			msgList.add(i *346 +10000+random.nextInt(1000));
//		}
//		//--------------------------------test-------------------------------------------

		series.add(new Series("多人会话消息量", "line", groupMsgList));
		series.add(new Series("单人会话消息量", "line", msgList));
		Echarts echarts = new Echarts(legend,axis,series);
		HttpUtil.writeResult(response, echarts);
	}

}

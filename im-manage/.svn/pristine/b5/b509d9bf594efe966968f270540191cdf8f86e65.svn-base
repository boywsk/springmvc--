package cn.com.gome.manage.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;

import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pageSupport.PageInfoFactory;
import cn.com.gome.manage.pojo.Server;
import cn.com.gome.manage.pojo.ServerEcharts;
import cn.com.gome.manage.pojo.ServerSeries;
import cn.com.gome.manage.pojo.ServerStatistics;
import cn.com.gome.manage.service.ServerService;
import cn.com.gome.manage.service.ServerStatisticsService;
import cn.com.gome.manage.utils.HttpUtil;
import cn.com.gome.manage.utils.ParamUtil;
import cn.com.gome.manage.utils.StringUtil;

@Controller
@RequestMapping("/server")
public class ServerController {
	Logger log = LoggerFactory.getLogger(ServerController.class);

	@Autowired
	private ServerService serverService;
	@Autowired
	private ServerStatisticsService serverStatisticsService;

	@RequestMapping(value = "logicServer", method = RequestMethod.GET)
	public String logic() {

		return "server/logicList";
	}

	@RequestMapping(value = "logicList", method = RequestMethod.GET)
	public void logicList(HttpServletRequest request, HttpServletResponse response) {
		int pageNo = ParamUtil.getIntParams(request, "page", 1);
		int pageSize = ParamUtil.getIntParams(request, "pagesize", 10);
		PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
		String serverIp = ParamUtil.getParams(request, "serverIp", null);
		// int serverPort = ParamUtil.getIntParams(request, "serverPort", 0);
		int serverType = ParamUtil.getIntParams(request, "serverType", 0);

		Server server = new Server();
		server.setServerIp(serverIp);
		// server.setServerPort(serverPort);
		server.setServerType(serverType);

		List<Server> list = serverService.listServer(pageInfo, server);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Rows", list);
		map.put("Total", pageInfo.getTotalResult()); // 数据总数
		HttpUtil.writeResult(response, map);
	}

	@RequestMapping(value = "preServerStatistics", method = RequestMethod.GET)
	public String preServerStatistics(HttpServletRequest request, Model model) {
		int serverType = ParamUtil.getIntParams(request, "serverType", 0);
		String serverKey = ParamUtil.getParams(request, "serverKey", "0");
		String time = StringUtil.long2DateString2(System.currentTimeMillis());
		
		model.addAttribute("serverType", serverType);
		model.addAttribute("serverKey", serverKey);
		model.addAttribute("nowTime", time);
		
		return "server/serverStatistics";
	}
	
	@RequestMapping(value = "serverStatistics", method = RequestMethod.POST)
	public void serverStatistics(HttpServletRequest request, HttpServletResponse response) {
		String time = ParamUtil.getParams(request, "time", "0");
		int serverType = ParamUtil.getIntParams(request, "serverType", 0);
		String serverKey = ParamUtil.getParams(request, "serverKey", "0");
		ServerStatistics statistics = new ServerStatistics();
		statistics.setServerKey(serverKey);
		statistics.setServerType(serverType);
		long statisticsTime = StringUtil.dateString2Long(time);
		statistics.setStatisticsTime(statisticsTime);
		List<ServerStatistics> listStatistics = serverStatisticsService.listServerStatistics(statistics);
		int size = listStatistics.size();
		List<String> axis = new ArrayList<String>(size);
		List<Number> avgMemRateList = new ArrayList<Number>(size);
		List<Number> maxMemRateList = new ArrayList<Number>(size);
		List<Number> minMemRateList = new ArrayList<Number>(size);
		List<Number> avgCpuRateList = new ArrayList<Number>(size);
		List<Number> maxCpuRateList = new ArrayList<Number>(size);
		List<Number> minCpuRateList = new ArrayList<Number>(size);
		List<Number> avgConnList = new ArrayList<Number>(size);
		List<Number> maxConnList = new ArrayList<Number>(size);
		List<Number> minConnList = new ArrayList<Number>(size);
		for(ServerStatistics statis : listStatistics) {
			String hour = StringUtil.long2DateString3(statis.getStatisticsTime());
			double avgMemRate = statis.getAvgMemRate();
			double maxMemRate = statis.getMaxMemRate();
			double minMemRate = statis.getMinMemRate();
			double avgCpuRate = statis.getAvgCpuRate();
			double maxCpuRate = statis.getMaxCpuRate();
			double minCpuRate = statis.getMinCpuRate();
			long avgConn = statis.getAvgInConn();
			long maxConn = statis.getMaxInConn();
			long minConn = statis.getMinInConn();
			axis.add(hour);
			avgMemRateList.add(avgMemRate);
			maxMemRateList.add(maxMemRate);
			minMemRateList.add(minMemRate);
			avgCpuRateList.add(avgCpuRate);
			maxCpuRateList.add(maxCpuRate);
			minCpuRateList.add(minCpuRate);
			avgConnList.add(avgConn);
			maxConnList.add(maxConn);
			minConnList.add(minConn);
		}

		List<ServerSeries> series = new ArrayList<ServerSeries>();
		List<String> legend = new ArrayList<String>(Arrays.asList(new String[]
				{"平均内存", "最大内存", "最小内存", "平均CPU", "最大CPU", "最小CPU", "平均连接数", "最大连接数", "最小连接数"}));
		series.add(new ServerSeries("平均内存", "line", avgMemRateList));
		series.add(new ServerSeries("最大内存", "line", maxMemRateList));
		series.add(new ServerSeries("最小内存", "line", minMemRateList));
		series.add(new ServerSeries("平均CPU", "line", avgCpuRateList));
		series.add(new ServerSeries("最大CPU", "line", maxCpuRateList));
		series.add(new ServerSeries("最小CPU", "line", minCpuRateList));
		series.add(new ServerSeries("平均连接数", "line", avgConnList));
		series.add(new ServerSeries("最大连接数", "line", maxConnList));
		series.add(new ServerSeries("最小连接数", "line", minConnList));
		ServerEcharts echarts = new ServerEcharts(legend, axis, series);
		HttpUtil.writeResult(response, echarts);
	}
}

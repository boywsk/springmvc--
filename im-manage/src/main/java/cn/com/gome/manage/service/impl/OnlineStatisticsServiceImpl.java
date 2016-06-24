package cn.com.gome.manage.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.gome.manage.dao.OnlineStatisticsMapper;
import cn.com.gome.manage.pojo.OnlineStatistics;
import cn.com.gome.manage.service.OnlineStatisticsService;

@Service("onlineStatisticsService")
public class OnlineStatisticsServiceImpl implements OnlineStatisticsService {
	Logger log = LoggerFactory.getLogger(OnlineStatisticsServiceImpl.class);
	
	@Autowired
	private OnlineStatisticsMapper onlineStatisticsMapper;
	
	/**
	 * 获取在线人数统计数据
	 */
	public List<OnlineStatistics> listOnlineStatistics(OnlineStatistics statistics) {
		log.info("[OnlineStatisticsServiceImpl ListOnlineStatistics]............");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appId", statistics.getAppId());
		long time = statistics.getStatisticsTime();
		long[] timeArr = this.getTime(time);
		params.put("startTime", timeArr[0]);
		params.put("endTime", timeArr[1]);
		List<OnlineStatistics> list = onlineStatisticsMapper.listOnlineStatistics(params);
		
		return list;
	}
	
	
	/**
	 * 获取开始和结束时间
	 * @param time
	 * @return
	 */
	private long[] getTime(long time) {
		long[] times = new long[2];
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
		long startTime = calendar.getTimeInMillis();
		
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(time);
	    c2.set(Calendar.HOUR_OF_DAY, 23);
	    c2.set(Calendar.MINUTE, 59);
	    c2.set(Calendar.SECOND, 59);
		long endTime = c2.getTimeInMillis();
		
		times[0] = startTime;
		times[1] = endTime;
		
		return times;
	}
}

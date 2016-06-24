package cn.com.gome.manage.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.gome.manage.dao.ServerStatisticsMapper;
import cn.com.gome.manage.pojo.ServerStatistics;
import cn.com.gome.manage.service.ServerStatisticsService;

/**
 * 服务信息统计
 */
@Service("serverStatisticsService")
public class ServerStatisticsServiceImpl implements ServerStatisticsService {
	Logger log = LoggerFactory.getLogger(ServerStatisticsServiceImpl.class);
	
	@Autowired
	private ServerStatisticsMapper serverStatisticsMapper;
	
	/**
	 * 获取服务统计数据
	 */
	public List<ServerStatistics> listServerStatistics(ServerStatistics statistics) {
		log.info("[ServerStatisticsServiceImpl ListServerStatistics]............");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serverType", statistics.getServerType());
		params.put("serverKey", statistics.getServerKey());
		long time = statistics.getStatisticsTime();
		long[] timeArr = this.getTime(time);
		params.put("startTime", timeArr[0]);
		params.put("endTime", timeArr[1]);
		List<ServerStatistics> list = serverStatisticsMapper.listServerStatistics(params);
		
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

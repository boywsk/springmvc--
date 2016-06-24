package cn.com.gome.manage.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import cn.com.gome.manage.pojo.ServerResource;
import cn.com.gome.manage.global.Global;
import cn.com.gome.manage.utils.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * 服务器资源redis操作工具类
 */
public class ServerResurceRedisDao {
	Logger log = LoggerFactory.getLogger(ServerResurceRedisDao.class);
	
	/**
	 * 获取服务器资源列表信息
	 * @param resource
	 */
	public List<ServerResource> listServerResource(String serverType) {
		log.info("listServerResource serverType:[{}]", serverType);
		List<ServerResource> list = new ArrayList<ServerResource>();
		JedisUtil util = JedisUtil.getInstance();
		Jedis jedis = util.getJedis();
		jedis.select(Global.SERVER_RESOURCE_REDIS_INDEX);
		Map<String, String> map = jedis.hgetAll(serverType);
		if(map != null) {
			for(String key : map.keySet()) {
				String value = map.get(key);
				ServerResource resource = JSON.parseObject(value, ServerResource.class);
				list.add(resource);
			}
		}
		
		return list;
	}
}

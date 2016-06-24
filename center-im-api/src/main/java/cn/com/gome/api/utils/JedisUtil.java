package cn.com.gome.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/** 
 * Redis工具类,用于获取RedisPool. 
 */  
public class JedisUtil {
	private static Logger log = LoggerFactory.getLogger(JedisUtil.class);
	private static JedisUtil instance = null;
	private static ShardedJedisPool shardJedisPool = null;
	private static final String config = "/conf/config.properties";
	
	private static String cluster_ip_ports = "";

    public static JedisUtil getInstance() {
    	if(instance == null) {
    		instance = new JedisUtil();
    	}
        return instance;
    }
    
    static {
        if (shardJedisPool == null) {
			try {
				Properties properties = PropertiesUtils.LoadProperties(config);
				cluster_ip_ports = properties.getProperty("redis_cluster_ip_ports");

				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxIdle(5);
				config.setMaxTotal(50);
				config.setMinIdle(5);
				config.setMaxWaitMillis(1000 * 10);
				config.setTestOnBorrow(true);

				String[] ipArr = cluster_ip_ports.split(",");
				List<JedisShardInfo> shardList = new ArrayList<>();
				for(String ipPort : ipArr){
					String[] hostPort = ipPort.split(":");
					String host = hostPort[0];
					int port = Integer.parseInt(hostPort[1]);
					JedisShardInfo info = new JedisShardInfo(host,port);
					shardList.add(info);
				}

				shardJedisPool = new ShardedJedisPool(config,shardList);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
        }
    }
    
    /** 
     * 获取Redis实例. 
     * @return Redis工具类实例 
     */  
    public ShardedJedis getJedis() {
		ShardedJedis jedis = null;
		try{
			jedis = shardJedisPool.getResource();
		}catch (Exception e){
			log.error("getJedis error:"+e.getMessage());
		}
        return jedis;
    }
  
    public void releaseJedis(ShardedJedis jedis) {
        if(jedis != null) {
			shardJedisPool.returnBrokenResource(jedis);
        }
    }

	public void returnJedis(ShardedJedis jedis) {
		if(jedis != null) {
			shardJedisPool.returnResource(jedis);
		}
	}
    
    public static void main(String[] args) throws Exception {
		JedisUtil util = JedisUtil.getInstance();
		ShardedJedis jedis = util.getJedis();
		try{
			String key = "testShardRedisKey";
			jedis.setex(key,5, "123");
			System.out.println(jedis.get(key));
			jedis.del(key);
			util.releaseJedis(jedis);
		}catch (Exception e){
			util.releaseJedis(jedis);
		}finally {
			util.returnJedis(jedis);
		}
	}
}

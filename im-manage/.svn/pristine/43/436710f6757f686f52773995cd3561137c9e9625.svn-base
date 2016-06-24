package cn.com.gome.manage.utils;

import cn.com.gome.manage.global.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

/** 
 * Redis工具类,用于获取RedisPool. 
 */  
public class JedisUtil {
	Logger log = LoggerFactory.getLogger(JedisUtil.class);
	private static JedisUtil instance = null;
	private static JedisPool pool = null;
	private static final String redisProperties = "/redis.properties";
	
	private static String ip = "";
	private static int port = 10379;
	private static String pass = "";
	
    public static JedisUtil getInstance() {
    	if(instance == null) {
    		instance = new JedisUtil();
    	}
        return instance;
    }
    
    static {
        if (pool == null) {
			try {
				Properties properties = PropertiesUtils.LoadProperties(redisProperties);
				ip = properties.getProperty("logic.server.redis.address");
				port = Integer.parseInt(properties.getProperty("logic.server.redis.port"));
				//pass = properties.getProperty("offline.server.redis.pass");
				Global.REDIS_INDEX = Integer.parseInt(properties.getProperty("logic.server.redis.index"));

				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxIdle(5);
				config.setMaxTotal(50);
				config.setMinIdle(5);
				config.setMaxWaitMillis(1000 * 10);
				config.setTestOnBorrow(true);
				if(pass != null && pass.length() > 0) {
					pool = new JedisPool(config, ip, port, 10*1000, pass);
				} else {
					pool = new JedisPool(config, ip, port, 10*1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
    
    /** 
     * 获取Redis实例. 
     * @return Redis工具类实例 
     */  
    public Jedis getJedis() {
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
		}catch (Exception e){
			log.error("getJedis error:"+e.getMessage());
		}
        return jedis;
    }
  
    public void releaseJedis(Jedis jedis) {
        if(jedis != null) {
			pool.returnBrokenResource(jedis);
        }
    }

	public void returnJedis(Jedis jedis) {
		if(jedis != null) {
			pool.returnResource(jedis);
		}
	}
    
    public static void main(String[] args) throws Exception {
		JedisUtil util = JedisUtil.getInstance();
		Jedis jedis = util.getJedis();
		try{
			jedis.select(0);
			jedis.setex("test_1000", 30, "test");
			System.out.println(jedis.keys("test_1000"));
			jedis.del("test_1000");
			System.out.println(jedis.keys("test_1000"));
			util.releaseJedis(jedis);
		}catch (Exception e){
			util.releaseJedis(jedis);
		}finally {
			util.returnJedis(jedis);
		}
	}
}

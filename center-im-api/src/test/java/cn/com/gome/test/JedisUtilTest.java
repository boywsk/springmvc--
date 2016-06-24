package cn.com.gome.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
 * Redis工具类,用于获取RedisPool. 
 */  
public class JedisUtilTest {
	private static Logger log = LoggerFactory.getLogger(JedisUtilTest.class);
	private static JedisUtilTest instance = null;
	private static JedisCluster jedisCluster = null;

	private JedisUtilTest(){}

	private static String cluster_ip_ports = "";

    public static JedisUtilTest getInstance() {
    	if(instance == null) {
    		instance = new JedisUtilTest();
    	}
        return instance;
    }
    
    static {
        if (jedisCluster == null) {
			try {
				Set<HostAndPort> jedisClusterNodes = new HashSet<>();
				cluster_ip_ports = "10.125.3.11:7000,10.125.3.21:7000,10.125.3.31:7000";
				String[] ipArr = cluster_ip_ports.split(",");
				for(String ipPort : ipArr){
					String[] hostPort = ipPort.split(":");
					String host = hostPort[0];
					int port = Integer.parseInt(hostPort[1]);
					//Jedis Cluster will attempt to discover cluster nodes automatically
					jedisClusterNodes.add(new HostAndPort(host, port));
				}
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxIdle(5);
				config.setMaxTotal(50);
				config.setMinIdle(5);
				config.setMaxWaitMillis(1000 * 10);
				config.setTestOnBorrow(true);
				jedisCluster = new JedisCluster(jedisClusterNodes,config);
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
    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public static void main(String[] args) throws Exception {
		JedisUtilTest util = JedisUtilTest.getInstance();
		JedisCluster jedis = util.getJedisCluster();
		try{
			String key = "testShardRedisKey";
			List<String> uids = new ArrayList<>();
			uids.add(123+"");
			uids.add(1234+"");
			uids.add(1235+"");
			String[] groupMembers = uids.toArray(new String[uids.size()]);
			jedis.sadd(key,groupMembers);
			Set<String> uidSet = jedis.smembers(key);
			System.out.println("uidSet:"+ uidSet.toString());
			jedis.del(key);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

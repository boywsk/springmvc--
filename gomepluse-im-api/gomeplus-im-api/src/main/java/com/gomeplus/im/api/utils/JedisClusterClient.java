package com.gomeplus.im.api.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.gomeplus.im.api.global.Global;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by wangshikai on 2016/5/9.
 */
public class JedisClusterClient {
    private static Logger log = LoggerFactory.getLogger(JedisClusterClient.class);

    public static JedisClusterClient INSTANCE = new JedisClusterClient();

    private static JedisCluster jedisCluster = null;
    private static final String config = "/conf/config.properties";
    private static String cluster_ip_ports = "";
    private JedisClusterClient(){
    }
    static {
        if (jedisCluster == null) {
            try {
                Set<HostAndPort> jedisClusterNodes = new HashSet<>();
                Properties properties = PropertiesUtils.LoadProperties(config);
                cluster_ip_ports = properties.getProperty("redis_cluster_ip_ports");
                String[] ipArr = cluster_ip_ports.split(",");
                for(String ipPort : ipArr){
                    String[] hostPort = ipPort.split(":");
                    String host = hostPort[0];
                    int port = Integer.parseInt(hostPort[1]);
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

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }
    
    /**
	 * 根据用户ID得到昵称
	 * @param userId 用户ID
	 * @param nickName 用户表中的昵称
	 * @return
	 */
	public static String getNowNickName(long userId,String nickName){
		//1、redis中取出变化的ID集合
		JedisCluster jedis = JedisClusterClient.INSTANCE.getJedisCluster();
		//2、判断redis中变化的id列表中是否存在当前用户
		Set<String> idList = jedis.smembers(Global.TEMP_CHANGE_NIKENAME_IDS);
		if (CollectionUtils.isEmpty(idList)) {
			return nickName;
		}
		for (String id : idList) {
			if (StringUtils.equals(userId+"", id)) {
				return jedis.get(id+Global.TEMP_CHANGE_USERID_NICKNAME_SUFFIX);
			}
		}
		//3、不存在直接返回，存在则取redis中的昵称
		return nickName;
	}
	
	
    /**
	 * 是不是存在昵称改动
	 * @param appId 
	 * @return
	 */
	public static boolean hasNameChange(String appId){
		JedisCluster jedis = JedisClusterClient.INSTANCE.getJedisCluster();
		String key = appId + Global.TEMP_CHANGE_NIKENAME_IDS;
		return jedis.exists(key);
	}
}

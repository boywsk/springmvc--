package cn.com.gome.manage.redis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.gome.manage.global.Global;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.service.impl.AppServiceImpl;
import cn.com.gome.manage.utils.JedisUtil;
import cn.com.gome.manage.utils.JedisUtils;
import redis.clients.jedis.Jedis;

/**
 * 用户在线信息redis操作工具类
 */
public class UserRedisService {
	Logger log = LoggerFactory.getLogger(UserRedisService.class);
	
	/**
	 * 根据用户id获取用户在线数据
	 * @param uid
	 * @return
	 */
	public Map<String, String> listUserRsp(long uid) {
		JedisUtil util = JedisUtil.getInstance();
		Jedis jedis = util.getJedis();
		try {
			jedis.select(Global.REDIS_INDEX);
			Map<String, String> map = jedis.hgetAll(String.valueOf(uid));
			return map;
		} catch(Exception e) {
			util.releaseJedis(jedis);
			log.error("listUserRsp:", e);
		} finally {
			util.returnJedis(jedis);
		}
		return null;
	}
	/**
	 * 根据appId,uId获取用户Apns（消息推送）
	 * @param appId,uId
	 * @return
	 */
	public Map<String, String> getApnsInfo(String appId, String uid) {
		log.info("[incPushCount] appId=[{}],uid=[{}]", appId, uid);
		JedisUtils util = JedisUtils.getInstance();
		try {
			String hashName = "apns_"+appId+"_"+uid;
			log.info("getApnsInfo,hashName:"+hashName);			
			Map<String, String> map = util.getJedisCluster().hgetAll(hashName);
			log.info(map.toString());
			return map;
		} catch(Exception e) {
			log.error("[getApnsInfo] cause is:", e);
		} 
		return null;
	}
	/**
	 * 根据appId,uId获取appId下所有用户Apns（消息推送）信息
	 * @param appId
	 * @return
	 */
	public List<RedisHashInfo> getAllApnsInfo(String appId,PageInfo pageInfo) {
		log.info("[incPushCount] appId=[{}]", appId);
		JedisUtils util = JedisUtils.getInstance();
		//获取appId下的所有用户ID，即uId
		AppServiceImpl appService = new AppServiceImpl();
		List<String> uidList = appService.getAllUidFromAppId(appId);
		List<RedisHashInfo> list = new ArrayList<>();
		try {
			for(int i = 0; i < uidList.size(); i++){
				String uId = uidList.get(i);
				String hashName = "apns_"+appId+"_"+uId;
				log.info("getApnsInfo,hashName:"+hashName);			
				Map<String, String> map = util.getJedisCluster().hgetAll(hashName);
				Set<Map.Entry<String,String>> set = map.entrySet();
	            for(Map.Entry<String,String> entry : set){
	           	 RedisHashInfo apnsInfo = new RedisHashInfo();
	           	 apnsInfo.setuId(String.valueOf(uId));
	                apnsInfo.setKey(entry.getKey());
	                apnsInfo.setValue(entry.getValue());
	                list.add(apnsInfo);
	            }
			}
			Collections.sort(list,new Comparator<RedisHashInfo>() {
				@Override
				public int compare(RedisHashInfo o1,RedisHashInfo o2) {
					if(Integer.parseInt(o1.getValue()) >= Integer.parseInt(o2.getuId())){
						return -1;
					}else {
						return 1;
					}
				}
			});
			//获取分页后的最终List
			pageInfo.setTotalResult(list.size());
			List<RedisHashInfo> resultList = new ArrayList<>();
			int startLocation = (pageInfo.getCurrentPage()-1)*pageInfo.getPageSize();
			int endLocation = startLocation+pageInfo.getPageSize();
			if(endLocation>=list.size()){
				endLocation = list.size()-1;
			}
			for(int i=startLocation ; i<=endLocation; i++){
				resultList.add(list.get(i));
			}
			return resultList;
		} catch(Exception e) {
			log.error("[getApnsInfo] cause is:", e);
		} 
		return null;
	}
	/**
	 * 根据appId,uId获取用户登录信息
	 * @param appId,uId
	 * @return
	 */
	public Map<String, String> getLoginInfo(String appId, String uid) {
		log.info("[incPushCount] appId=[{}],uid=[{}]", appId, uid);
		JedisUtils util = JedisUtils.getInstance();
		try {
			String hashName = appId+"_"+uid;
			log.info("getApnsInfo,hashName:"+hashName);			
			Map<String, String> map = util.getJedisCluster().hgetAll(hashName);
			log.info(map.toString());
			return map;
		} catch(Exception e) {
			log.error("[getApnsInfo] cause is:", e);
		} 
		return null;
	}
	/**
	 * 根据appId,uId获取appId下所有用户登录信息
	 * @param appId
	 * @return
	 */
	public List<RedisHashInfo> getAllLogonInfo(String appId,PageInfo pageInfo) {
		log.info("[incPushCount] appId=[{}]", appId);
		JedisUtils util = JedisUtils.getInstance();
		//获取appId下的所有用户ID，即uId
		AppServiceImpl appService = new AppServiceImpl();
		List<String> uidList = appService.getAllUidFromAppId(appId);
		List<RedisHashInfo> list = new ArrayList<>();
		try {
			for(int i = 0; i < uidList.size(); i++){
				String uId = uidList.get(i);
				String hashName = appId+"_"+uId;
				log.info("getApnsInfo,hashName:"+hashName);			
				Map<String, String> map = util.getJedisCluster().hgetAll(hashName);
				Set<Map.Entry<String,String>> set = map.entrySet();
	            for(Map.Entry<String,String> entry : set){
	           	 RedisHashInfo apnsInfo = new RedisHashInfo();
	           	 apnsInfo.setuId(String.valueOf(uId));
	                apnsInfo.setKey(entry.getKey());
	                apnsInfo.setValue(entry.getValue());
	                list.add(apnsInfo);
	            }
			}
			Collections.sort(list,new Comparator<RedisHashInfo>() {
				@Override
				public int compare(RedisHashInfo o1,RedisHashInfo o2) {
					if(o1.getuId().compareTo(o2.getuId())>=0){
						return -1;
					}else {
						return 1;
					}
				}
			});
			//获取分页后的最终List
			pageInfo.setTotalResult(list.size());
			List<RedisHashInfo> resultList = new ArrayList<>();
			int startLocation = (pageInfo.getCurrentPage()-1)*pageInfo.getPageSize();
			int endLocation = startLocation+pageInfo.getPageSize();
			if(endLocation>=list.size()){
				endLocation = list.size()-1;
			}
			for(int i=startLocation ; i<=endLocation; i++){
				resultList.add(list.get(i));
			}
			return resultList;
		} catch(Exception e) {
			log.error("[getApnsInfo] cause is:", e);
		} 
		return null;
	}
}

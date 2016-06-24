package cn.com.gome.manage.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.com.gome.manage.mongodb.dao.UserInfoDao;
import cn.com.gome.manage.mongodb.model.TUserInfo;
import cn.com.gome.manage.mongodb.model.UserSearchModel;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pojo.UserInfo;
import cn.com.gome.manage.redis.UserRedisService;
import cn.com.gome.manage.service.UserInfoService;
import cn.com.gome.manage.utils.IPUtils;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	
	public List<TUserInfo> listTUserInfo(UserSearchModel userSearchModel,PageInfo pageInfo){
		List<TUserInfo> tUserInfoList = new ArrayList<TUserInfo>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(StringUtils.isNotEmpty(userSearchModel.getStartTime())){
				String startTime = userSearchModel.getStartTime()+" 00:00:00";
				userSearchModel.setStartTime(Long.toString(formatter.parse(startTime).getTime()));
			}
			if(StringUtils.isNotEmpty(userSearchModel.getEndTime())){
				String endTime = userSearchModel.getEndTime()+" 23:59:59";				
				userSearchModel.setEndTime(Long.toString(formatter.parse(endTime).getTime()));
			}			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		UserInfoDao dao = new UserInfoDao();
		tUserInfoList = dao.listTUserInfo(userSearchModel, pageInfo);
		for(TUserInfo tui : tUserInfoList){
			try{
				tui.setCreateTime(formatter.format(Long.parseLong(tui.getCreateTime())));
				tui.setUpdateTime(formatter.format(Long.parseLong(tui.getUpdateTime())));
			}catch(Exception e){
				tui.setCreateTime(String.valueOf(Long.parseLong(tui.getCreateTime())));
			}
		}
		return tUserInfoList;
	}

	/**
	 * 分页获取im用户信息
	 */
	public List<UserInfo> listUserInfo(PageInfo pageInfo) {
		UserInfoDao dao = new UserInfoDao();
		List<UserInfo> list = dao.listUserInfo(pageInfo);
		UserRedisService userRedis = new UserRedisService();
    	for(UserInfo info : list) {
    		Map<String, String> map = userRedis.listUserRsp(info.getUid());
    		if(map == null || map.isEmpty()) {
    			info.setStatus(false);
    			continue;
    		}
    		info.setStatus(true);
    		String device = "";
    		for(String key : map.keySet()) {
    			String[] keyArr = key.split("_");
    			if(keyArr.length >= 2) {
    				String value = map.get(key);
    				String[] valueArr = value.split(":");
    				String ip = IPUtils.longToIP(Long.valueOf(valueArr[0]));
    				int port = Integer.valueOf(valueArr[1]);
    				int clientId = Integer.parseInt(keyArr[0]);
    				if(clientId == 10) {
    					String str = "IOS(" + ip + ":" + port + ")";
    					if(device.length() == 0) {
    						device = str;
    					} else {
    						device = device + ";" + str;
    					}
    				} else if(clientId == 11) {
    					String str = "Android(" + ip + ":" + port + ")";
    					if(device.length() == 0) {
    						device = str;
    					} else {
    						device = device + ";" + str;
    					}
    				} else if(clientId == 20) {
    					String str = "PC(" + ip + ":" + port + ")";
    					if(device.length() == 0) {
    						device = str;
    					} else {
    						device = device + ";" + str;
    					}
    				}
    			}
    		}
    		info.setDevive(device);
    	}
		
		
		return list;
	}

	/**
	 * 根据昵称分页查询im用户
	 */
	public List<UserInfo> searchUserInfo(String nickName, PageInfo pageInfo) {
		UserInfoDao dao = new UserInfoDao();
		List<UserInfo> list = dao.searchUserInfo(nickName, pageInfo);
		UserRedisService userRedis = new UserRedisService();
    	for(UserInfo info : list) {
    		Map<String, String> map = userRedis.listUserRsp(info.getUid());
    		if(map == null || map.isEmpty()) {
    			info.setStatus(false);
    			continue;
    		}
    		info.setStatus(true);
    		String device = "";
    		for(String key : map.keySet()) {
    			String[] keyArr = key.split("_");
    			if(keyArr.length >= 2) {
    				String value = map.get(key);
    				String[] valueArr = value.split(":");
    				String ip = IPUtils.longToIP(Long.valueOf(valueArr[0]));
    				int port = Integer.valueOf(valueArr[1]);
    				int clientId = Integer.parseInt(keyArr[0]);
    				if(clientId == 10) {
    					String str = "IOS(" + ip + ":" + port + ")";
    					if(device.length() == 0) {
    						device = str;
    					} else {
    						device = device + ";" + str;
    					}
    				} else if(clientId == 11) {
    					String str = "Android(" + ip + ":" + port + ")";
    					if(device.length() == 0) {
    						device = str;
    					} else {
    						device = device + ";" + str;
    					}
    				} else if(clientId == 20) {
    					String str = "PC(" + ip + ":" + port + ")";
    					if(device.length() == 0) {
    						device = str;
    					} else {
    						device = device + ";" + str;
    					}
    				}
    			}
    		}
    		info.setDevive(device);
    	}
		
		
		return list;
	}
}

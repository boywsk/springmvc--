package com.gome.im.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gome.im.api.dao.UserMapper;
import com.gome.im.api.db.model.User;
import com.gome.im.api.global.Global;
import com.gome.im.api.model.ReqUser;
import com.gome.im.api.model.ResultModel;
import com.gome.im.api.model.request.ReqUserInfo;
import com.gome.im.api.service.UserService;
import com.gome.im.api.threadPool.ThreadPool;
import com.gome.im.api.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;
	
	public ResultModel<Object> saveUser(final User user) {
		ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE,"注册失败",new HashMap<>());
		try {
			if(user.getPhoneNumber()==null || user.getPhoneNumber().length()==0){
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE,"手机号不能为空",new HashMap<>());
			}
			User oldUser = userMapper.getUserInfoByPhoneNumber(user.getPhoneNumber());
			if(oldUser != null){
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE,"手机号已注册",new HashMap<>());
			}
			long time = System.currentTimeMillis();
			user.setCreateTime(time);
			user.setUpdateTime(time);
			userMapper.saveUser(user);
			final long userId = user.getId();
			user.setAppId(Global.APP_ID);
			user.setId(userId);
			int opt = 1;
			ReqUserInfo req = getReqUserInfo(opt,userId);
			req.setUseUid(1); //使用我们自己的uid作为imUserId 标识
			String json = HttpUtil.httpRequest(HttpUtil.GET_USER_TOKEN_PATH, JSON.toJSONString(req));
			System.out.println("json:"+json);
			String data = JSON.parseObject(json).getString("data");
			final String token = JSON.parseObject(data).getString("token");
			final long  tokenExpires = JSON.parseObject(data).getLong("tokenExpires").longValue();
			final long imUserId = JSON.parseObject(data).getLong("imUserId").longValue();
			user.setToken(token);
			user.setTokenValidity(tokenExpires);
			user.setImUserId(imUserId);
			ThreadPool pool = ThreadPool.getInstance();
			pool.addTask(new Runnable() {
				@Override
				public void run() {
					try {
						userMapper.updateUserInfo(user);
					} catch (Exception e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
				}
			});
			resultModel = new ResultModel<Object>(ResultModel.RESULT_OK,"注册成功",user);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return resultModel;
	}
	
	public ResultModel<Object> updateUserInfo(User reqUser) {
		ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE,"更新失败",new HashMap<>());
		try {
			User user = userMapper.getUserInfo(reqUser.getId());
			int opt = 2;
			ReqUserInfo req = getReqUserInfo(opt,reqUser.getId());
//			req.getReqUser().setImUserId(reqUser.getImUserId());
			String json = HttpUtil.httpRequest(HttpUtil.GET_USER_TOKEN_PATH, JSON.toJSONString(req));
			JSONObject object = JSON.parseObject(json);
			String data = object.getString("data");
			String token = JSON.parseObject(data).getString("token");
			long  tokenExpires = JSON.parseObject(data).getLong("tokenExpires").longValue();
			user.setToken(token);
			user.setTokenValidity(tokenExpires);
			userMapper.updateUserInfo(user);
			resultModel= new ResultModel<Object>(ResultModel.RESULT_OK,"更新成功",user);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return resultModel;
	}
	
	public ResultModel<Object> getUserInfo(User reqUser) {
		ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE,"获取信息失败",new HashMap<>());
		try {
			User user = userMapper.getUserInfo(reqUser.getId());
			user.setAppId(Global.APP_ID);
			HashMap<String,Object> map = getInfo(user);
			resultModel = new ResultModel<Object>(ResultModel.RESULT_OK,"获取信息成功",map);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return resultModel;
	}

	@Override
	public ResultModel<Object> login(User reqUser,HttpServletRequest request) {
		ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE,"登录失败",new HashMap<>());
		try {
			User user = userMapper.getUserInfoByPhoneNumber(reqUser.getPhoneNumber());
			if(user != null && user.getPassword().equals(reqUser.getPassword())){
				request.getSession().setAttribute("user",user);
				user.setAppId(Global.APP_ID);
				return new ResultModel<Object>(ResultModel.RESULT_OK,"登录成功",user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return resultModel;
	}

	@Override
	public ResultModel<Object> findUser(User reqUser) {
		ResultModel<Object> resultModel = new ResultModel<Object>(ResultModel.RESULT_FAILURE,"用户不存在",new HashMap<>());
		try {
			User user = userMapper.getUserInfoByPhoneNumber(reqUser.getPhoneNumber());
			HashMap<String,Object> map = new HashMap<>();
			if(user != null){
				user.setAppId(Global.APP_ID);
				map = getInfo(user);
			}
			return new ResultModel<Object>(ResultModel.RESULT_OK,"查询用户成功",map);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return resultModel;
	}

	public HashMap<String,Object> getInfo(User user){
		HashMap<String,Object> map = new HashMap<>();
		map.put("id",user.getId());
		map.put("imUserId",user.getImUserId());
		map.put("appId",user.getAppId());
		map.put("userName",user.getUserName());
		map.put("nickName",user.getNickName());
		map.put("gender",user.getGender());
		map.put("avatar",user.getAvatar());
		map.put("region",user.getRegion());
		map.put("birthday",user.getBirthday());
		map.put("autograph",user.getAutograph());
		map.put("phoneNumber",user.getPhoneNumber());
		return map;
	}

	public ReqUserInfo getReqUserInfo(int opt,long uid){
		ReqUserInfo reqUserInfo = new ReqUserInfo();
		reqUserInfo.setOpt(opt);
		ReqUser userInfo = new ReqUser();
		userInfo.setUid(String.valueOf(uid));
		reqUserInfo.setReqUser(userInfo);
		return reqUserInfo;
	}

}

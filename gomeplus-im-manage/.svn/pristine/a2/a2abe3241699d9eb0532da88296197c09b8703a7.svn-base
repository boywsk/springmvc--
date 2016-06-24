package com.gomeplus.im.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gomeplus.im.manage.dao.UserMapper;
import com.gomeplus.im.manage.model.User;
import com.gomeplus.im.manage.pageSupport.PageInfo;
import com.gomeplus.im.manage.pojo.Menu;
import com.gomeplus.im.manage.service.UserService;

/**
 * Created by wangshikai on 2016/6/3.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByName(Map<String,Object> parm) {
        return userMapper.getUserInfoByName(parm);
    }
    @Override
	public void updateUserByid(User user) {
		userMapper.updateUserByid(user);
	}

    @Override
    public User getUserByPhone(long phoneNum) {
        return userMapper.getUserInfoByPhoneNumber(phoneNum);
    }
    
    @Override
    public List<User> findUserByPage(String userName,String nickName,PageInfo pageInfo) {
    	
    	Map<String,Object> parms = new HashMap<String,Object>();
    	parms.put("userName", userName);
    	parms.put("nickName", nickName);
    	int total = userMapper.countUser(parms);
    	pageInfo.setTotalResult(total);
    	parms.put("start", pageInfo.getBeginResult());
    	parms.put("end", pageInfo.getEndResult());
        return userMapper.findUserByPage(parms);
    }
    @Override
	public User getUserByUserName(String userName) {
		return userMapper.getUserByUserName(userName);
	}
    @Override
	public void saveUser(User user) {
		userMapper.saveUser(user);
	}
    @Override
	public void delUser(String ids) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("ids", ids);
		userMapper.delUser(param);
	}
}

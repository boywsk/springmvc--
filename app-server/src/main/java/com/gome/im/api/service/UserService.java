package com.gome.im.api.service;


import com.gome.im.api.db.model.User;
import com.gome.im.api.model.ResultModel;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
	
	public ResultModel<Object> saveUser(User user);
	
	public ResultModel<Object> updateUserInfo(User user);
	
	public ResultModel<Object> getUserInfo(User user);

	public ResultModel<Object> login(User user,HttpServletRequest request);

	public ResultModel<Object> findUser(User user);

}

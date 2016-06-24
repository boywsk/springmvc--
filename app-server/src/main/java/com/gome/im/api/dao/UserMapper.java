package com.gome.im.api.dao;

import com.gome.im.api.db.model.User;

import java.util.Map;

public interface UserMapper {
	
	public long saveUser(User user);
	
	public void updateUserInfo(User user);
	
	public User getUserInfo(long id);

	public User getUserInfoByPhoneNumber(String phoneNumber);
	
	public int delUser(int id);

	public int countUser(Map<?, ?> param);

}

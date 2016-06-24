package com.gomeplus.im.api.dao;


import com.gomeplus.im.api.model.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
	
	public long saveUser(User user);
	
	public void updateUserInfo(User user);
	
	public User getUserInfoById(long id);

	public User getUserInfoByPhoneNumber(long phoneNumber);
	
	public User getUserInfoByUserName(String userName);
	
	public User getUserInfoByCondition(Map<String, Object> param);
	
	public int delUser(int id);

	public int countUser(Map<?, ?> param);

	public List<User>  findUser(String keyWord);
	
	public List<User> findUsersByIds(List<Long> idList);
	
}

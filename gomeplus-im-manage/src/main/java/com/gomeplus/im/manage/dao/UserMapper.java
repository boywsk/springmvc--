package com.gomeplus.im.manage.dao;



import java.util.List;
import java.util.Map;

import com.gomeplus.im.manage.model.User;
import com.gomeplus.im.manage.pojo.Menu;


/**
 * gomeplus_im 用户中心 用户数据
 */
public interface UserMapper {
	

	public User getUserInfo(long id);

	public User getUserInfoByPhoneNumber(long phoneNumber);

	public User getUserInfoByName(Map<String,Object> parms);
	
	public User getUserByUserName(String userName);
	
	public void saveUser(User user);
	
	public void delUser(Map<?, ?> param);
	
	public int countUser(Map<?, ?> param);
	
	public List<User> findUserByPage(Map<String,Object> parms);
	
	public void updateUserByid(User user);
	
}

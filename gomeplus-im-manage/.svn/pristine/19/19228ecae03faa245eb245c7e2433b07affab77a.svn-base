package com.gomeplus.im.manage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gomeplus.im.manage.model.User;
import com.gomeplus.im.manage.pageSupport.PageInfo;
import com.gomeplus.im.manage.pojo.Menu;

/**
 * Created by wangshikai on 2016/6/3.
 */
public interface UserService {

    public User getUserByName(Map<String,Object> parm);
    
    public User getUserByUserName(String userName);
    
    public void saveUser(User user);
    
    public void delUser(String ids); 
    
    public User getUserByPhone(long phoneNum);
    
    public List<User> findUserByPage(String userName,String nickName,PageInfo pageInfo);
    
    public void updateUserByid(User user);
    
}

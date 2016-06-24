package cn.com.gome.manage.service;

import java.util.List;
import java.util.Map;

import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pojo.Menu;
import cn.com.gome.manage.pojo.User;
import cn.com.gome.manage.pojo.UserPermit;

public interface UserService {
	
	public void saveUser(User user);
	
	public void updateUserByid(User user);
	
	public User getUserByUserName(String userName);
	
	public int countUser(Map<?, ?> param);
	
	public List<User> listUser(PageInfo pageInfo, String userName, String name);
	
	public void delUser(String ids);
	
	public void saveMenu(Menu menu);
	
	public void updateMenuByid(Menu menu);
	
	public List<Menu> getMenuByPid(long pid);
	
	public Menu getMenuById(long id);
	
	public void delMenu(long id);
	
	public List<Menu> listUserMenu(long uid);
	
	public List<Long> listUserMenuId(long uid);
	
	public void delUserPermit(long uid);
	
	public void saveUserPermit(UserPermit userPermit);
	
	public List<Menu> listUserParentMenu(long uid);
	
	public List<Menu> listUserSubMenu(long uid, long pid);
}

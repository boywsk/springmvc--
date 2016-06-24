package cn.com.gome.manage.dao;

import java.util.List;
import java.util.Map;
import cn.com.gome.manage.pojo.Menu;
import cn.com.gome.manage.pojo.User;
import cn.com.gome.manage.pojo.UserPermit;

public interface UserMapper {
	
	public void saveUser(User user);
	
	public void updateUserByid(User user);
	
	public User getUserByUserName(String userName);
	
	public int countUser(Map<?, ?> param);
	
	public List<User> listUser(Map<?, ?> param);
	
	public void delUser(Map<?, ?> param);
	
	public void saveMenu(Menu menu);
	
	public void updateMenuByid(Menu menu);
	
	public List<Menu> getMenuByPid(long pid);
	
	public Menu getMenuById(long id);
	
	public void delMenu(Map<?, ?> param);
	
	public List<Menu> listUserMenu(Map<?, ?> param);
	
	public void delUserPermit(Map<?, ?> param);
	
	public void saveUserPermit(UserPermit userPermit);
	
	public List<Menu> listUserParentMenu(Map<?, ?> param);
	
	public List<Menu> listUserSubMenu(Map<?, ?> param);
}

package cn.com.gome.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.gome.manage.dao.UserMapper;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pojo.Menu;
import cn.com.gome.manage.pojo.User;
import cn.com.gome.manage.pojo.UserPermit;
import cn.com.gome.manage.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	
	public void saveUser(User user) {
		userMapper.saveUser(user);
	}
	
	public void updateUserByid(User user) {
		userMapper.updateUserByid(user);
	}
	
	public User getUserByUserName(String userName) {
		return userMapper.getUserByUserName(userName);
	}
	
	public int countUser(Map<?, ?> param) {
		return userMapper.countUser(param);
	}
	
	public List<User> listUser(PageInfo pageInfo, String userName, String name) {
		Map<Object,Object> param = new HashMap<Object,Object>();
		int pageNo = pageInfo.getCurrentPage();
		int pageSize = pageInfo.getPageSize();
		
		param.put("sart", (pageNo -1) * pageSize);
		param.put("pageSize", pageSize);
		param.put("userName", userName);
		param.put("name", name);
		
		Map<Object,Object> countParam = new HashMap<Object,Object>();
		countParam.put("userName", userName);
		countParam.put("name", name);
		
		int totalResult = countUser(countParam);
		pageInfo.setTotalResult(totalResult);
		pageInfo.calculate();
		
		return userMapper.listUser(param);
	}
	
	public void delUser(String ids) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("ids", ids);
		userMapper.delUser(param);
	}
	
	public void saveMenu(Menu menu){
		userMapper.saveMenu(menu);
	}
	
	public void updateMenuByid(Menu menu) {
		userMapper.updateMenuByid(menu);
	}
	
	public List<Menu> getMenuByPid(long pid) {
		return userMapper.getMenuByPid(pid);
	}
	
	public Menu getMenuById(long id) {
		return userMapper.getMenuById(id);
	}
	
	public void delMenu(long id) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("id", id);
		userMapper.delMenu(param);
	}
	
	public List<Menu> listUserMenu(long uid) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("uid", uid);
		return userMapper.listUserMenu(param);
	}
	
	public List<Long> listUserMenuId(long uid) {
		List<Long> ids = new ArrayList<Long>();
		List<Menu> menus = listUserMenu(uid);
		for(Menu menu : menus) {
			ids.add(menu.getId());
		}
		
		return ids;
	}
	
	public void delUserPermit(long uid) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("uid", uid);
		userMapper.delUserPermit(param);
	}
	
	public void saveUserPermit(UserPermit userPermit) {
		userMapper.saveUserPermit(userPermit);
	}
	
	public List<Menu> listUserParentMenu(long uid) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("uid", uid);
		return userMapper.listUserParentMenu(param);
	}
	
	public List<Menu> listUserSubMenu(long uid, long pid) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("uid", uid);
		param.put("pid", pid);
		return userMapper.listUserSubMenu(param);
	}
}

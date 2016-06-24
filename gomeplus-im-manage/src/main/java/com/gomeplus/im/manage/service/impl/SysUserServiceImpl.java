package com.gomeplus.im.manage.service.impl;

import com.gomeplus.im.manage.dao.SysUserMapper;
import com.gomeplus.im.manage.pageSupport.PageInfo;
import com.gomeplus.im.manage.pojo.Menu;
import com.gomeplus.im.manage.pojo.User;
import com.gomeplus.im.manage.pojo.UserPermit;
import com.gomeplus.im.manage.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserMapper sysUserMapper;
	
	public void saveUser(User user) {
		sysUserMapper.saveUser(user);
	}
	
	public void updateUserByid(User user) {
		sysUserMapper.updateUserByid(user);
	}
	
	public User getUserByUserName(String userName) {
		return sysUserMapper.getUserByUserName(userName);
	}
	
	public int countUser(Map<?, ?> param) {
		return sysUserMapper.countUser(param);
	}
	
	public List<User> listUser(PageInfo pageInfo, String userName, String name) {
		Map<Object,Object> param = new HashMap<Object,Object>();
		int pageNo = pageInfo.getCurrentPage();
		int pageSize = pageInfo.getPageSize();
		
		param.put("sart", (pageNo -1) * pageSize);
		param.put("pageSize", pageSize);
		param.put("userName", userName);
		param.put("nickName", name);
		
		Map<Object,Object> countParam = new HashMap<Object,Object>();
		countParam.put("userName", userName);
		countParam.put("nickName", name);
		
		int totalResult = countUser(countParam);
		pageInfo.setTotalResult(totalResult);
		pageInfo.calculate();
		
		return sysUserMapper.listUser(param);
	}
	
	public void delUser(String ids) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("ids", ids);
		sysUserMapper.delUser(param);
	}
	
	public void saveMenu(Menu menu){
		sysUserMapper.saveMenu(menu);
	}
	
	public void updateMenuByid(Menu menu) {
		sysUserMapper.updateMenuByid(menu);
	}
	
	public List<Menu> getMenuByPid(long pid) {
		return sysUserMapper.getMenuByPid(pid);
	}
	
	public Menu getMenuById(long id) {
		return sysUserMapper.getMenuById(id);
	}
	
	public void delMenu(long id) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("id", id);
		sysUserMapper.delMenu(param);
	}
	
	public List<Menu> listUserMenu(long uid) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("uid", uid);
		return sysUserMapper.listUserMenu(param);
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
		sysUserMapper.delUserPermit(param);
	}
	
	public void saveUserPermit(UserPermit userPermit) {
		sysUserMapper.saveUserPermit(userPermit);
	}
	
	public List<Menu> listUserParentMenu(long uid) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("uid", uid);
		return sysUserMapper.listUserParentMenu(param);
	}
	
	public List<Menu> listUserSubMenu(long uid, long pid) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("uid", uid);
		param.put("pid", pid);
		return sysUserMapper.listUserSubMenu(param);
	}
}

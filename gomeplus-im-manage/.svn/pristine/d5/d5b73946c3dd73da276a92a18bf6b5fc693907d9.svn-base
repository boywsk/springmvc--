package com.gomeplus.im.manage.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.gomeplus.im.manage.model.User;
import com.gomeplus.im.manage.pageSupport.PageInfo;
import com.gomeplus.im.manage.pageSupport.PageInfoFactory;
import com.gomeplus.im.manage.pojo.Menu;
import com.gomeplus.im.manage.service.UserService;
import com.gomeplus.im.manage.utils.HttpUtil;
import com.gomeplus.im.manage.utils.ParamUtil;
import com.gomeplus.im.manage.utils.PasswordEncode;

/**
 * Created by wangshikai on 2016/6/6.
 */
@Controller
@RequestMapping("imUser")
public class UserController {
	
	@Autowired
    private UserService userService;
	
    @RequestMapping(value="listUser", method = RequestMethod.GET)
    public String listUser(HttpServletRequest request, HttpServletResponse response,Model model) {
    	
        String userName = request.getParameter("userName");
        String name = request.getParameter("name");
        if(userName != null && userName.trim().length() > 0){
        	 model.addAttribute("userName", userName);
        }else{
        	model.addAttribute("userName", "");
        	userName = null;
        }
        if(name != null && name.trim().length() > 0){
       	 model.addAttribute("name", name);
       }else{
       	model.addAttribute("name", "");
       	name = null;
       }
        
        int pageNo =  ParamUtil.getIntParams(request, "page", 1);
        int pageSize =  ParamUtil.getIntParams(request, "pagesize", 10);
        PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
        List<User> users = userService.findUserByPage(userName, name, pageInfo);
        model.addAttribute("users", users);
        model.addAttribute("pageInfo", pageInfo);
        return "user/listUser";
    }
    
    @RequestMapping("preAddUser")
	public String preAddUser(HttpServletRequest request, Model model) {
		
		return "user/addUser";
	}
	
	@RequestMapping(value="addUser", method = RequestMethod.POST)
	public String addUser(HttpServletRequest request, Model model) {
		
		String userName = request.getParameter("userName");
		String name = request.getParameter("nickName");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");

		User user = new User();
		user.setNickName(name);
		user.setPassword(password);
		user.setUserName(userName);
		user.setUserNameHashId(userName.hashCode());
		user.setPhoneNumber(Long.parseLong(phoneNumber));
		
		if(userName == null || name == null || password == null){
			model.addAttribute("error", "用户名,姓名,密码不能为空");
			return "user/addUser";
		}
		
			User user_old = userService.getUserByUserName(userName);
			if(user_old != null) {
				model.addAttribute("error", "用户名已存在！");
				model.addAttribute("user", user);
				return "user/addUser";
			}
			
			try {
				user.setPassword(PasswordEncode.SHA256(password));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			user.setCreateTime(System.currentTimeMillis());
			userService.saveUser(user);
			
		return "redirect:listUser.do";
	}
	
    @RequestMapping(value="preEditUser", method = RequestMethod.GET)
    public String editUser(HttpServletRequest request, HttpServletResponse response,Model model){
    	
    	 String userName = request.getParameter("userName");
    	 User user = null;
    	 if(userName != null && !"".equals(userName.trim())){
    		 Map<String,Object> parms = new HashMap<>();
             parms.put("userNameHashId",userName.hashCode());
             parms.put("userName",userName);
             user = userService.getUserByName(parms);
    	 }
    	 model.addAttribute("user", user);
    	return "user/editUser";
    }
    @RequestMapping(value="updateUser", method = RequestMethod.POST)
    public String updateUser(HttpServletRequest request, HttpServletResponse response,Model model){
    	
   	 String id = request.getParameter("id");
   	String userName = request.getParameter("userName");
   	
   	String nickName = request.getParameter("nickName");
   	String password = request.getParameter("password");
   	if(nickName == null || "".equals(nickName.trim()) || password == null || "".equals(password.trim())){
   		model.addAttribute("error", "姓名或密码不能为空!");
   		return "user/editUser";
   	}
   	
    Map<String,Object> parms = new HashMap<>();
    parms.put("userNameHashId",userName.hashCode());
    parms.put("userName",userName);
    User user = userService.getUserByName(parms);
    if(!(user.getPassword().equals(password))){
    	try {
			user.setPassword(PasswordEncode.SHA256(password));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }
    user.setNickName(nickName);
    
    userService.updateUserByid(user);
    
   	return "redirect:listUser.do";
   }
    
    
    
	@RequestMapping(value="preAddPermit", method = RequestMethod.GET)
	public String preAddPermit(HttpServletRequest request, Model model) {
		long uid = ParamUtil.getLongParams(request, "uid", 0);
		
		model.addAttribute("uid", uid);
		
		return "user/addPermit";
	}
	
	@RequestMapping(value="delUser", method = RequestMethod.GET)
	public String delUser(HttpServletRequest request, Model model) {
		String ids = ParamUtil.getParams(request, "ids", "");
		userService.delUser(ids);
		
		return "redirect:listUser.do";
	}
	
}

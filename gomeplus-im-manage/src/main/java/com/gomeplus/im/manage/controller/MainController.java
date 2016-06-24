package com.gomeplus.im.manage.controller;

import com.gomeplus.im.manage.pojo.Menu;
import com.gomeplus.im.manage.pojo.User;
import com.gomeplus.im.manage.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/main")
public class MainController {
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(value="main", method = RequestMethod.GET)
	public String main(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginInfo");
		
		List<Menu> list = sysUserService.listUserParentMenu(user.getId());
		for(Menu menu : list) {
			List<Menu> subList = sysUserService.listUserSubMenu(user.getId(), menu.getId());
			menu.setChildren(subList);
		}
		model.addAttribute("menus", list);
		model.addAttribute("user", user);
		model.addAttribute("sessionID", session.getId());
		return "main/index";
	}
	
}

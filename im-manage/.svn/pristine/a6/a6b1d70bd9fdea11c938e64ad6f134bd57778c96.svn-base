package cn.com.gome.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.gome.manage.pojo.Menu;
import cn.com.gome.manage.service.UserService;
import cn.com.gome.manage.utils.ParamUtil;

@Controller
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="listMenu", method = RequestMethod.GET)
	public String listMenu(HttpServletRequest request, Model model) {
		long pid = ParamUtil.getLongParams(request, "pid", 0);
		List<Menu> menus = userService.getMenuByPid(pid);
		model.addAttribute("menus", menus);
		
		return "menu/listMenu";
	}
	
	@RequestMapping(value="preAddMenu", method = RequestMethod.GET)
	public String preAddMenu(HttpServletRequest request, Model model) {
		long pid = ParamUtil.getLongParams(request, "pid", 0);
		long id = ParamUtil.getLongParams(request, "id", 0);
		
		
		if(id > 0) {
			Menu menu = userService.getMenuById(id);
			model.addAttribute("menu", menu);
		}
		if(pid > 0) {
			Menu menu = userService.getMenuById(pid);
			model.addAttribute("pMenu", menu);
		}
		
		return "menu/addMenu";
	}
	
	@RequestMapping(value="addMenu", method = RequestMethod.POST)
	public String addMenu(HttpServletRequest request, Model model) {
		long pid = ParamUtil.getLongParams(request, "pid", 0);
		long id = ParamUtil.getLongParams(request, "id", 0);
		String name = ParamUtil.getParams(request, "name", "");
		String url = ParamUtil.getParams(request, "url", "");
		
		if(id > 0) {
			Menu menu = userService.getMenuById(id);
			menu.setName(name);
			menu.setUrl(url);
			userService.updateMenuByid(menu);
		} else {
			long orderBy = 1;
			List<Menu> list =  userService.getMenuByPid(pid);
			if(list != null && list.size() > 0) {
				Menu m = list.get(list.size() -1);
				orderBy = m.getOrderBy() + 1;
			}
			
			Menu menu = new Menu();
			menu.setPid(pid);
			menu.setName(name);
			menu.setUrl(url);
			menu.setOrderBy(orderBy);
			userService.saveMenu(menu);
		}
		if(pid > 0) {
			Menu menu = userService.getMenuById(pid);
			model.addAttribute("pMenu", menu);
		}
		
		return "redirect:listMenu.do?pid=" + pid;
	}
	
	@RequestMapping(value="delMenu", method = RequestMethod.GET)
	public String delMenu(HttpServletRequest request, Model model) {
		long id = ParamUtil.getLongParams(request, "id", 0);
		long pid = ParamUtil.getLongParams(request, "pid", 0);
		
		userService.delMenu(id);
		
		return "redirect:listMenu.do?pid=" + pid;
	}
	
	@RequestMapping(value="menuOrder", method = RequestMethod.GET)
	public String menuOrder(HttpServletRequest request, Model model) {
		long id = ParamUtil.getLongParams(request, "id", 0);
		long pid = ParamUtil.getLongParams(request, "pid", 0);
		long nearId = ParamUtil.getLongParams(request, "nearId", 0);
		Menu menu = userService.getMenuById(id);
		long order1 = menu.getOrderBy();
		
		Menu nearMenu = userService.getMenuById(nearId);
		long order2 = nearMenu.getOrderBy();
		
		menu.setOrderBy(order2);
		userService.updateMenuByid(menu);
		
		nearMenu.setOrderBy(order1);
		userService.updateMenuByid(nearMenu);
		
		return "redirect:listMenu.do?pid=" + pid;
	}
}

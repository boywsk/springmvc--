package cn.com.gome.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.gome.manage.pojo.Menu;
import cn.com.gome.manage.pojo.User;
import cn.com.gome.manage.service.UserService;

@Controller
@RequestMapping("/main")
public class MainController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="main", method = RequestMethod.GET)
	public String main(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginInfo");
		
		List<Menu> list = userService.listUserParentMenu(user.getId());
		for(Menu menu : list) {
			List<Menu> subList = userService.listUserSubMenu(user.getId(), menu.getId());
			menu.setChildren(subList);
		}
		
		
		model.addAttribute("menus", list);
		model.addAttribute("user", user);
		model.addAttribute("sessionID", session.getId());
		
		
		return "main/index";
	}
	
//	@SuppressWarnings("rawtypes")
//	@RequestMapping(value="listPushConnect", method = RequestMethod.GET)
//	public String listPushConnects(HttpServletRequest request, model model) {
//		List list = new ArrayList();
//		String str = ListPushConnectUtil.listPushConnect("192.168.15.158", 8089);
//		if(str != null && str.length() >= 2) {
//			list = (List)JSON.parseObject(str, List.class);
//		}
//		
//		model.addAttribute("list", list);
//		
//		return "main/listPushConnect";
//	}
}

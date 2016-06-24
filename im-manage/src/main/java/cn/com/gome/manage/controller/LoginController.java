package cn.com.gome.manage.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cn.com.gome.manage.mongodb.model.AppSearchModel;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pageSupport.PageInfoFactory;
import cn.com.gome.manage.pojo.AppInfo;
import cn.com.gome.manage.pojo.Menu;
import cn.com.gome.manage.pojo.User;
import cn.com.gome.manage.service.AppService;
import cn.com.gome.manage.service.UserService;
import cn.com.gome.manage.utils.HttpUtil;
import cn.com.gome.manage.utils.ParamUtil;

@Controller
@RequestMapping("/login")
public class LoginController {
	Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private AppService appService;
	
	@RequestMapping(value="login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		
		return "login/login";
	}
	
	@RequestMapping(value="logon", method = RequestMethod.POST)
	public ModelAndView logon(HttpServletRequest request, Model model) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = userService.getUserByUserName(username);
		if(user == null) {
			return new ModelAndView("login/login","error","用户名错误！");
		} else {
			String pass = user.getPassword();
			if(pass.equals(password)) {
				request.getSession().setAttribute("loginInfo", user);
				//addAppIDListToSession(request,user);
				int pageNo = ParamUtil.getIntParams(request, "page", 1);
				int pageSize = ParamUtil.getIntParams(request, "pagesize", 10);
				PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
				AppSearchModel appSearchModel = new AppSearchModel();
				appSearchModel.setUserId(Long.toString(user.getId()));
				appSearchModel.setType(user.getType());
				List<AppInfo> appInfo = appService.getAppInfo(pageInfo, appSearchModel);
				request.getSession().setAttribute("appIdList", appInfo);
				return new ModelAndView(new RedirectView("../main/main.do"));
			} else {
				return new ModelAndView("login/login","error","密码错误！");
			}
		}
	}
	
	@RequestMapping(value="logout", method = RequestMethod.GET)
	public String main(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		session.removeAttribute("loginInfo");
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping(value="listMenu", method = RequestMethod.GET)
	public void listMenu(HttpServletRequest request, HttpServletResponse response) {
		List<Long> userMemus = userService.listUserMenuId(1);		
		List<Menu> list = userService.getMenuByPid(0L);
		for(Menu menu : list) {
			if(userMemus.indexOf(menu.getId()) >= 0) {
				menu.setSelected(1);
			}
			//List<Menu> subMenus = new ArrayList<Menu>();
			List<Menu> subList = userService.getMenuByPid(menu.getId());
			for(Menu child : subList) {
				if(userMemus.indexOf(child.getId()) >= 0) {
					child.setSelected(1);
				} 
			}
			menu.setChildren(subList);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Rows", list);
		HttpUtil.writeResult(response, map);
	}
	
	@RequestMapping(value="addPermit", method = RequestMethod.POST)
	public String addPermit(HttpServletRequest request, Model model) {
		String menusIds = ParamUtil.getParams(request, "menusIds", "");
		
		//System.out.println(menusIds);
		
		return "";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="checkSid", method = RequestMethod.GET)
	public void checkSessionId(HttpServletRequest request, HttpServletResponse response) {
		String res = "";
		PrintWriter out = null;
		String sessionid = request.getParameter("sid");
		if(sessionid != null && sessionid.length() > 0) {
			ServletContext application = request.getServletContext();
			Map<String, String> online = (Map<String, String>) application.getAttribute("online");
			if(online != null) {
				if(online.containsKey(sessionid)) {
					res = sessionid;
				}
			}
		}
		HttpUtil.writeResult(response, res);
	}
	/**
	 * 增加APPID列表到Session
	 * */
	public void addAppIDListToSession(HttpServletRequest request,User user){
		
	}
}
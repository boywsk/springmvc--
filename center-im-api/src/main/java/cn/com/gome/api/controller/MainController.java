package cn.com.gome.api.controller;

import cn.com.gome.api.model.User;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("main")
public class MainController {
	Logger log = LoggerFactory.getLogger(MainController.class);

//	@Autowired
//	private UserBaseInfoFacade userBaseInfoFacade; //会员相关接口(基本信息+账户安全)
//
//	@RequestMapping(value = "user", method = RequestMethod.GET)
//	@ResponseBody
//	public String index() {
//		CommonResultEntity<UserModel> profile = userBaseInfoFacade.getProfileUserInfoById(11000L);
//
//		if(profile != null)
//			return profile.getMessage() + profile.getCode() + profile.getBusinessObj();
//		return "index";
//	}

	@RequestMapping(value = "user2", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> index2() {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("id", 2);
		modelMap.put("name", "test");
		modelMap.put("age", 99);
		return modelMap;
	}

	@RequestMapping(value = "user3", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> index3() {
		User user = new User();
		user.setId(22);
		user.setName("测试");
		user.setAge(99);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("user", user);
		return modelMap;
	}

	@RequestMapping(value = "get_user", method = RequestMethod.POST)
	@ResponseBody
	public User testPostJson(@RequestParam("id") int id,
			@RequestParam("name") String name, @RequestParam("age") int age) {
		System.out.println("get_user " + id + name + age);
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setAge(age);
		return user;
	}

	@RequestMapping(value = "sysInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> sysInfo() {
		//System.out.println(System.getProperty("java.library.path"));
		Map<String, Object> map = new HashMap<String, Object>();
		Sigar sigar = new Sigar();
		try {
			CpuPerc[] cpuList = sigar.getCpuPercList();
			double cpuRate = 0.0;
			for(CpuPerc cpu : cpuList) {
				double d = cpu.getCombined();
				cpuRate += d;
			}
			cpuRate = cpuRate / (double)cpuList.length;
			BigDecimal b = new BigDecimal(cpuRate);
			double df = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();  
			map.put("CPURate：", df);
			Mem mem = sigar.getMem();
			double d = mem.getUsedPercent();
			b = new BigDecimal(d);
			df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			map.put("MEMRate：", df);
		} catch (Exception e) {
			log.error("sysInfo:", e);
		}

		return map;
	}
	@RequestMapping(value = "uploadTest", method = RequestMethod.GET)
	public String uploadTest() {
		return "upload";
	}

}

package com.gome.im.api.controller;

import com.gome.im.api.db.model.User;
import com.gome.im.api.service.UserService;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("main")
public class MainController {
	Logger log = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "user2", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> index2() {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("id", 2);
		modelMap.put("name", "test");
		modelMap.put("age", 99);
		return modelMap;
	}

	@RequestMapping(value = "get_user", method = RequestMethod.GET)
	@ResponseBody
	public User testPostJson() {
		System.out.println("get_user ");
		User user = new User();
		user.setPhoneNumber("150111111111");
		user.setAvatar("avatar");
		user.setAutograph("autograph");
		user.setBirthday(1223);
		user.setGender(0);
		user.setNickName("john");
		user.setUserName("小明");
		user.setRegion("北京");
		user.setPassword("password");
		user.setCreateTime(System.currentTimeMillis());
		user.setUpdateTime(System.currentTimeMillis());
		userService.saveUser(user);

//		user = userService.getUserByUserId(3);
		user.setId(3);
		user.setUserName("小李");
//		userService.updateUserByUserId(user);
//		userService.delUser(1);
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

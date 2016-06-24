package cn.com.gome.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.gome.manage.dao.ServerMapper;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pojo.Server;
import cn.com.gome.manage.service.ServerService;

@Service
public class ServerServiceImpl implements ServerService {
	@Autowired
	private ServerMapper serverMapper;
	
	public List<Server> listServer(PageInfo pageInfo, Server server) {
		Map<Object,Object> param = new HashMap<Object,Object>();
		int pageNo = pageInfo.getCurrentPage();
		int pageSize = pageInfo.getPageSize();
		
		param.put("sart", (pageNo -1) * pageSize);
		param.put("pageSize", pageSize);
		param.put("serverIp", server.getServerIp());
		param.put("serverPort", server.getServerPort());
		param.put("serverType", server.getServerType());
		
		List<Server> list = serverMapper.listServer(param);
		return list;
	}

}

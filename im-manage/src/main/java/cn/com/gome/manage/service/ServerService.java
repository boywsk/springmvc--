package cn.com.gome.manage.service;

import java.util.List;

import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pojo.Server;

public interface ServerService {
	/**
	 * 分页获取服务信息
	 * @param param
	 * @return
	 */
	public List<Server> listServer(PageInfo pageInfo, Server server);
}

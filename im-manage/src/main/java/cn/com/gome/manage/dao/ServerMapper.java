package cn.com.gome.manage.dao;

import java.util.List;
import java.util.Map;

import cn.com.gome.manage.pojo.Server;

public interface ServerMapper {
	
	/**
	 * 分页获取服务信息
	 * @param param
	 * @return
	 */
	public List<Server> listServer(Map<?, ?> param);
}

package cn.com.gome.api.service;

import cn.com.gome.api.model.ResultModel;
import cn.com.gome.api.model.request.ReqGroup;

public interface GroupService {
	
	/**
	 * 创建群组
	 * @param reqGroup
	 * @return
	 */
	ResultModel<Object> addGroup(ReqGroup reqGroup,String appId);
	
	/**
	 * 加入群
	 * @param reqGroup
	 * @return
	 */
	ResultModel<Object> joinGroup(ReqGroup reqGroup,String appId);
	

	/**
	 * 退群
	 * @param reqGroup
	 * @return
	 */
	ResultModel<Object> quitGroup(ReqGroup reqGroup,String appId);
	
	/**
	 * 踢人
	 * @param reqGroup
	 * @return
	 */
	ResultModel<Object> kickGroup(ReqGroup reqGroup,String appId);
	
	/**
	 * 解散群
	 * @param reqGroup
	 * @return
	 */
	ResultModel<Object> disbandGroup(ReqGroup reqGroup,String appId);

}

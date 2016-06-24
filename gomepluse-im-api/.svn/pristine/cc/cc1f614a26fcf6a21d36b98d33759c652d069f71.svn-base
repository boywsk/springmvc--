package com.gomeplus.im.api.service;


import com.gomeplus.im.api.request.ReqFriendGroup;
import com.gomeplus.im.api.request.response.ResultModel;

/**
 * 好友分组 （特注： 非朋友圈）
 * @author liuzhenhuan
 * @date 2016年6月6日 下午5:08:52 
 * @version V1.0  
 */
public interface FriendGroupService {
	
	/**
	 * 创建（添加好友分组）好友分组
	 * @param req
	 * @return
	 */
	public ResultModel<Object> addFriendGroup(ReqFriendGroup reqFriendGroup,String appId,long userId);
	
	/**
	 * 删除好友分组
	 * @param req
	 * @return
	 */
	public ResultModel<Object> deleteFriendGroup(ReqFriendGroup reqFriendGroup,String appId);


	/**
	 * 查看用户自己创建的好友分组
	 * @param req
	 * @return
	 */
	public ResultModel<Object> getFriendGroup(String appId,long userId);

	/**
	 * 修改自己创建的好友分组信息
	 * @param req
	 * @return
	 */
	public ResultModel<Object> updateFriendGroup(ReqFriendGroup reqFriendGroup,String appId);


}

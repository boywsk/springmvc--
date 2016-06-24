package com.gomeplus.im.api.service;

import com.gomeplus.im.api.model.Friend;
import com.gomeplus.im.api.request.ReqFriend;
import com.gomeplus.im.api.request.response.ResultModel;

public interface FriendService {
	
	/**
	 * 创建好友
	 * @param reqFriend
	 * @param appId 
	 * @param userId
	 * @return
	 */
  public ResultModel<Object> applyAddFriend(ReqFriend reqFriend,String appId,long userId);
	/**
	 * 修改好友
	 * @param reqFriend
	 * @return
	 */
  public ResultModel<Object> updateMark(ReqFriend reqFriend,String appId,long userId);
	/**
	 * 好友列表
	 * @param reqFriend
	 * @return
	 */
  public ResultModel<Object> listFriends(ReqFriend reqFriend,String appId,long userId);
	/**
	 * 删除好友
	 * @param reqFriend
	 * @return
	 */
  public ResultModel<Object> deleteFriend(ReqFriend reqFriend,String appId,long userId);
    /**
     * 好友申请审核
     */
  public ResultModel<Object> auditFriend(ReqFriend reqFriend,String appId,long userId);
  
  /**
	 * 好友列表
	 * @param reqFriend
	 * @return
	 */
  public ResultModel<Object> getFriendsByGroupId(ReqFriend reqFriend,String appId,long userId);
  /**
   * 好友列表
   * @param reqFriend
   * @return
   */
  public ResultModel<Object> getAllFriends(String appId,long userId);
  /**
   * 好友列表
   * @param reqFriend
   * @return
   */
  public ResultModel<Object> updateFriendGroup(ReqFriend reqFriend,String appId,long userId);
  /**
   * 更新用户信息测试
   * @param reqFriend
   * @return
   */
  public ResultModel<Object> updateFriend(Friend reqFriend,String appId);
}

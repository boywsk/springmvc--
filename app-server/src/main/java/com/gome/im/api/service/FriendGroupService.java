package com.gome.im.api.service;


import com.gome.im.api.model.ResultModel;
import com.gome.im.api.model.request.ReqFriend;
import com.gome.im.api.model.request.ReqFriendGroup;

public interface FriendGroupService {
	
	public ResultModel<Object> addFriendGroup(ReqFriendGroup req);
	

	public ResultModel<Object> delFriendGroup(ReqFriendGroup req);


	/**
	 * 查看用户自己发布过的朋友圈信息
	 * @param req
	 * @return
	 */
	public ResultModel<Object> personalFriendGroup(ReqFriendGroup req);

	/**
	 * 查看用户所有朋友发布的朋友圈信息
	 * @param req
	 * @return
	 */
	public ResultModel<Object> listFriendGroup(ReqFriendGroup req);


}

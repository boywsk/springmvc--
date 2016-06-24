package com.gome.im.api.service;


import com.gome.im.api.model.ResultModel;
import com.gome.im.api.model.request.ReqFriend;

public interface FriendService {
	
	/**
	 * 添加好友
	 * @param reqFriend
	 * @return
	 */
	public ResultModel<Object> addFriend(ReqFriend reqFriend);
	
	/**
	 * 好友申请审核
	 * @param reqFriend
	 * @return
	 */
	public ResultModel<Object> auditFriend(ReqFriend reqFriend);
	
	/**
	 * 删除好友关系
	 * @param reqFriend
	 * @return
	 */
	public ResultModel<Object> delFriend(ReqFriend reqFriend);

	/**
	 * 修改好友备注
	 * @param reqFriend
	 * @return
	 */
	public ResultModel<Object> setMark(ReqFriend reqFriend);
	
	/**
	 * 根据时间和状态获取好友列表
	 * @return
	 */
	public ResultModel<Object> listFriend(ReqFriend reqFriend);

	/**
	 * 检查用户id 和 好友id 是否异常
	 * @param uid  用户id
	 * @param fid 好友id
	 * @return 返回null，则需要继续处理。返回非null，则将数据作为返回值下发
	 */
	public ResultModel<Object> checkId(long uid, long fid);

}

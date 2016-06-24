package cn.com.gome.manage.service;

import java.util.List;

import cn.com.gome.manage.mongodb.model.TUserInfo;
import cn.com.gome.manage.mongodb.model.UserSearchModel;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pojo.UserInfo;

/**
 * im用户信息service层接口
 */
public interface UserInfoService {
	
	/**
	 * 分页获取im用户列表--分库分表后的查询160414
	 * @param userSearchModel，pageInfo
	 * @return
	 */
	public List<TUserInfo> listTUserInfo(UserSearchModel userSearchModel,PageInfo pageInfo);
	
	/**
	 * 分页获取im用户列表
	 * @param pageInfo
	 * @return
	 */
	public List<UserInfo> listUserInfo(PageInfo pageInfo);
	
	/**
	 * 根据昵称分页查询im用户
	 * @param nickName
	 * @param pageInfo
	 * @return
	 */
	public List<UserInfo> searchUserInfo(String nickName, PageInfo pageInfo);
}

package com.gomeplus.im.api.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSON;
import com.gomeplus.im.api.global.Constant;
import com.gomeplus.im.api.request.ReqGroupMember;

public class GroupTest extends ApiTester{
	public static final String GROUP = "/group";
	
	private static final String GROUP_INFO_SUFFIX = "_info";
	private static final String MEMBER_INFO_SUFFIX = "_member";
	private static final String USER_GROUPS_SUFFIX = "_gids";
	private static final String GROUP_MEMBERS_SUFFIX = "_members";

	@Test
	public void addGroupTest() {
		
		String token="497dfeff5a4449d086dacc983ef9a431";
		long userId=33;
		String urlParams = getURLParams(token, userId);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupName", "刘贞环群组测试5");
		
		List<ReqGroupMember> memberList=new ArrayList<ReqGroupMember>();
		for (int i = 35; i <=37; i+=2) {
			ReqGroupMember member=new ReqGroupMember();
			if (i<10) {
				member.setIdentity(Constant.GROUP_MEMBER_IDENTITY.E_MEMBER_ORDINARY.value);
			}else {
				member.setIdentity(Constant.GROUP_MEMBER_IDENTITY.E_MEMBER_MANAGER.value);
			}
			member.setUserId(i);
			member.setNickName("刘贞环昵称"+i);//
			member.setMark("贞环备注"+i);
			memberList.add(member);
		}
		
		params.put("members", memberList);
		params.put("isAudit", "1");
		params.put("nickName", "刘贞环222");
		String result = post(GOMEPLUS_IM_API + GROUP
				+ "/addGroup.json"+urlParams, params);
		outputResultInfo(GOMEPLUS_IM_API + GROUP
				+ "/addGroup.json"+urlParams, result);
	}
	@Test
	public void getRedisTest(){
		JedisCluster jedis=JedisClusterClientTest.INSTANCE.getJedisCluster();
		String groupId = "58c5f73e9f824969b5b0f317a2d17002";
		long userId = 1L;
		String appId=APP_ID;
	    //  缓存用户的群组（创建的群组） redim set ( key = appId + "_" + userId +"_"+suffix)
		String userGroupIdsKey = appId +"_" + userId + USER_GROUPS_SUFFIX;
		Set<String> userGroupIdsValue = jedis.smembers(userGroupIdsKey);
		System.out.println("userGroupIdsKey:"+userGroupIdsKey+",userGroupIdsValue:"+userGroupIdsValue);
//		
		
//	//  缓存群组信息 redis string  ( key = appId+"_"+groupId +"_"+suffix)
		String groupInfoKey = appId + "_" + groupId + GROUP_INFO_SUFFIX;
		String groupInfoValue = jedis.get(groupInfoKey);
		System.out.println("groupInfoKey:"+groupInfoKey+",groupInfoValue:"+groupInfoValue);
//		
//		
//		//  缓存群组的成员 redis set ( key = appId + "_" + groupId +"_"+suffix)
//		List<String> userIdList=new ArrayList<String>();
		String groupMemberKey ="";
		String groupMembersKey = appId + "_" + groupId + GROUP_MEMBERS_SUFFIX;
		Set<String> groupMembersValue = jedis.smembers(groupMembersKey);
		System.out.println("groupMemberKey:"+groupMembersKey+",groupMemberValue: "+groupMembersValue);
		System.out.println("====groupMember======");
		
		for (String strMemeberUserId : groupMembersValue) {
			long memberUserId = Long.valueOf(strMemeberUserId);
			//缓存群组成员信息 redis string ( key = appId +"_"+groupId+"_"+ userId +"_"+ suffix)
			groupMemberKey = appId +"_"+groupId+"_"+memberUserId + MEMBER_INFO_SUFFIX;
			
			String groupMemberValue = jedis.get(groupMemberKey);
			System.out.println("groupMemberKey:"+groupMemberKey+",groupMemberValue: "+groupMemberValue);
		}
//		
		
	}
	@Test
	public void getRedisUserTest(){
		JedisCluster jedis=JedisClusterClientTest.INSTANCE.getJedisCluster();
		String groupId = "58c5f73e9f824969b5b0f317a2d17002";
		long userId = 1L;
		String appId=APP_ID;
		//  缓存用户的群组（创建的群组） redim set ( key = appId + "_" + userId +"_"+suffix)
		String userGroupIdsKey = appId +"_" + userId + USER_GROUPS_SUFFIX;
		Set<String> userGroupIdsValue = jedis.smembers(userGroupIdsKey);
		System.out.println("userGroupIdsKey:"+userGroupIdsKey+",userGroupIdsValue:"+userGroupIdsValue);
//		
		
//	//  缓存群组信息 redis string  ( key = appId+"_"+groupId +"_"+suffix)
		String groupInfoKey = appId + "_" + groupId + GROUP_INFO_SUFFIX;
		String groupInfoValue = jedis.get(groupInfoKey);
		System.out.println("groupInfoKey:"+groupInfoKey+",groupInfoValue:"+groupInfoValue);
//		
//		
//		//  缓存群组的成员 redis set ( key = appId + "_" + groupId +"_"+suffix)
//		List<String> userIdList=new ArrayList<String>();
		String groupMemberKey ="";
		String groupMembersKey = appId + "_" + groupId + GROUP_MEMBERS_SUFFIX;
		Set<String> groupMembersValue = jedis.smembers(groupMembersKey);
		System.out.println("groupMemberKey:"+groupMembersKey+",groupMemberValue: "+groupMembersValue);
		System.out.println("====groupMember======");
		
		for (String strMemeberUserId : groupMembersValue) {
			long memberUserId = Long.valueOf(strMemeberUserId);
			//缓存群组成员信息 redis string ( key = appId +"_"+groupId+"_"+ userId +"_"+ suffix)
			groupMemberKey = appId +"_"+groupId+"_"+memberUserId + MEMBER_INFO_SUFFIX;
			
			String groupMemberValue = jedis.get(groupMemberKey);
			System.out.println("groupMemberKey:"+groupMemberKey+",groupMemberValue: "+groupMemberValue);
		}
//		
		
	}
	
	@Test
	public void joinGroupTest() {
		String token="497dfeff5a4449d086dacc983ef9a431";
		long userId=41;
		String urlParams = getURLParams(token, userId);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupId", "417894bd37804cdcb2fea891d70901cc");
		
		
		String result = post(GOMEPLUS_IM_API + GROUP
				+ "/joinGroup.json"+urlParams, params);
		outputResultInfo(GOMEPLUS_IM_API + GROUP
				+ "/joinGroup.json"+urlParams, result);
	}
	@Test
	public void dateTest(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String format = sdf.format(new Date(1463639557748L));
		System.out.println(Integer.valueOf(format));
		
	}
	@Test
	public void inviteGroupTest() {
		String token="497dfeff5a4449d086dacc983ef9a431";
		long userId=37;
		String urlParams = getURLParams(token, userId);
		Map<String , Object> params=new HashMap<String, Object>();
		List<ReqGroupMember> memberList=new ArrayList<ReqGroupMember>();
		for (int i = 53; i <=57; i+=2) {
			ReqGroupMember member=new ReqGroupMember();
			if (i<10) {
				member.setIdentity(Constant.GROUP_MEMBER_IDENTITY.E_MEMBER_ORDINARY.value);
			}else {
				member.setIdentity(Constant.GROUP_MEMBER_IDENTITY.E_MEMBER_MANAGER.value);
			}
			member.setUserId(i);
			member.setNickName("刘贞环昵称"+i);//
			memberList.add(member);
		}
		
		params.put("members", memberList);
//		params.put("isAudit", 1);
		params.put("groupId", "520b51af80de48a4a1ad82852dae13a2");
		
		
		String result = post(GOMEPLUS_IM_API + GROUP
				+ "/inviteGroup.json"+urlParams, params);
		outputResultInfo(GOMEPLUS_IM_API + GROUP
				+ "/joinGroup.json"+urlParams, result);
	}
	@Test
	public void disbandGroupTest() {
		String token="497dfeff5a4449d086dacc983ef9a431";
		long userId=33;
		String urlParams = getURLParams(token, userId);
		Map<String , Object> params=new HashMap<String, Object>();
		params.put("groupId", "8de1b60a09284fb2b6603744c5d79ca7");
		
		
		String result = post(GOMEPLUS_IM_API + GROUP
				+ "/disbandGroup.json"+urlParams, params);
		outputResultInfo(GOMEPLUS_IM_API + GROUP
				+ "/disbandGroup.json"+urlParams, result);
	}
	@Test
	public void quitGroupTest() {
		String token="497dfeff5a4449d086dacc983ef9a431";
		long userId=37;
		String urlParams = getURLParams(token, userId);
		Map<String , Object> params=new HashMap<String, Object>();
		params.put("groupId", "520b51af80de48a4a1ad82852dae13a2");
		
		
		String result = post(GOMEPLUS_IM_API + GROUP
				+ "/quitGroup.json"+urlParams, params);
		outputResultInfo(GOMEPLUS_IM_API + GROUP
				+ "/quitGroup.json"+urlParams, result);
	}
	@Test
	public void kickGroupTest() {
		String token="497dfeff5a4449d086dacc983ef9a431";
		long userId=33;
		String urlParams = getURLParams(token, userId);
		Map<String , Object> params=new HashMap<String, Object>();
		params.put("groupId", "520b51af80de48a4a1ad82852dae13a2");
		List<ReqGroupMember> memberList=new ArrayList<ReqGroupMember>();
		for (int i = 53; i <=57; i+=2) {
			ReqGroupMember member=new ReqGroupMember();
			member.setUserId(i);
			memberList.add(member);
		}
		
		params.put("members", memberList);
		
		String result = post(GOMEPLUS_IM_API + GROUP
				+ "/kickGroup.json"+urlParams, params);
		outputResultInfo(GOMEPLUS_IM_API + GROUP
				+ "/kickGroup.json"+urlParams, result);
	}
	@Test
	public void editGroupTest() {
		String token="497dfeff5a4449d086dacc983ef9a431";
		long userId=33;
		String urlParams = getURLParams(token, userId);
		Map<String , Object> params=new HashMap<String, Object>();
		params.put("groupId", "520b51af80de48a4a1ad82852dae13a2");
//		group.setGroupName(reqGroup.getGroupName());
//        group.setGroupDesc(reqGroup.getDesc());
//        group.setAvatar(reqGroup.getAvatar());
//        group.setqRcode(reqGroup.getqRCode());
//        group.setCapacity(reqGroup.getCapacity());
//        group.setIsAudit(reqGroup.getIsAudit());
//        group.setIsTop(reqGroup.getIsTop());
		params.put("groupName", "20160622贞环修改群信息测试");
		params.put("desc", "信息22");
		params.put("isTop", "122");
		
		String result = post(GOMEPLUS_IM_API + GROUP
				+ "/editGroup.json"+urlParams, params);
		outputResultInfo(GOMEPLUS_IM_API + GROUP
				+ "/editGroup.json"+urlParams, result);
	}
	@Test
	public void auditMemberTest() {
		String token="497dfeff5a4449d086dacc983ef9a431";
		long userId=1;
		String urlParams = getURLParams(token, userId);
		List<ReqGroupMember> reqGroupMemberList=new ArrayList<ReqGroupMember>();
		ReqGroupMember reqGroupMember = new ReqGroupMember();
		reqGroupMember.setUserId(13);
		reqGroupMember.setStatus(Constant.GROUP_MEMBER_STATUS.E_MEMBER_STATUS_YES.value);
		reqGroupMemberList.add(reqGroupMember);
		
		Map<String , Object> params=new HashMap<String, Object>();
		params.put("groupId", "aa3c403db1274dd3a8630b765605c1d9");
		params.put("status",Constant.GROUP_STATUS.E_GROUP_STATUS_OK.value);
		params.put("members", reqGroupMemberList);
		String result = post(GOMEPLUS_IM_API + GROUP
				+ "/auditMember.json"+urlParams, params);
		outputResultInfo(GOMEPLUS_IM_API + GROUP
				+ "/auditMember.json"+urlParams, result);
	}
	@Test
	public void getGroupTest() {
		String token="497dfeff5a4449d086dacc983ef9a431";
		long userId=33;
		String urlParams = getURLParams(token, userId);
		
		Map<String , Object> params=new HashMap<String, Object>();
		params.put("groupId", "81224118fa6d49ffaa2fce54c21a6b2a");
		params.put("status", Constant.GROUP_STATUS.E_GROUP_STATUS_OK.value+"");
		params.put("page", "1");
		String result = post(GOMEPLUS_IM_API + GROUP
				+ "/getGroup.json"+urlParams, params);
		outputResultInfo(GOMEPLUS_IM_API + GROUP
				+ "/getGroup.json"+urlParams, result);
	}
	
	@Test
	public void listGroupTest() {
		String token="497dfeff5a4449d086dacc983ef9a431";
		long userId=1;
		String urlParams = getURLParams(token, userId);
		
		String result = post(GOMEPLUS_IM_API + GROUP
				+ "/listGroup.json"+urlParams, null);
		outputResultInfo(GOMEPLUS_IM_API + GROUP
				+ "/listGroup.json"+urlParams, result);
	}
	
	@Test
	public void editMemberMarkTest() {
		String token="497dfeff5a4449d086dacc983ef9a431";
		long userId=1;
		String urlParams = getURLParams(token, userId);
		
		Map<String , Object> params=new HashMap<String, Object>();
		params.put("groupId", "0df0146c01114418b1dada3ffbc99a5a");
		params.put("markedUserId", "11");
		params.put("mark", "贞环测试保存备注1-保存11");
		String result = post(GOMEPLUS_IM_API + GROUP
				+ "/editMemberMark.json"+urlParams, params);
		outputResultInfo(GOMEPLUS_IM_API + GROUP
				+ "/editMemberMark.json"+urlParams, result);
	}
	@Test
	public void shieldGroupTest() {
		String token="497dfeff5a4449d086dacc983ef9a431";
		long userId=11;
		String urlParams = getURLParams(token, userId);
		
		Map<String , Object> params=new HashMap<String, Object>();
		params.put("groupId", "0df0146c01114418b1dada3ffbc99a5a");
		params.put("isTop", Constant.GROUP_MEMBER_TOP.E_MEMBER_TOP_YES.value);
		String result = post(GOMEPLUS_IM_API + GROUP
				+ "/sickiesGroup.json"+urlParams, params);
		outputResultInfo(GOMEPLUS_IM_API + GROUP
				+ "/shieldGroup.json"+urlParams, result);
	}
}





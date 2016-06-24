package com.gomeplus.im.api.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class FriendGroupTest extends ApiTester {
	public static final String FRIEND_GROUP = "/friendGroup";

	@Test
	public void addFriendGroupTest() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", 123L);
		params.put("groupName", "group1");

		String result = post(GOMEPLUS_IM_API + FRIEND_GROUP
				+ "/addFriendGroup.json", params);
		outputResultInfo(GOMEPLUS_IM_API + FRIEND_GROUP
				+ "/addFriendGroup.json", result);
	}
	@Test
	public void deleteFriendGroupTest() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", "9");
		
		String result = post(GOMEPLUS_IM_API + FRIEND_GROUP
				+ "/deleteFriendGroup.json"+"?appId=123", params);
		outputResultInfo(GOMEPLUS_IM_API + FRIEND_GROUP
				+ "/deleteFriendGroup.json"+"?appId=123", result);
	}
	@Test
	public void getFriendGroupTest() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", 1234);
		
		String result = post(GOMEPLUS_IM_API + FRIEND_GROUP
				+ "/getFriendGroup.json", params);
		outputResultInfo(GOMEPLUS_IM_API + FRIEND_GROUP
				+ "/getFriendGroup.json", result);
	}
	@Test
	public void updateFriendGroupTest() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", "7");
		params.put("groupName", "liuzhenhuan3");
		
		String result = post(GOMEPLUS_IM_API + FRIEND_GROUP
				+ "/updateFriendGroup.json", params);
		outputResultInfo(GOMEPLUS_IM_API + FRIEND_GROUP
				+ "/updateFriendGroup.json", result);
	}

}

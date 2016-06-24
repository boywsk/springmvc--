package com.gomeplus.im.api.schedule;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.JedisCluster;

import com.gomeplus.im.api.dao.FriendMapper;
import com.gomeplus.im.api.global.Global;
import com.gomeplus.im.api.model.Friend;
import com.gomeplus.im.api.model.GroupMember;
import com.gomeplus.im.api.mongo.GroupMemberDao;
import com.gomeplus.im.api.utils.Chinese2Spell;
import com.gomeplus.im.api.utils.JedisClusterClient;

@Service
@Qualifier("updateScheduleTask")
public class UpdateScheduleTask {

	private static final Logger logger = LoggerFactory
			.getLogger(UpdateScheduleTask.class);

	@Autowired
	private FriendMapper friendMapper;

	private GroupMemberDao groupMemberDao = new GroupMemberDao();

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateNickName() {
		JedisCluster jedis = JedisClusterClient.INSTANCE.getJedisCluster();
		long lockCode = jedis.setnx(Global.REDIS_LOCK_KEY,Global.REDIS_LOCK_KEY);
		try {
			if (lockCode == 1) {
				logger.info("===============================开始更新用户昵称======");
				// 计算更新的时间
				long startTime = System.currentTimeMillis();
				Set<String> appIds = jedis.smembers(Global.ALL_APP_KEY);
				if (CollectionUtils.isEmpty(appIds)) {
					return;
				}
				for (String appId : appIds) {
					// 1 查出所有的已经修改昵称的用户ID集合
					String key = appId + Global.TEMP_CHANGE_NIKENAME_IDS;
					Set<String> userIdSet = jedis.smembers(key);
					if (CollectionUtils.isEmpty(userIdSet)) {
						logger.info("没有修改昵称的用户");
						continue;
					}

					// 2 遍历集合更新所有的好友表（
					// (1)根据用户ID更新mysql中的Friend中的昵称;
					// (2)根据用户ID更新mongodb中的群组成员(GroupMember)的昵称

					for (String userId : userIdSet) {
						//1.更新好友昵称
						Friend friend = new Friend();
						friend.setFriendUserId(Long.valueOf(userId));
						String userNickNameKey = userId + Global.TEMP_CHANGE_USERID_NICKNAME_SUFFIX;
						String nickName = jedis.get(userNickNameKey);
						friend.setFriendNickName(nickName);
						friend.setFirstNickNameSpell(Chinese2Spell.converterToFirstSpell(nickName));
						friend.setUpdateTime(startTime);
						friendMapper.updateNickNameByFUid(friend);
						
						//2.更新群组成员的昵称
						List<GroupMember> groupMembers = groupMemberDao.listMemberGroups(appId,Long.valueOf(userId));
						for(GroupMember member : groupMembers){
							String groupId = member.getGroupId();
							groupMemberDao.updateNickName(appId, groupId, Long.valueOf(userId), nickName);
						}
						jedis.del(userNickNameKey);
					}
					// 3.删除redis中的key值
					jedis.del(key);
					long endTime = System.currentTimeMillis();
					logger.info("更新昵称所用的时间为：{}毫秒", endTime - startTime);
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			jedis.del(Global.ALL_APP_KEY);
			jedis.del(Global.REDIS_LOCK_KEY);
		}
	}

}

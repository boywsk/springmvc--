package cn.com.gome.api.dao;

import cn.com.gome.api.global.Constant;
import cn.com.gome.api.model.GroupMember;
import cn.com.gome.api.model.GroupMemberMark;
import cn.com.gome.api.utils.BeanTransUtils;
import cn.com.gome.api.utils.JedisClusterClient;
import cn.com.gome.api.utils.JedisUtil;
import cn.com.gome.api.utils.Pinyin4jUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

/**
 * 群成员数据库操作类
 */
public class GroupMemberDao extends BaseDao {
	private static Logger log = LoggerFactory.getLogger(GroupMemberDao.class);
	private final static String collName = "t_group_member";

	/**
	 * redis中添加群组成员uid
	 *
	 * @param appId
	 * @param groupId
	 * @param uids
	 */
	private static void addGroupMembersUid2Redis(String appId, String groupId, List<Long> uids) {
		JedisCluster jedis = JedisClusterClient.INSTANCE.getJedisCluster();
		String key = appId + "_" + groupId;
		log.info("addGroupMembersUid2Redis start,key:{},groupMember:{}", key, uids.toString());
		try {
			if (uids != null && !uids.isEmpty()) {
				List<String> list = convertStringList(uids);
				String[] groupMembers = list.toArray(new String[list.size()]);
				jedis.sadd(key, groupMembers);
				log.info("addGroupMembersUid2Redis success,key:{},groupMember:{}", key, list.toString());
			}
		} catch (Exception e) {
			log.error("addGroupMembersUid2Redis failed,error:{},key:{},groupMember:{}", e, key, uids.toString());
			e.printStackTrace();
		}
	}

	private static List<String> convertStringList(List<Long> uids){
		List<String> list = new ArrayList<>();
		for(Long uid : uids){
			list.add(String.valueOf(uid));
		}
		return list;
	}
	/**
	 * 移除redis中群组成员uid
	 *
	 * @param appId
	 * @param groupId
	 * @param uids
	 */
	private static void delGroupMembersUid2Redis(String appId, String groupId, List<Long> uids) {
		JedisCluster jedis = JedisClusterClient.INSTANCE.getJedisCluster();
		String key = appId + "_" + groupId;
		log.info("delGroupMembersUid2Redis start,key:{},groupMember:{}", key, uids.toString());
		try {
			if (uids != null && !uids.isEmpty()) {
				List<String> list = convertStringList(uids);
				String[] groupMembers = list.toArray(new String[list.size()]);
				jedis.srem(key, groupMembers);
				log.info("delGroupMembersUid2Redis success,key:{},groupMember:{}", key, list.toString());
			}
		} catch (Exception e) {
			log.error("delGroupMembersUid2Redis failed,error:{},key:{},groupMember:{}", e, key, uids.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 移除redis中全部成员
	 *
	 * @param appId
	 * @param groupId
	 */
	private static void delAllGroupMember2Redis(String appId, String groupId) {
		JedisCluster jedis = JedisClusterClient.INSTANCE.getJedisCluster();
		String key = appId + "_" + groupId;
		log.info("delAllGroupMember2Redis start,key:{}", key);
		try {
			jedis.del(key);
			log.info("delAllGroupMember2Redis success,key:{}", key);
		} catch (Exception e) {
			log.error("delAllGroupMember2Redis failed,error:{},key:{}", e.getMessage(), key);
		}
	}

	/**
	 * 保存群成员信息
	 * @return 是否插入成功
	 */
	public boolean save(String appId,GroupMember groupMember) {
		//加入到redis
		List<Long> list = new ArrayList<>();
		list.add(groupMember.getUid());
		addGroupMembersUid2Redis(appId, groupMember.getGroupId(), list);
		//加入到mongodb
		String dbName = getDatabaseName(appId);
		Document doc = BeanTransUtils.bean2Document(groupMember);
		return insert(dbName, collName, doc);
	}

	/**
	 * 修改seq
	 * @param groupMember
	 * @return
	 */
	public boolean updateSeq(String appId,GroupMember groupMember) {
		String dbName = getDatabaseName(appId);
		String groupId = groupMember.getGroupId();
		long uid = groupMember.getUid();
		long initSeq = groupMember.getInitSeq();
		long readSeq = groupMember.getReadSeq();
		
		BasicDBObject filter = new BasicDBObject();
		filter.put("groupId", groupId);
		filter.put("uid", uid);

		Document newdoc = new Document();
		if(initSeq >= 0) {
			newdoc.put("initSeq", initSeq);
		}
		if(readSeq >= 0) {
			newdoc.put("readSeq", readSeq);
		}
		newdoc.put("updateTime", System.currentTimeMillis());

		return update(dbName, collName, filter, newdoc);
	}
	
	/**
	 * 修改成员状态
	 * @param groupId
	 * @param uid
	 * @param status
	 * @return
	 */
	public boolean updateStatus(String appId,String groupId, long uid, int status) {
		String dbName = getDatabaseName(appId);
		BasicDBObject filter = new BasicDBObject();
		filter.put("groupId", groupId);
		filter.put("uid", uid);

		Document newdoc = new Document();
		if(status >= 0) {
			newdoc.put("status", status);
		}
		newdoc.put("updateTime", System.currentTimeMillis());

		return update(dbName, collName, filter, newdoc);
	}
	
	/**
	 * 修改seq和成员状态
	 * @param groupMember
	 * @param status
	 * @return
	 */
	public boolean updateSeqAndStatus(String appId,GroupMember groupMember, int status) {
		String dbName = getDatabaseName(appId);
		String groupId = groupMember.getGroupId();
		long uid = groupMember.getUid();
		long initSeq = groupMember.getInitSeq();
		long readSeq = groupMember.getReadSeq();
		
		BasicDBObject filter = new BasicDBObject();
		filter.put("groupId", groupId);
		filter.put("uid", uid);

		Document newdoc = new Document();
		if(initSeq >= 0) {
			newdoc.put("initSeq", initSeq);
		}
		if(readSeq >= 0) {
			newdoc.put("readSeq", readSeq);
		}
		if(status >= 0) {
			newdoc.put("status", status);
		}
		newdoc.put("updateTime", System.currentTimeMillis());

		return update(dbName, collName, filter, newdoc);
	}
	
	/**
	 * 修改identity
	 * @param groupId
	 * @param uid
	 * @param identity
	 * @return
	 */
	public boolean updateIdentity(String appId,String groupId, long uid, int identity) {
		String dbName = getDatabaseName(appId);
		BasicDBObject filter = new BasicDBObject();
		filter.put("groupId", groupId);
		filter.put("uid", uid);

		Document newdoc = new Document();
		if(identity >= 0) {
			newdoc.put("identity", identity);
		}
		newdoc.put("updateTime", System.currentTimeMillis());

		return update(dbName, collName, filter, newdoc);
	}
	
	/**
	 * 修改屏蔽群消息状态
	 * @param groupId
	 * @param uid
	 * @param isShield
	 * @return
	 */
	public boolean updateShield(String appId,String groupId, long uid, int isShield) {
		String dbName = getDatabaseName(appId);
		BasicDBObject filter = new BasicDBObject();
		filter.put("groupId", groupId);
		filter.put("uid", uid);

		Document newdoc = new Document();
		if(isShield >= 0) {
			newdoc.put("isShield", isShield);
		}
		newdoc.put("updateTime", System.currentTimeMillis());

		return update(dbName, collName, filter, newdoc);
	}
	
	/**
	 * 修改群置顶状态
	 * @param groupId
	 * @param uid
	 * @param stickies
	 * @return
	 */
	public boolean updateStickies(String appId,String groupId, long uid, int stickies) {
		String dbName = getDatabaseName(appId);
		BasicDBObject filter = new BasicDBObject();
		filter.put("groupId", groupId);
		filter.put("uid", uid);
		Document newdoc = new Document();
		if(stickies >= 0) {
			newdoc.put("stickies", stickies);
		}
		newdoc.put("updateTime", System.currentTimeMillis());

		return update(dbName, collName, filter, newdoc);
	}
	
	/**
	 * 群成员修改自己的群昵称
	 * @param uid
	 * @param groupId
	 * @param selfMark
	 * @return
	 */
	public boolean udateSelfMark(String appId,long uid, String groupId, String selfMark) {
		String dbName = getDatabaseName(appId);
		BasicDBObject filter = new BasicDBObject();
		filter.put("groupId", groupId);
		filter.put("uid", uid);
		
		Document newdoc = new Document();
		newdoc.put("selfMark", selfMark);
		String[] pinyinArr = Pinyin4jUtil.getPinYinAndHeadChar(selfMark);
		newdoc.put("selfMarkSpell", pinyinArr[0]);
		newdoc.put("selfMarkHeadChar", pinyinArr[1]);
		newdoc.put("updateTime", System.currentTimeMillis());
		return update(dbName, collName, filter, newdoc);
	}

	/**
	 * 批量保存群成员信息
	 * 
	 * @return 是否插入成功
	 */
	public boolean save(String appId,List<GroupMember> groupMembers) {
		List<Long> list = new ArrayList<>();
		String groupId = "";
		//加入到mongodb
		String dbName = getDatabaseName(appId);
		List<Document> docs = new ArrayList<Document>();
		for (int i = 0; i < groupMembers.size(); i++) {
			GroupMember groupMember = groupMembers.get(i);
			Document doc = BeanTransUtils.bean2Document(groupMember);
			docs.add(doc);
			list.add(groupMember.getUid());
			if(groupId.isEmpty()){
				groupId = groupMember.getGroupId();
			}
		}
		//加入到redis
		addGroupMembersUid2Redis(appId,groupId,list);
		return insertBatch(dbName, collName, docs);
	}

	/**
	 * 删除群组关系信息(删除单条)
	 * @return boolean 是否删除成功
	 */
	public int delGroupMemberById(String appId,String id) {
		String dbName = getDatabaseName(appId);
		return deleteById(dbName, collName, id);
	}

	/**
	 * 根据 群组id删除群组所有成员
	 * @return 是否删除成功
	 */
	public boolean delGroupAllMember(String appId,String groupId) {
		//从redis移除
		delAllGroupMember2Redis(appId, groupId);
		//从mongodb移除
		String dbName = getDatabaseName(appId);
		BasicDBObject del = new BasicDBObject();
		del.put("groupId", groupId);
		return delete(dbName, collName, del);
	}
	
	/**
	 * 根据 群组id 和 用户id 删除记录
	 * 
	 * @return 是否删除成功
	 */
	public boolean delGroupMember(String appId,String groupId, long uid) {
		//从redis移除
		List<Long> list = new ArrayList<>();
		list.add(uid);
		delGroupMembersUid2Redis(appId, groupId, list);
		//从mongodb移除
		String dbName = getDatabaseName(appId);
		BasicDBObject del = new BasicDBObject();
		del.put("groupId", groupId);
		del.put("uid", uid);
		return delete(dbName, collName, del);
	}

	/**
	 * 删除多个成员
	 *
	 * @return 是否删除成功
	 */
	public boolean delGroupMembers(String appId,String groupId, List<Long> members) {
		List<Long> list = new ArrayList<>();
		//从mongodb移除
		String dbName = getDatabaseName(appId);
		BasicDBObject[] deleteObjArr = new BasicDBObject[members.size()];
		for (int i = 0; i < members.size(); i++) {
			long memberId = members.get(i);
			list.add(memberId);
			BasicDBObject del = new BasicDBObject();
			del.put("groupId", groupId);
			del.put("uid", memberId);
			deleteObjArr[i] = del;
		}
		//从redis移除
		delGroupMembersUid2Redis(appId, groupId, list);
		BasicDBObject delObject = new BasicDBObject().append(QueryOperators.OR, deleteObjArr);
		return deleteAll(dbName, collName, delObject);
	}

	/**
	 * 设置群成员读取群消息的位置
	 * @return 是否设置成功
	 */
	public boolean setReadSeq(String appId,String groupId, long uid, long readSeq) {
		String dbName = getDatabaseName(appId);
		BasicDBObject filter = new BasicDBObject();
		filter.put("groupId", groupId);
		filter.put("uid", uid);
		Document newdoc = new Document();
		newdoc.put("readSeq", readSeq);

		return update(dbName, collName, filter, newdoc);
	}

	/**
	 * 获取成员读取群消息的位置
	 * @return 读取群消息的位置 如果是-1，表示读取失败
	 */
	public long getReadSeq(String appId,String groupId, long uid) {
		String dbName = getDatabaseName(appId);
		MongoCursor<Document> cursor = null;
		long readSeq = -1L;
		try {
			BasicDBObject filter = new BasicDBObject();
			filter.put("groupId", groupId);
			filter.put("uid", uid);

			cursor = find(dbName, collName, filter);
			if (cursor.hasNext()) {
				Document item = cursor.next();
				readSeq = item.getLong("readSeq");
			}
		} catch (Exception e) {
			log.error("GroupMemberDao getReadSeq:", e);
		} finally {
			cursorClose(cursor);
		}
		return readSeq;
	}
	
	/**
	 * 获取群内 所有已通过的成员关系
	 * @param groupId
	 * @return
	 */
	public List<GroupMember> listGroupMembers(String appId,String groupId) {
		String dbName = getDatabaseName(appId);
		List<GroupMember> list = new ArrayList<GroupMember>();
		MongoCollection<Document> coll = this.getCollection(dbName, collName);
		BasicDBObject where = new BasicDBObject();
		where.put("groupId", groupId);
		//where.put("status", Constant.GROUP_STATUS.E_GROUP_STATUS_OK.value);
		MongoCursor<Document> cursor = null;
		cursor = coll.find(where).iterator();
		if(cursor == null) {
			return list;
		}
		while (cursor.hasNext()) {
			Document item = cursor.next();
			GroupMember been = (GroupMember) BeanTransUtils.document2Bean(item, GroupMember.class);
			list.add(been);
		}
		
		return list;
	}

	/**
	 * 分页获取群内 所有成员关系
	 * @param groupId
	 * @param time
	 */
	public List<GroupMember> listGroupMembers(String appId,String groupId, long time, int status, int size) {
		String dbName = getDatabaseName(appId);
		List<GroupMember> list = new ArrayList<GroupMember>();
		MongoCollection<Document> coll = this.getCollection(dbName, collName);
		BasicDBObject where = new BasicDBObject();
		where.put("groupId", groupId);
		if(status >= 0){
			where.put("status", status);
		}
		where.put("updateTime", new BasicDBObject("$gte", time));
		Bson sort = new BasicDBObject("joinTime", 1);
		MongoCursor<Document> cursor = null;
		if(size > 0) {
			cursor = coll.find(where).sort(sort).limit(size).iterator();
		} else {
			cursor = coll.find(where).sort(sort).iterator();
		}
		if(cursor == null) {
			return list;
		}
		while (cursor.hasNext()) {
			Document item = cursor.next();
			GroupMember been = (GroupMember) BeanTransUtils.document2Bean(item, GroupMember.class);
			list.add(been);
		}

		return list;
	}

	/**
	 * 获取指定的群成员关系
	 * @param groupId
	 * @param uid
	 */
	public GroupMember getGroupMemberByUid(String appId,String groupId, long uid) {
		String dbName = getDatabaseName(appId);
		MongoCursor<Document> cursor = null;
		try {
			BasicDBObject filter = new BasicDBObject();
			filter.put("groupId", groupId);
			filter.put("uid", uid);
			cursor = find(dbName, collName, filter);
			while (cursor.hasNext()) {
				Document item = cursor.next();
				GroupMember been = (GroupMember) BeanTransUtils.document2Bean(item, GroupMember.class);
				
				return been;
			}
		} catch (Exception e) {
			log.error("GroupMemberDao getGroupUsers:", e);
		} finally {
			cursorClose(cursor);
		}
		
		return null;
	}
	
	/**
	 * 修改群成员备注
	 * @param groupId
	 * @param uid
	 * @param markedUid
	 * @param mark
	 */
	public void updateMemberMark(String appId,String groupId, long uid, long markedUid, String mark) {
		String dbName = getDatabaseName(appId);
		String[] pinyin = new String[2];
		MongoCollection<Document> coll = this.getCollection(dbName, collName);
		if(mark != null && mark.length() > 0) {
			pinyin = Pinyin4jUtil.getPinYinAndHeadChar(mark);
		}
		Bson filter = Filters.and(Filters.eq("groupId", groupId), Filters.eq("uid", uid),
				Filters.eq("membersMark.markedUid", markedUid));
		MongoCursor<Document> cursor = this.find(dbName, collName, filter);
		if(cursor.hasNext()) {
			Document doc = new Document("membersMark.$.mark", mark);
			doc.append("membersMark.$.markSpell", pinyin[0]);
			doc.append("membersMark.$.markHeadChar", pinyin[1]);
			Document update = new Document("$set", doc);
			coll.updateOne(filter, update);
		} else {
			GroupMemberMark memberMark = new GroupMemberMark();
			memberMark.setMarkedUid(markedUid);
			memberMark.setMark(mark);
			memberMark.setMarkSpell(pinyin[0]);
			memberMark.setMarkHeadChar(pinyin[1]);
			Document markDoc = BeanTransUtils.bean2Document(memberMark);
			Document update = new Document("$addToSet", new Document("membersMark", markDoc));
			FindOneAndUpdateOptions options = new FindOneAndUpdateOptions(); 
			filter = Filters.and(Filters.eq("groupId", groupId), Filters.eq("uid", uid));
			coll.findOneAndUpdate(filter, update, options.upsert(true));
		}
	}


	/**
	 * 根据用户Id，获取其关联所有的群
	 * @param uid
	 */
	public List<GroupMember> listMemberGroups(String appId,long uid) {
		String dbName = getDatabaseName(appId);
		MongoCursor<Document> cursor = null;
		List<GroupMember> groupMembers = null;
		try {
			groupMembers = new ArrayList<GroupMember>();
			BasicDBObject filter = new BasicDBObject();
			filter.put("uid", uid);
			cursor = find(dbName, collName, filter);
			while (cursor.hasNext()) {
				Document item = cursor.next();
				GroupMember been = (GroupMember) BeanTransUtils.document2Bean(item, GroupMember.class);
				groupMembers.add(been);
			}
		} catch (Exception e) {
			log.error("GroupMemberDao listMemberGroups:", e.toString());
		} finally {
			cursorClose(cursor);
		}
		return groupMembers;
	}
	
	/**
	 * 统计群成员数
	 * @param groupId
	 * @return
	 */
	public long countGroupMember(String appId,String groupId) {
		String dbName = getDatabaseName(appId);
		BasicDBObject filter = new BasicDBObject();
		filter.put("groupId", groupId);
		return getCount(dbName, collName, filter);
	}

	/**
	 *  踢人时，删除memberMark数组内的备注
	 * @param groupId
	 * @param uid
	 * @param members  要删除的 memberMark.markedUid 数组
	 */
	public void delMemberMark(String appId,String groupId, long uid, List<Long> members) {
		String dbName = getDatabaseName(appId);
		try {
			Bson filter = Filters.and(Filters.eq("groupId", groupId), Filters.eq("uid", uid));
			MongoCollection<Document> coll = getCollection(dbName, collName);
			for (int i = 0; i < members.size(); i++) {
				long memberId = members.get(i);
				BasicDBObject object = new BasicDBObject();
				object.put("markedUid", memberId);
				Document doc = new Document();
				doc.put("membersMark", object);
				BasicDBObject update = new BasicDBObject("$pull", doc);
				coll.updateOne(filter, update);
			}
		} catch (Exception e) {
			log.error("error:" + e.getMessage());
		}
	}
}

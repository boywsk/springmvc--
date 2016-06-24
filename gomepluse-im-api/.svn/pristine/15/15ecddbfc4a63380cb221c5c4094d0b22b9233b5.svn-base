package com.gomeplus.im.api.mongo;

import com.gomeplus.im.api.model.GroupQuitMember;
import com.gomeplus.im.api.utils.BeanTransUtils;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 退出群成员数据库操作类
 */
public class GroupQuitMemberDao extends BaseDao {
	Logger log = LoggerFactory.getLogger(GroupQuitMemberDao.class);
	private final static String collName = "t_group_quit_member";
	
	/**
	 * 保存退出群成员信息
	 */
	public void save(String appId,GroupQuitMember member) {
		String dbName = getDatabaseName(appId);
		MongoCollection<Document> coll = this.getCollection(dbName, collName);
		Bson filter = Filters.and(Filters.eq("groupId", member.getGroupId()),
				Filters.eq("userId", member.getUserId()));
		member.setCreateTime(System.currentTimeMillis());
		Document doc = BeanTransUtils.bean2Document(member);
		Document update = new Document("$set", doc);
		FindOneAndUpdateOptions options = new FindOneAndUpdateOptions(); 
		coll.findOneAndUpdate(filter, update, options.upsert(true));
	}

	/**
	 * 批量保存退出群成员信息
	 * @param groupQuitMembers
	 */
	public boolean saveGroupQuitMembers(String appId,List<GroupQuitMember> groupQuitMembers) {
		String dbName = getDatabaseName(appId);
		boolean success = false;
		List<Document> documentList = BeanTransUtils.bean2Document2(groupQuitMembers);
		success = this.insertBatch(dbName, collName, documentList);
		return success;
	}
	
	/**
	 * 根据退出群组id和用户id 删除记录
	 */
	public boolean delQuitMember(String appId,String groupId, long userId) {
		String dbName = getDatabaseName(appId);
		BasicDBObject del = new BasicDBObject();
		del.put("groupId", groupId);
		del.put("userId", userId);
		return delete(dbName, collName, del);
	}
	
	/**
	 * 删除多个成员
	 * @return 是否删除成功
	 */
	public boolean delQuitMembers(String appId,String groupId, List<Long> members) {
		String dbName = getDatabaseName(appId);
		BasicDBObject[] deleteObjArr = new BasicDBObject[members.size()];
		for (int i = 0; i < members.size(); i++) {
			long memberId = members.get(i);
			BasicDBObject del = new BasicDBObject();
			del.put("groupId", groupId);
			del.put("userId", memberId);
			deleteObjArr[i] = del;
		}
		BasicDBObject delObject = new BasicDBObject().append(QueryOperators.OR, deleteObjArr);
		return deleteAll(dbName, collName, delObject);
	}
	
	/**
	 * 根据用户id获取
	 * @param userId
	 * @return
	 */
	public List<GroupQuitMember> listGroupQuitMember(String appId,long userId) {
		String dbName = getDatabaseName(appId);
		List<GroupQuitMember> groupMembers = new ArrayList<GroupQuitMember>();
		try {
			BasicDBObject filter = new BasicDBObject();
			filter.put("userId", userId);
			MongoCursor<Document> cursor = find(dbName, collName, filter);
			while (cursor.hasNext()) {
				Document item = cursor.next();
				GroupQuitMember been = (GroupQuitMember) BeanTransUtils.document2Bean(item, GroupQuitMember.class);
				groupMembers.add(been);
			}
		} catch (Exception e) {
			log.error("listGroupQuitMember:", e);
		}
		
		return groupMembers;
	}
}

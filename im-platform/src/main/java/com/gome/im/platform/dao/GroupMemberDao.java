package com.gome.im.platform.dao;


import com.gome.im.platform.model.GroupMember;
import com.gome.im.platform.utils.BeanTransUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 群成员数据库操作类
 */
public class GroupMemberDao extends BaseDao {
	private static Logger log = LoggerFactory.getLogger(GroupMemberDao.class);
	private final static String collName = "t_group_member";

	/**
	 * 获取指定的群成员关系
	 * @param groupId
	 * @param uid
	 */
	public GroupMember getGroupMemberByUid(String appId,String groupId, long uid) {
		String dbName = getDatabaseName(appId);
		MongoCursor<Document> cursor = null;
		GroupMember been = null;
		try {
			BasicDBObject filter = new BasicDBObject();
			filter.put("groupId", groupId);
			filter.put("uid", uid);
			cursor = find(dbName, collName, filter);
			while (cursor.hasNext()) {
				Document item = cursor.next();
				been = (GroupMember) BeanTransUtils.document2Bean(item, GroupMember.class);
				break;
			}
		} catch (Exception e) {
			log.error("GroupMemberDao getGroupMember:", e);
		}
		return been;
	}


	/**
	 * 消息免打扰设置
	 * @param groupId
	 * @param uid
	 * @param isMsgBlocked
	 * @return
	 */
	public boolean updateMsgBlocked(String appId,String groupId, long uid, int isMsgBlocked) {
		String dbName = getDatabaseName(appId);
		BasicDBObject filter = new BasicDBObject();
		filter.put("groupId", groupId);
		filter.put("uid", uid);
		Document newdoc = new Document();
		newdoc.put("isMsgBlocked", isMsgBlocked);
		newdoc.put("updateTime", System.currentTimeMillis());
		return update(dbName, collName, filter, newdoc);
	}
}

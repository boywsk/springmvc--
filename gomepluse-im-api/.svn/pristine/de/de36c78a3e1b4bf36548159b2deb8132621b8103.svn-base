package cn.com.gome.api.dao;

import cn.com.gome.api.global.Constant;
import cn.com.gome.api.model.Group;
import cn.com.gome.api.utils.BeanTransUtils;
import com.google.common.base.Strings;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

//import cn.com.gome.api.model.GroupMsg;

/**
 * 群组数据库操作层
 */
public class GroupDao extends BaseDao {
	//private final static String dbName = Global.MONGODB_DBNAME;
	private final static String collName = "t_group";

	/**
	 * 保存群组信息
	 * 
	 * @param group
	 */
	public boolean save(String appId,Group group) {
		String dbName = getDatabaseName(appId);
		Document doc = BeanTransUtils.bean2Document(group);
		return insert(dbName, collName, doc);
	}

	/**
	 * 修改群组信息
	 * 
	 * @param group
	 */
	public void update(String appId,Group group) {
		String dbName = getDatabaseName(appId);
		MongoCollection<Document> coll = this.getCollection(dbName, collName);
		Document doc = new Document();
		String name  = group.getName();
		if(!Strings.isNullOrEmpty(name)) {
			doc.put("name", name);
		}
//		String desc = group.getDesc();
//		if(desc != null) {
//			doc.put("desc", desc);
//		}
//		String avatar = group.getAvatar();
//		if(!Strings.isNullOrEmpty(avatar)) {
//			doc.put("avatar", avatar);
//		}
//		String qRCode = group.getqRCode();
//		if(!Strings.isNullOrEmpty(qRCode)) {
//			doc.put("qRCode", qRCode);
//		}
		int capacity = group.getCapacity();
		if(capacity > 0) {
			doc.put("capacity", capacity);
		}
//		int isAudit = group.getIsAudit();
//		if(isAudit >= 0) {
//			doc.put("isAudit", isAudit);
//		}
		int isDele = group.getIsDele();
		if(isDele >= 0) {
			doc.put("isDele", isDele);
		}
		doc.put("updateTime", System.currentTimeMillis());
		Bson filter =Filters.eq("groupId", group.getGroupId());
		coll.updateOne(filter, new Document("$set", doc));
	}
	
	/**
	 * 修改群组修改时间
	 * @param groupId
	 */
	public void updateGroupTime(String appId,String groupId) {
		String dbName = getDatabaseName(appId);
		MongoCollection<Document> coll = this.getCollection(dbName, collName);
		Document doc = new Document();
		doc.put("updateTime", System.currentTimeMillis());
		Bson filter = Filters.eq("groupId", groupId);
		coll.updateOne(filter, new Document("$set", doc));
	}

	/**
	 * 根据groupId 获得  未删除状态的group数据
	 * @param groupId
	 * 
	 */
	public Group getValidGroup(String appId, String groupId) {
		String dbName = getDatabaseName(appId);
		MongoCursor<Document> cursor = null;
		Group group = null;
		try {
			BasicDBObject filter = new BasicDBObject();
			filter.put("groupId", groupId);
			filter.put("isDele", Constant.GROUP_DEL.E_GROUP_DEL_NOT.value);
			cursor = find(dbName, collName, filter);
			if (cursor.hasNext()) {
				Document item = cursor.next();
				group = (Group) BeanTransUtils.document2Bean(item, Group.class);
			}
		} catch (Exception e) {
			log.error("GroupDao getValidGroup:", e);
		} finally {
			cursorClose(cursor);
		}
		return group;
	}

	/**
	 * 根据groupId 获得group数据
	 * @param groupId
	 *
	 */
	public Group getGroupById(String appId, String groupId) {
		String dbName = getDatabaseName(appId);
		MongoCursor<Document> cursor = null;
		Group group = null;
		try {
			BasicDBObject filter = new BasicDBObject();
			filter.put("groupId", groupId);
			cursor = find(dbName, collName, filter);
			if (cursor.hasNext()) {
				Document item = cursor.next();
				group = (Group) BeanTransUtils.document2Bean(item, Group.class);
			}
		} catch (Exception e) {
			log.error("GroupDao getGroupById:", e);
		} finally {
			cursorClose(cursor);
		}
		return group;
	}

	/**
	 * 设置群组的删除状态
	 * 
	 * @param groupId
	 *            群组id
	 * @param isDel
	 *            群主删除状态
	 * @return boolean 是否修改成功
	 */
	public boolean setGroupIsDel(String appId,String groupId, int isDel) {
		String dbName = getDatabaseName(appId);
		BasicDBObject filter = new BasicDBObject();
		filter.put("groupId", groupId);

		Document newdoc = new Document();
		newdoc.put("isDele", isDel);
		newdoc.put("updateTime", System.currentTimeMillis());

		return update(dbName, collName, filter, newdoc);
	}

//	/**
//	 * 保存最后一条消息，并递增seqId
//	 * @param msg
//	 * @return
//	 */
//	public Group incGroupSeq(GroupMsg msg) {
//		MongoCollection<Document> coll = this.getCollection(dbName, collName);
//		Bson where = Filters.eq("groupId", msg.getGroupId());
//		Document doc = new Document("$inc", new Document().append("seq", 1));
//		Document gDoc = coll.findOneAndUpdate(where, doc);
//		Group group = (Group) BeanTransUtils.document2Bean(gDoc, Group.class);
//		
//		Document upDoc = new Document();
//		msg.setMsgSeqId(group.getSeq() + 1);
//		upDoc.append("lastMsg", msg.toString());
//		upDoc.append("updateTime", System.currentTimeMillis());
//		coll.updateOne(where, new Document("$set", upDoc));
//	
//		return group;
//	}
	
	/**
	 * 根据群组id列表和时间获取群组
	 * @param groupIds
	 * @param time
	 * @return
	 */
	public List<Group> listGroup(List<String> groupIds, long time) {
		List<Group> list = new ArrayList<Group>();
		BasicDBObject where = new BasicDBObject();
		where.put("groupId", new BasicDBObject(QueryOperators.IN, groupIds));
		where.put("updateTime", new BasicDBObject(QueryOperators.GTE, time));
		where.put("isDele", Constant.GROUP_DEL.E_GROUP_DEL_NOT.value);
		MongoCursor<Document> cursor = find(dbName, collName, where);
		while (cursor.hasNext()) {
			Document item = cursor.next();
			Group group = (Group) BeanTransUtils.document2Bean(item, Group.class);
			list.add(group);
		}
		
		return list;
	}

	/**
	 * 保存对话的group信息
	 * @param group
	 */
	public void saveOrUpdateGroupById(String appId,String groupId,Group group) {
		try{
			String dbName = getDatabaseName(appId);
			MongoCollection<Document> coll = this.getCollection(dbName, collName);
			Document doc = new Document();
			if(group.getType() > 0){
				doc.put("type", group.getType());
			}
			if(group.getSeq() >= 0){
				doc.put("seq",group.getSeq());
			}
			if(group.getIsDele() >= 0){
				doc.put("isDele",group.getIsDele());
			}
			if(group.getCreateTime() > 0){
				doc.put("createTime",group.getCreateTime());
			}
			if(group.getUpdateTime() > 0){
				doc.put("updateTime",group.getUpdateTime());
			}
			if(StringUtils.isNotEmpty(group.getName())){
				doc.put("name",group.getName());
			}
			Bson filter = Filters.eq("groupId", groupId);
			coll.findOneAndUpdate(filter, new Document("$set", doc), new FindOneAndUpdateOptions().upsert(true));
		}catch (Exception e){
			log.error("saveOrUpdateGroupId error:{},groupId:{}", e, group.getGroupId());
		}
	}
}

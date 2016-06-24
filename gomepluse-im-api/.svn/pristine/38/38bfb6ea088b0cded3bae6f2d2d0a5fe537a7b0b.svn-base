package com.gomeplus.im.api.mongo;

import com.gomeplus.im.api.global.Constant;
import com.gomeplus.im.api.model.Group;
import com.gomeplus.im.api.utils.BeanTransUtils;
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


/**
 * 群组数据库操作层
 */
public class GroupDao extends BaseDao {
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
		String groupName  = group.getGroupName();
		if(!Strings.isNullOrEmpty(groupName)) {
			doc.put("groupName", groupName);
		}
		String groupDesc = group.getGroupDesc();
		if(groupDesc != null) {
			doc.put("groupDesc", groupDesc);
		}
		String avatar = group.getAvatar();
		if(!Strings.isNullOrEmpty(avatar)) {
			doc.put("avatar", avatar);
		}
		String qRcode = group.getqRcode();
		if(!Strings.isNullOrEmpty(qRcode)) {
			doc.put("qRcode", qRcode);
		}
		int capacity = group.getCapacity();
		if(capacity > 0) {
			doc.put("capacity", capacity);
		}
		int isAudit = group.getIsAudit();
		if(isAudit >= 0) {
			doc.put("isAudit", isAudit);
		}
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
	 * 根据groupId 获得group数据
	 * @param groupId
	 *
	 */
	public Group getGroup(String appId, String groupId) {
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
			if(StringUtils.isNotEmpty(group.getGroupName())){
				doc.put("groupName",group.getGroupName());
			}
			Bson filter = Filters.eq("groupId", groupId);
			coll.findOneAndUpdate(filter, new Document("$set", doc), new FindOneAndUpdateOptions().upsert(true));
		}catch (Exception e){
			log.error("saveOrUpdateGroupId error:{},groupId:{}", e, group.getGroupId());
		}
	}
}

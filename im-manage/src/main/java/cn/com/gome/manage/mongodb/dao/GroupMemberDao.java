package cn.com.gome.manage.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import cn.com.gome.manage.mongodb.model.Group;
import cn.com.gome.manage.mongodb.model.GroupMember;
import cn.com.gome.manage.mongodb.model.GroupSearchModel;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.utils.BeanTransUtils;
import cn.com.gome.manage.utils.Constant;

/**
 * 群成员数据库操作类
 */
public class GroupMemberDao extends BaseDao {
	private final static String collName = "t_group_member";
		
	/**
	 * 根据用户Id，获取其关联所有的群
	 * @param uid
	 */
	public List<GroupMember> listMemberGroups(GroupSearchModel groupSearchModel,PageInfo pageInfo) {
		MongoCursor<Document> cursor = null;
		List<GroupMember> groupMembers = null;
		try {
			String dbname = dbName + "_" + groupSearchModel.getAppId();
			
			MongoCollection<Document> coll = getCollection(dbname, collName);
			
			groupMembers = new ArrayList<GroupMember>();
			BasicDBObject filter = new BasicDBObject();
			if(groupSearchModel.getType().equals(Constant.USER_TYPE.E_USER_TYPE_IM.value)){
				if(StringUtils.isNotEmpty(groupSearchModel.getUserId())){
					filter.put("uid", Long.parseLong(groupSearchModel.getUserId()));
				}
			}else{
				filter.put("uid", Long.parseLong(groupSearchModel.getUserId()));
			}			
			Bson sort = new BasicDBObject("joinTime", -1);						
			int count = (int)coll.count(filter);
			log.info("dbName :"+dbname+"; collName :"+collName+"; Query ："+filter.toJson().toString());
			cursor = coll.find(filter).sort(sort).skip((pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize()).limit(pageInfo.getPageSize()).iterator();
			pageInfo.setTotalResult(count);
			pageInfo.calculate();

			while (cursor.hasNext()) {
				Document item = cursor.next();
				GroupMember been = (GroupMember) BeanTransUtils.document2Bean(item, GroupMember.class);
				groupMembers.add(been);
			}
		} catch (Exception e) {
			log.error("GroupMember getGroupById:", e);
		} finally {
			cursorClose(cursor);
		}
		return groupMembers;
	}


	/**
	 * 获取群内 所有成员关系
	 * @param groupId
	 * @param time
	 */
	public List<GroupMember> listGroupMembers(GroupSearchModel groupSearchModel,PageInfo pageInfo) {
		List<GroupMember> list = new ArrayList<GroupMember>();
		MongoCollection<Document> coll = this.getCollection(dbName+"_"+groupSearchModel.getAppId(), collName);
		BasicDBObject where = new BasicDBObject();
		where.put("groupId", groupSearchModel.getGroupId());
		/*if(groupSearchModel.getType().equals(Constant.USER_TYPE.E_USER_TYPE_IM.value)){
			if(!StringUtils.isNotEmpty(groupSearchModel.getUserId())){
				where.put("groupId", groupSearchModel.getGroupId());
			}
		}else{
			where.put("groupId", groupSearchModel.getGroupId());
		}*/
		/*if(groupSearchModel.getStatus() >= 0){
			where.put("status", groupSearchModel.getStatus());
		}*/
		//where.put("updateTime", new BasicDBObject("$gte", groupSearchModel.getTime()));
		Bson sort = new BasicDBObject("joinTime", 1);
		MongoCursor<Document> cursor = null;
		int count = (int)coll.count(where);
		log.info("DBName:"+dbName+"_"+groupSearchModel.getAppId()+"; collName:"+collName+"; Filter:"+where.toJson().toString());
		cursor = coll.find(where).sort(sort).skip((pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize()).limit(pageInfo.getPageSize()).iterator();
		pageInfo.setTotalResult(count);
		pageInfo.calculate();
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
	public GroupMember getGroupMemberByUid(String groupId, long uid) {
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
	 * 获取群组管理员
	 */
	public List<GroupMember> listGroupManagers(String groupId) {
		List<GroupMember> groupMembers = new ArrayList<GroupMember>();
		try {
			BasicDBObject filter = new BasicDBObject();
			filter.put("groupId", groupId);
			filter.put("status", GROUP_STATUS.E_GROUP_STATUS_OK.value);
			filter.put("identity", new BasicDBObject("$gte", 1));
			MongoCursor<Document> cursor = find(dbName, collName, filter);
			while (cursor.hasNext()) {
				Document item = cursor.next();
				GroupMember been = (GroupMember) BeanTransUtils.document2Bean(item, GroupMember.class);
				groupMembers.add(been);
			}
		} catch (Exception e) {
			log.error("GroupMember listGroupManagers:", e);
		}
		
		return groupMembers;
	}

	/**
	 * 用户与群的关系状态 0 未通过， 1 通过状态， 2拒绝状态
	 */
	public enum GROUP_STATUS {
		E_GROUP_STATUS_NOT(0),//未通过
		E_GROUP_STATUS_OK(1),//通过状态
		E_GROUP_STATUS_REFUSE(2);//拒绝状态

		public int value;
		GROUP_STATUS(int value) {
			this.value = value;
		}
	}
}

package cn.com.gome.manage.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import cn.com.gome.manage.mongodb.model.Group;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.utils.BeanTransUtils;

/**
 * 群组数据库操作层
 */
public class GroupDao extends BaseDao {
	private final static String collName = "t_group";
	private final static String TUSERINFO = "t_user_info";

	/**
	 * 群的删除状态
	 * 0:未删除， 1:已删除
	 */
	public enum GROUP_DEL {
		E_GROUP_DEL_NOT(0),//未删除
		E_GROUP_DEL_OK(1);//删除

		public int value;
		GROUP_DEL(int value) {
			this.value = value;
		}
	}
	
	/**
	 * 获取群组信息
	 * @param groupId
	 * @param time
	 */
	public Group getGroupInfo(String appId,String groupId, PageInfo pageInfo) {
		Group group = null;
		MongoCollection<Document> coll = this.getCollection(dbName+"_"+appId, collName);
		BasicDBObject where = new BasicDBObject();
		where.put("groupId", groupId);
		MongoCursor<Document> cursor = null;
		int count = (int)coll.count(where);
		cursor = coll.find(where).skip((pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize()).limit(pageInfo.getPageSize()).iterator();
		pageInfo.setTotalResult(count);
		pageInfo.calculate();
		/*if(cursor == null) {
			return list;
		}*/
		while (cursor.hasNext()) {
			Document item = cursor.next();
			group = (Group) BeanTransUtils.document2Bean(item, Group.class);
		}
		return group;
	}
	
	/**
	 * 获取所有的群信息
	 * Cb-whf-20160219
	 */
	public List<Group> listAllGroups(String appId,String startTime,String endTime,PageInfo pageInfo) {
		MongoCursor<Document> cursor = null;
		List<Group> list = new ArrayList<Group>();		
		MongoCollection<Document> coll = getCollection(dbName+"_"+appId, collName);
		BasicDBObject where = new BasicDBObject();
		//where.put("isDele", GROUP_DEL.E_GROUP_DEL_NOT.value);
		if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
			//where.append(QueryOperators.AND, new BasicDBObject[]{new BasicDBObject().append("createTime", new BasicDBObject().append(QueryOperators.GTE, Long.parseLong(startTime))),new BasicDBObject().append("cretatTime", new BasicDBObject().append(QueryOperators.LTE, Long.parseLong(endTime)))});
			BasicDBList condition = new BasicDBList();
			condition.add(new BasicDBObject("createTime",new BasicDBObject(QueryOperators.GTE, Long.parseLong(startTime))));
			condition.add(new BasicDBObject("createTime",new BasicDBObject(QueryOperators.LTE, Long.parseLong(endTime))));
			where.put(QueryOperators.AND, condition);
		}else if(StringUtils.isNotEmpty(startTime)){
			where.put("createTime", new BasicDBObject().append(QueryOperators.GTE, Long.parseLong(startTime)));
		}else if(StringUtils.isNotEmpty(endTime)){
			where.put("createTime", new BasicDBObject().append(QueryOperators.LTE, Long.parseLong(endTime)));
		}
		long count = coll.count(where);
		Bson sort = new BasicDBObject("createTime", -1);
		cursor = coll.find(where).sort(sort).skip((pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize()).limit(pageInfo.getPageSize()).iterator();
		pageInfo.setTotalResult((int)count);
		log.info("DBName:"+dbName+appId+"; collName:"+collName+"; filter:"+where.toJson().toString());
		pageInfo.calculate();
		while (cursor.hasNext()) {
			Document item = cursor.next();
			Group group = (Group) BeanTransUtils.document2Bean(item, Group.class);
			//根据群主uid获取昵称，单个依次获取
			group.setNickName(getNickName(group.getUid()));
			list.add(group);
		}
		return list;
	}
	
	/**
	 * 根据群主uid获取昵称
	 * CB-by-Phil-2016-02-23-am-1120
	 * @param uid
	 * @return
	 */
	public String getNickName(Long uid) {
		String nickName = "";
		MongoCursor<Document> cursor_tuserinfo = null;
		BasicDBObject where2 = new BasicDBObject();
		where2.put("uid", uid);	
		try{
			MongoCollection<Document> coll2 = getCollection(dbName, TUSERINFO);		
			cursor_tuserinfo = coll2.find(where2).iterator();				
			Document item_tuserinfo = cursor_tuserinfo.next();
			nickName = item_tuserinfo.getString("nickName");
//			String nickName2 = item_tuserinfo.get("nickName").toString(); //也可以成功
//			UserInfo userInfo = (UserInfo)BeanTransUtils.document2Bean(item_tuserinfo,UserInfo.class);
//			nickName = userInfo.getNickName();
		}catch(Exception e){
			return nickName;
		}
		return nickName;
	}

	/**
	 * 根据群组id列表和时间获取群组
	 * @param groupIds
	 * @param time
	 * @return
	 */
	public List<Group> listGroup(List<String> groupIds, String appId, long time) {
		List<Group> list = new ArrayList<Group>();
		BasicDBObject where = new BasicDBObject();
		where.put("groupId", new BasicDBObject(QueryOperators.IN, groupIds));
		//where.put("updateTime", new BasicDBObject(QueryOperators.GTE, time));
		//where.put("isDele", GROUP_DEL.E_GROUP_DEL_NOT.value);
		MongoCursor<Document> cursor = find(dbName+"_"+appId, collName, where);
		log.info("dbName:"+dbName+"; collName:"+collName+";where:"+where.toJson().toString());		
		while (cursor.hasNext()) {
			Document item = cursor.next();
			Group group = (Group) BeanTransUtils.document2Bean(item, Group.class);
			list.add(group);
		}
		
		return list;
	}
}

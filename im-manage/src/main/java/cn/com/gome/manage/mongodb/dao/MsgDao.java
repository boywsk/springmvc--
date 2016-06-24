package cn.com.gome.manage.mongodb.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import cn.com.gome.manage.global.Global;
import cn.com.gome.manage.mongodb.model.GroupMsg;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.utils.BeanTransUtils;
import cn.com.gome.manage.utils.StringUtil;

/**
 * 数据库消息相关操作
 */
public class MsgDao extends BaseDao {
	Logger logger = LoggerFactory.getLogger(MsgDao.class);
	
	private final static String dbName = "db_msg_";
	private final static String collName = "t_group_msg_";
	
	/**
	 * 分页获取聊天消息
	 * @param groupId
	 * @return
	 */
	public List<GroupMsg> listGroupMsg(String appId, String groupId, String senderId,String senderName, String startTime,String endTime,PageInfo pageInfo) {
		String[] names = getDBAndTableName(groupId,appId);
		logger.info("dbName=[{}],tableName=[{}]", names[0], names[1]);
		List<GroupMsg> list = new ArrayList<GroupMsg>();
		MongoCollection<Document> coll = this.getCollection(names[0], names[1]);
		//-----------------------test------------------------------------------
		//TODO 测试时间 查询时 时间从0开始
//		startTime = 0;
		//TODO 测试库
//		coll = this.getCollection("db_im","t_group_msg");
		//-----------------------test------------------------------------------
		//Bson where = Filters.and(Filters.eq("groupId", groupId), Filters.gte("sendTime", startTime),Filters.lte("sendTime",endTime));
		BasicDBObject filter = new BasicDBObject();
		if(StringUtils.isNotEmpty(groupId)){
			filter.put("groupId", groupId);
		}
		if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
			filter.append(QueryOperators.AND, new BasicDBObject[]{new BasicDBObject().append("sendTime",new BasicDBObject().append(QueryOperators.GTE, Long.parseLong(startTime))),new BasicDBObject().append("sendTime",new BasicDBObject().append(QueryOperators.LTE, Long.parseLong(endTime)))});
		}else if(StringUtils.isNotEmpty(startTime)){
			filter.put("sendTime", new BasicDBObject().append(QueryOperators.GTE, Long.parseLong(startTime)));  
		}else if(StringUtils.isNotEmpty(endTime)){
			filter.put("sendTime", new BasicDBObject().append(QueryOperators.LTE, Long.parseLong(endTime)));
		}
		if(StringUtils.isNotEmpty(senderId)){
			filter.put("senderId", Long.parseLong(senderId));
		}
		if(StringUtils.isNotEmpty(senderName)){
			filter.put("senderName", senderName);
		}
		Bson sort = new BasicDBObject("sendTime", -1);
		int size = pageInfo.getPageSize();
		int currentPage = pageInfo.getCurrentPage();
		int skipSize = (currentPage -1) * size;
		long totalResult = coll.count(filter);
		MongoCursor<Document> cursor = coll.find(filter).sort(sort).skip(skipSize).limit(size).iterator();
		log.info("filter:"+filter.toJson().toString());
		while(cursor.hasNext()) {
			Document doc = cursor.tryNext();
			GroupMsg msg = (GroupMsg)BeanTransUtils.document2Bean(doc, GroupMsg.class);
			list.add(msg);
		}
		pageInfo.setTotalResult((int)totalResult);
		pageInfo.calculate();
		return list;
	}
	
	/**
	 * 分页获取聊天消息-根据APPID，startTime,endTime
	 * @param GroupMsg 
	 * @param appId,startTime,endTime
	 * @return
	 */
	public List<GroupMsg> listGroupMsg(String appId,String senderId,String senderName, String startTime,String endTime,PageInfo pageInfo) {
		List<GroupMsg> list = new ArrayList<GroupMsg>();
		List<GroupMsg> resultList = new ArrayList<GroupMsg>();
		MongoCollection<Document> coll = null;
		BasicDBObject filter = new BasicDBObject();
		if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
			filter.append(QueryOperators.AND, new BasicDBObject[]{new BasicDBObject().append("sendTime",new BasicDBObject().append(QueryOperators.GTE, Long.parseLong(startTime))),new BasicDBObject().append("sendTime",new BasicDBObject().append(QueryOperators.LTE, Long.parseLong(endTime)))});
		}else if(StringUtils.isNotEmpty(startTime)){
			filter.put("sendTime", new BasicDBObject().append(QueryOperators.GTE, Long.parseLong(startTime)));  
		}else if(StringUtils.isNotEmpty(endTime)){
			filter.put("sendTime", new BasicDBObject().append(QueryOperators.LTE, Long.parseLong(endTime)));
		}
		if(StringUtils.isNotEmpty(senderId)){
			filter.put("senderId", Long.parseLong(senderId));
		}
		if(StringUtils.isNotEmpty(senderName)){
			filter.put("senderName", senderName);
		}
		try{
			int dbMod = Global.MSG_DB_MODULO;
			int collectionMod = Global.MSG_TABLE_MODULO;
			for (int i = 0; i < dbMod; i++) {
				String database = dbName + appId +"_" + i;
				for (int j = 0; j < collectionMod; j++) {
					String collection = collName + j;
					coll = this.getCollection(database, collection);
					Bson sort = new BasicDBObject("sendTime", -1);
					//System.out.println("DB From "+ i +",CollName From "+ j +", Count: "+coll.count(filter));
					MongoCursor<Document> cursor = coll.find(filter).sort(sort).iterator();
					while(cursor.hasNext()) {
						Document doc = cursor.tryNext();
						GroupMsg msg = (GroupMsg)BeanTransUtils.document2Bean(doc, GroupMsg.class);
						list.add(msg);
					}
				}
			}
			Collections.sort(list,new Comparator<GroupMsg>() {
				@Override
				public int compare(GroupMsg o1,GroupMsg o2) {
					if(o1.getSendTime() >= o2.getSendTime()){
						return -1;
					}else {
						return 1;
					}
				}
			}); 
			int currentPage = (pageInfo.getCurrentPage() -1) * pageInfo.getPageSize();
			if(list.size() > 0){
				for(int i = currentPage ; i <= currentPage+pageInfo.getPageSize() - 1 ;i++){
					resultList.add(list.get(i));
				}
			}
			pageInfo.setTotalResult(list.size());
			pageInfo.calculate();
			log.info("DBName:"+dbName + appId+"_ALL ;collName:t_group_msg_ALL ;filter:"+filter.toJson().toString());
		}catch (Exception e){
			logger.error(e.getMessage());
		}
		return resultList;
	}

	/**
	 * 根据起止时间获取消息表中不同类型的消息总数量
	 * @param groupType  1:单聊，2:群聊
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public long getMsgCountByDay(int groupType, long startTime, long endTime) {
		long count = 0;
		try{
			Bson filter = Filters.and(Filters.eq("groupType", groupType), Filters.gte("sendTime", startTime), Filters.lt("sendTime", endTime));
			int dbMod = Global.MSG_DB_MODULO;
			int collectionMod = Global.MSG_TABLE_MODULO;
			for (int i = 0; i < dbMod; i++) {
				String database = dbName + i;
				for (int j = 0; j < collectionMod; j++) {
					String collection = collName + j;
					MongoCollection<Document> coll = this.getCollection(database, collection);
					count += coll.count(filter);
				}
			}
		}catch (Exception e){
			logger.error(e.getMessage());
		}
		return count;
	}

	/**
	 * 根据group计算库名和表名
	 * @param groupId
	 * @return
	 */
	private String[] getDBAndTableName(String groupId,String appId) {
		String[] arr = new String[2];
		int hashValue = StringUtil.FNVHash1(groupId);
		int hashValueForColl = StringUtil.SDBMHash(groupId);
		arr[0] = dbName + appId +"_"+ hashValue % Global.MSG_DB_MODULO;
		arr[1] = collName + hashValueForColl % Global.MSG_TABLE_MODULO;
		return arr;
	}
}

package cn.com.gome.manage.mongodb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import cn.com.gome.manage.mongodb.model.TUserInfo;
import cn.com.gome.manage.mongodb.model.UserSearchModel;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pojo.UserInfo;
import cn.com.gome.manage.utils.BeanTransUtils;

public class UserInfoDao extends BaseDao {
	Logger log = LoggerFactory.getLogger(UserInfoDao.class);
	
	private final static String collName = "t_user_info";
	
	/**
	 * 分页获取im用户
	 * @param pageInfo
	 * @return
	 */
	public List<TUserInfo> listTUserInfo(UserSearchModel userSearchModel,PageInfo pageInfo) {
		List<TUserInfo> tUserInfoList = new ArrayList<TUserInfo>();
		MongoCursor<Document> cursor = null;
		try{	
			String tempDbName = dbName+"_"+userSearchModel.getAppId();
			MongoCollection<Document> coll = getCollection(tempDbName, collName);
			BasicDBObject filter = new BasicDBObject();
			if(StringUtils.isNotEmpty(userSearchModel.getuId())){
				filter.put("uid", Integer.parseInt(userSearchModel.getuId()));
			}
			if(StringUtils.isNotEmpty(userSearchModel.getStartTime()) && StringUtils.isNotEmpty(userSearchModel.getEndTime())){
				BasicDBList values = new BasicDBList();
				values.add(new BasicDBObject("createTime", new BasicDBObject(QueryOperators.LTE, Long.parseLong(userSearchModel.getEndTime()))));  
				values.add(new BasicDBObject("createTime", new BasicDBObject(QueryOperators.GTE, Long.parseLong(userSearchModel.getStartTime()))));
				filter.put(QueryOperators.AND, values);
			}else if(StringUtils.isNotEmpty(userSearchModel.getStartTime())){
				filter.put("createTime", new BasicDBObject(QueryOperators.GTE, Long.parseLong(userSearchModel.getStartTime())));
			}else if(StringUtils.isNotEmpty(userSearchModel.getEndTime())){
				filter.put("createTime", new BasicDBObject(QueryOperators.LTE, Long.parseLong(userSearchModel.getEndTime()))); 
			}
			Bson sort = new BasicDBObject("createTime", -1);
			cursor = coll.find(filter).sort(sort).skip((pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize()).limit(pageInfo.getPageSize()).iterator();
			log.info("DBName:"+tempDbName+";collName:"+collName+"; filter:"+filter.toJson().toString());
			long count = coll.count(filter);
			pageInfo.setTotalResult((int)count);
			pageInfo.calculate();
			while (cursor.hasNext()) {
				Document item = cursor.next();
				TUserInfo info = (TUserInfo) BeanTransUtils.document2Bean(item, TUserInfo.class);
				tUserInfoList.add(info);
			}
		}catch (Exception e) {
			log.error("listTUserInfo:", e);
		}
		return tUserInfoList;
	}
	
	/**
	 * 分页获取im用户
	 * @param pageInfo
	 * @return
	 */
	public List<UserInfo> listUserInfo(PageInfo pageInfo) {
		List<UserInfo> list = new ArrayList<UserInfo>();
		MongoCursor<Document> cursor = null;
		try {
			Bson sort = new BasicDBObject("uid", -1);
			MongoCollection<Document> coll = getCollection(dbName, collName);
			long count = coll.count();
			cursor = coll.find().sort(sort).skip((pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize()).limit(pageInfo.getPageSize()).iterator();
			pageInfo.setTotalResult((int)count);
			pageInfo.calculate();
			while (cursor.hasNext()) {
				Document item = cursor.next();
				UserInfo info = (UserInfo) BeanTransUtils.document2Bean(item, UserInfo.class);
				list.add(info);
			}
		}catch (Exception e) {
			log.error("listUserInfo:", e);
		}
		
		return list;
	}
	
	/**
	 * 根据昵称分页查询im用户
	 * @param nickName
	 * @param pageInfo
	 * @return
	 */
	public List<UserInfo> searchUserInfo(String nickName, PageInfo pageInfo) {
		List<UserInfo> list = new ArrayList<UserInfo>();
		MongoCursor<Document> cursor = null;
		try {
			Bson sort = new BasicDBObject("uid", -1);
			MongoCollection<Document> coll = getCollection(dbName, collName);
			long count = coll.count();
			BasicDBObject where = new BasicDBObject();
			Pattern pattern = Pattern.compile("^.*" + nickName+ ".*$", Pattern.CASE_INSENSITIVE); 
			where.put("nickName", pattern);
			cursor = coll.find(where).sort(sort).skip((pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize()).limit(pageInfo.getPageSize()).iterator();
			pageInfo.setTotalResult((int)count);
			pageInfo.calculate();
			while (cursor.hasNext()) {
				Document item = cursor.next();
				UserInfo info = (UserInfo) BeanTransUtils.document2Bean(item, UserInfo.class);
				list.add(info);
			}
		}catch (Exception e) {
			log.error("searchUserInfo:", e);
		}
		
		return list;
	}
}

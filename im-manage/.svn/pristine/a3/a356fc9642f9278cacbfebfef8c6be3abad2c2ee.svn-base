package cn.com.gome.manage.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import cn.com.gome.manage.mongodb.model.AppAccount;
import cn.com.gome.manage.mongodb.model.AppSysAccount;
import cn.com.gome.manage.mongodb.model.TUserInfo;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.utils.BeanTransUtils;

public class AppSysAccountDao extends BaseDao {
	Logger log = LoggerFactory.getLogger(AppSysAccountDao.class);
	private final static String databaseName = "db_app";
	private final static String appCollName = "t_app_account";

	public void saveAppSysAccount(AppSysAccount app) {
		app.setCreateTime(Long.toString(System.currentTimeMillis()));
		Document document = BeanTransUtils.bean2Document(app);
		this.insert(databaseName, appCollName, document);
	}

	public List<AppSysAccount> getAppSysAccountByAppId(String appId, String uId){
		MongoCursor<Document> cursor = null;
		List<AppSysAccount> asaList = new ArrayList<>();
		AppSysAccount asa = null;
		try{
			BasicDBObject filter = new BasicDBObject();
			filter.put("appId", appId);
			filter.put("uId", uId);
			cursor = find(databaseName, appCollName, filter);
			while(cursor.hasNext()){
				Document item = cursor.next();
				asa = (AppSysAccount)BeanTransUtils.document2Bean(item, AppSysAccount.class);
				asaList.add(asa);
			}
		}catch(Exception e){
			log.error("getAppSysAccountDao " + e);
		}
		return asaList;
	}
	
	public List<AppSysAccount> displayAppSysAccountByAppId(PageInfo pageInfo, AppAccount appAccount){
		MongoCursor<Document> cursor = null;
		List<AppSysAccount> asaList = new ArrayList<>();
		AppSysAccount asa = null;
		try{
			MongoCollection<Document> coll = getCollection(databaseName, appCollName);
            BasicDBObject filter = new BasicDBObject();
            if(StringUtils.isNotEmpty(appAccount.getAppId())){
            	filter.put("appId", appAccount.getAppId());
            }
            if(StringUtils.isNotEmpty(appAccount.getuId())){
            	filter.put("uId", appAccount.getuId());
            }
            if(StringUtils.isNotEmpty(appAccount.getuName())){
            	filter.put("uName", appAccount.getuName());
            }
            long count = coll.count(filter);
            Bson sort = new BasicDBObject("createTime",-1);
            cursor = coll.find(filter).sort(sort).skip((pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize()).limit(pageInfo.getPageSize()).iterator();
            pageInfo.setTotalResult((int)count);
    		pageInfo.calculate();
    		while(cursor.hasNext()){
    			Document item = cursor.next();
    			asa = (AppSysAccount)BeanTransUtils.document2Bean(item, AppSysAccount.class);
    			asaList.add(asa);
    		}
		}catch(Exception e){
			log.error("displayAppSysAccountByAppId " + e);
		}
		return asaList;
	}
	
	public List<AppSysAccount> displayAllAppSysAccountByAppId(AppAccount appAccount){
		MongoCursor<Document> cursor = null;
		List<AppSysAccount> asaList = new ArrayList<>();
		AppSysAccount asa = null;
		try{
			MongoCollection<Document> coll = getCollection(databaseName, appCollName);
            BasicDBObject filter = new BasicDBObject();
            if(StringUtils.isNotEmpty(appAccount.getAppId())){
            	filter.put("appId", appAccount.getAppId());
            }
            if(StringUtils.isNotEmpty(appAccount.getuId())){
            	filter.put("uId", appAccount.getuId());
            }
            if(StringUtils.isNotEmpty(appAccount.getuName())){
            	filter.put("uName", appAccount.getuName());
            }
            Bson sort = new BasicDBObject("createTime",-1);
            cursor = coll.find(filter).sort(sort).iterator();
    		while(cursor.hasNext()){
    			Document item = cursor.next();
    			asa = (AppSysAccount)BeanTransUtils.document2Bean(item, AppSysAccount.class);
    			asaList.add(asa);
    		}
		}catch(Exception e){
			log.error("displayAppSysAccountByAppId " + e);
		}
		return asaList;
	}
	
	public List<String> getAllUidFromAppId(String appId){
		MongoCursor<Document> cursor = null;
		List<String> asaList = new ArrayList<String>();
		TUserInfo tui = null;
		try{
			MongoCollection<Document> coll = getCollection("db_im_"+appId, "t_user_info");
            cursor = coll.find().iterator();
            log.info("getAllUidFromAppId:"+cursor.toString());
    		while(cursor.hasNext()){
    			Document item = cursor.next();
    			tui = (TUserInfo)BeanTransUtils.document2Bean(item, TUserInfo.class);
    			asaList.add(tui.getUid());
    		}
		}catch(Exception e){
			log.error("displayAppSysAccountByAppId " + e);
		}
		return asaList;
		
	}

}

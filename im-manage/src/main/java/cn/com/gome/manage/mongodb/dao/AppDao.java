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

import cn.com.gome.manage.mongodb.model.AppSearchModel;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pojo.AppInfo;
import cn.com.gome.manage.utils.BeanTransUtils;
import cn.com.gome.manage.utils.Constant;

public class AppDao extends BaseDao{
	
	Logger log = LoggerFactory.getLogger(AppDao.class);
    private final static String databaseName = "db_app";
    private final static String appCollName = "t_app";
	
	public void saveApp(AppInfo app) {
        Document document = BeanTransUtils.bean2Document(app);
        this.insert(databaseName, appCollName, document);        
    }
	
	public void updateAppInfo(AppInfo app) {
		BasicDBObject filter = new BasicDBObject();
		filter.put("appId", app.getAppId());
        Document doc = new Document();
        if(StringUtils.isNotEmpty(app.getAppName())){
            doc.put("appName", app.getAppName());
        }
        if(StringUtils.isNotEmpty(app.getAppKey())){
            doc.put("appKey", app.getAppKey());
        }
        if(StringUtils.isNotEmpty(app.getAppDesc())){
            doc.put("appDesc", app.getAppDesc());
        }
        if(StringUtils.isNotEmpty(app.getUpdateTime())){
            doc.put("updateTime", app.getUpdateTime());
        }
        update(databaseName, appCollName, filter, doc);
	}
	
	public List<AppInfo> getAppInfo(PageInfo pageInfo, AppSearchModel appSearchModel) {
		MongoCursor<Document> cursor = null;
		List<AppInfo> appInfos = new ArrayList<>();
        AppInfo app = null;
        try {
        	MongoCollection<Document> coll = getCollection(databaseName, appCollName);
            BasicDBObject filter = new BasicDBObject();            
            if(appSearchModel.getType().equals(Constant.USER_TYPE.E_USER_TYPE_IM.value)){
            	if(StringUtils.isNotEmpty(appSearchModel.getUserId())){
            		filter.put("uId", appSearchModel.getUserId());
            	}
            }else{
            	filter.put("uId", appSearchModel.getUserId());
            }
            if(StringUtils.isNotEmpty(appSearchModel.getUserName())){
            	filter.put("userName", appSearchModel.getUserName());
            }
            if(StringUtils.isNotEmpty(appSearchModel.getAppId())){
            	filter.put("appId",appSearchModel.getAppId());
            }
            if(StringUtils.isNotEmpty(appSearchModel.getAppName())){
            	filter.put("appName",appSearchModel.getAppName());
            }
            long count = coll.count(filter);
            Bson sort = new BasicDBObject("uid", -1);
            cursor = coll.find(filter).sort(sort).skip((pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize()).limit(pageInfo.getPageSize()).iterator();
    		pageInfo.setTotalResult((int)count);
    		pageInfo.calculate();
            while (cursor.hasNext()) {
                Document item = cursor.next();
                app = (AppInfo) BeanTransUtils.document2Bean(item, AppInfo.class);
                appInfos.add(app);
            }
        } catch (Exception e) {
            log.error("AppDao getApp error:", e);
        } finally {
            cursorClose(cursor);
        }
		return appInfos;		
	}
	
	public List<AppInfo> getAllAppInfo(AppSearchModel appSearchModel) {
		MongoCursor<Document> cursor = null;
		List<AppInfo> appInfos = new ArrayList<>();
        AppInfo app = null;
        try {
        	MongoCollection<Document> coll = getCollection(databaseName, appCollName);
            BasicDBObject filter = new BasicDBObject();            
            if(appSearchModel.getType().equals(Constant.USER_TYPE.E_USER_TYPE_IM.value)){
            	if(StringUtils.isNotEmpty(appSearchModel.getUserId())){
            		filter.put("uId", appSearchModel.getUserId());
            	}
            }else{
            	filter.put("uId", appSearchModel.getUserId());
            }
            if(StringUtils.isNotEmpty(appSearchModel.getUserName())){
            	filter.put("userName", appSearchModel.getUserName());
            }
            if(StringUtils.isNotEmpty(appSearchModel.getAppId())){
            	filter.put("appId",appSearchModel.getAppId());
            }
            if(StringUtils.isNotEmpty(appSearchModel.getAppName())){
            	filter.put("appName",appSearchModel.getAppName());
            }
            Bson sort = new BasicDBObject("uid", -1);
            cursor = coll.find(filter).sort(sort).iterator();
            while (cursor.hasNext()) {
                Document item = cursor.next();
                app = (AppInfo) BeanTransUtils.document2Bean(item, AppInfo.class);
                appInfos.add(app);
            }
        } catch (Exception e) {
            log.error("AppDao getApp error:", e);
        } finally {
            cursorClose(cursor);
        }
		return appInfos;		
	}
	
	public void delAppInfo(String uId) {
		BasicDBObject filter = new BasicDBObject();
        filter.put("uId", uId);
        Document doc = new Document();        
        update(databaseName, appCollName, filter, doc);
	}
	
	/**
	 * appId查重
	 * @return
	 */
	public int getAPPIDCount(String appId){
		int count = 0;
		MongoCollection<Document> coll = getCollection(databaseName, appCollName);
		BasicDBObject filter = new BasicDBObject();
		filter.put("appId", appId);
		count = (int)coll.count(filter);
		return count;
	}
	
	/**
	 * 获取所有的AppInfo信息
	 * @return
	 */
	public List<AppInfo> listAppInfo() {
		MongoCursor<Document> cursor = null;
		List<AppInfo> appInfos = new ArrayList<AppInfo>();
        try {
        	MongoCollection<Document> coll = getCollection(databaseName, appCollName);
            cursor = coll.find().iterator();
            while (cursor.hasNext()) {
                Document item = cursor.next();
                AppInfo app = (AppInfo) BeanTransUtils.document2Bean(item, AppInfo.class);
                appInfos.add(app);
            }
        } catch (Exception e) {
            log.error("AppDao getApp error:", e);
        } finally {
            cursorClose(cursor);
        }
		return appInfos;		
	}

    public AppInfo getApp(String appId){
        MongoCursor<Document> cursor = null;
        AppInfo app = null;
        try {
            BasicDBObject filter = new BasicDBObject();
            filter.put("appId", appId);
            cursor = find(databaseName, appCollName, filter);
            if (cursor.hasNext()) {
                Document item = cursor.next();
                app = (AppInfo) BeanTransUtils.document2Bean(item, AppInfo.class);
            }
        } catch (Exception e) {
            log.error("AppDao getApp error:", e);
        } finally {
            cursorClose(cursor);
        }
        return app;
    }

}

package com.gome.im.platform.dao;

import com.gome.im.platform.model.App;
import com.gome.im.platform.utils.BeanTransUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangshikai on 2016/2/24.
 */
public class AppDao extends BaseDao {
    Logger log = LoggerFactory.getLogger(AppDao.class);
    private final static String databaseName = "db_app";
    private final static String appCollName = "t_app";

    private final static String  idCollName= "t_increment_ids";

    public static ConcurrentHashMap<String,App> APP_INFO_MAP = new ConcurrentHashMap<>();

    public boolean saveApp(App app) {
        boolean success = false;
        Document document = BeanTransUtils.bean2Document(app);
        success = this.insert(databaseName, appCollName, document);
        if(success){
            APP_INFO_MAP.put(app.getAppId(), app);
        }
        return success;
    }

    public boolean updateAppInfo(App app) {
        boolean success = false;
        BasicDBObject filter = new BasicDBObject();
        filter.put("appId", app.getAppId());
        Document doc = new Document();
        if(StringUtils.isNotEmpty(app.getAppKey())){
            doc.put("appKey", app.getAppKey());
        }
        success = this.update(databaseName, appCollName, filter, doc);
        if(success){
            APP_INFO_MAP.put(app.getAppId(), app);
        }
        return success;
    }

    public App getApp(String appId){
        MongoCursor<Document> cursor = null;
        App app = null;
        try {
            BasicDBObject filter = new BasicDBObject();
            filter.put("appId", appId);
            cursor = find(databaseName, appCollName, filter);
            if (cursor.hasNext()) {
                Document item = cursor.next();
                app = (App) BeanTransUtils.document2Bean(item, App.class);
            }
        } catch (Exception e) {
            log.error("AppDao getApp error:", e);
        } finally {
            cursorClose(cursor);
        }
        return app;
    }

    public Long getId(){
        MongoCollection<Document> coll = getCollection(dbName, idCollName);
        BasicDBObject filter = new BasicDBObject();
        filter.put("tableName", "t_app");
        BasicDBObject update = new BasicDBObject("$inc", new BasicDBObject("id", 1L));
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.upsert(true);
        Document d = coll.findOneAndUpdate(filter, update, options);
        if(d == null){
            return 0L;
        }
        return d.getLong("id").longValue();
    }
}

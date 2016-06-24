package cn.com.gome.api.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.bson.Document;

/**
 * Created by wangshikai on 2016/2/25.
 */
public class IncrementIdDao extends BaseDao{
    private final static String collName = "t_increment_ids";

    /**
     *自增的id维护表   获取t_user_info表的自增id值
     * @return 自增的id
     */
    public Long getId(String appId){
        String dbName = getDatabaseName(appId);
        MongoCollection<Document> coll = getCollection(dbName, collName);
        BasicDBObject filter = new BasicDBObject();
        filter.put("tableName", "t_user_info");
        BasicDBObject update = new BasicDBObject("$inc", new BasicDBObject("id", 1L));
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.upsert(true);
        Document d = coll.findOneAndUpdate(filter, update, options);
        if(d == null){
            return  1L;
        }
        return (d.getLong("id").longValue() + 1);
    }
}

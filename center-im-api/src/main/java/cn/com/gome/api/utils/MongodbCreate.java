package cn.com.gome.api.utils;

import cn.com.gome.api.model.Group;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.configuration.CompositeConfiguration;
import org.bson.Document;

/**
 * Created by wangshikai on 2016/4/20.
 */
public class MongodbCreate {
    private static MongoClient client = null;

    private static void  init(String host,int port){
        CompositeConfiguration config = new CompositeConfiguration();
        try {
            int poolSize = 10;
            int connectionMultiplier = 10;
            MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
            builder.connectionsPerHost(poolSize);
            builder.threadsAllowedToBlockForConnectionMultiplier(connectionMultiplier);
            builder.maxWaitTime(30000);
            builder.connectTimeout(10000);
            builder.socketKeepAlive(true);
            builder.socketTimeout(3000);
            builder.writeConcern(WriteConcern.SAFE);
            client = new MongoClient(new ServerAddress(host, port),
                    builder.build());
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        init("10.125.3.11",30000);
//        String databaseName = "db_im_TEST_APP_ID";
//        MongoDatabase database = client.getDatabase(databaseName);
//        database.createCollection("t_group");
//        database.createCollection("t_group_member");
//        database.createCollection("t_group_quit_member");
//        database.createCollection("t_user_info");
//        System.out.println("-----------------------create end!-------------------------------");
        String appId = "gomeplus_sync";
        String groupId = "176329128985755668";
        Group group = findOneGroupById(appId,groupId);
        System.out.println("群创建者IMUserId:"+group.getUid());
    }

    public static Group findOneGroupById(String appId,String groupId) {
        String dbName = getDatabaseName(appId);
        MongoCollection<Document> coll = getCollection(dbName, "t_group");
        BasicDBObject filter = new BasicDBObject();
        filter.put("groupId", groupId);
        BasicDBObject update = new BasicDBObject("$set",filter);
        Document item =  coll.findOneAndUpdate(filter,update);
        Group group = (Group) BeanTransUtils.document2Bean(item, Group.class);
        return group;
    }

    public static String getDatabaseName(String appId){
        return "db_im" +"_"+ appId;
    }

    public static MongoCollection<Document> getCollection(String dbName,
                                                   String collName) {
        if (null == collName || "".equals(collName)) {
            return null;
        }
        if (null == dbName || "".equals(dbName)) {
            return null;
        }
        // DBCollection coll = mongoClient.getDB(dbName).getDB(collName);
        MongoCollection<Document> collection = client.getDatabase(dbName)
                .getCollection(collName);
        return collection;
    }
}

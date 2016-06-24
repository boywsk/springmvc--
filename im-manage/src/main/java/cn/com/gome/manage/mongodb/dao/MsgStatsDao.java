package cn.com.gome.manage.mongodb.dao;

import cn.com.gome.manage.mongodb.model.MsgStats;
import cn.com.gome.manage.utils.BeanTransUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangshikai on 2016/2/18.
 */
public class MsgStatsDao extends BaseDao {
    private Logger log = LoggerFactory.getLogger(UserInfoDao.class);

    private final static String collName = "t_msg_stats";

    public void addMsgStats(MsgStats stats) {
        try{
            Document doc = BeanTransUtils.bean2Document(stats);
            insert(dbName, collName, doc);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public MsgStats  findMsgStats(String day){
        MsgStats msgStats = null;
        try{
            MongoCollection<Document> coll = getCollection(dbName, collName);
            BasicDBObject filter = new BasicDBObject();
            filter.put("day",day);
            MongoCursor<Document> cursor = find(dbName,collName,filter);
            while (cursor.hasNext()) {
                Document item = cursor.next();
                msgStats = (MsgStats) BeanTransUtils.document2Bean(item, MsgStats.class);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return msgStats;
    }

}

package cn.com.gome.manage.mongodb.dao;

import cn.com.gome.manage.mongodb.model.Friend;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.utils.BeanTransUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户好友关系的数据库操作类
 */
public class FriendDao extends BaseDao {

	private final static String collName = "t_friend";

	/**
	 * 获得指定两个用户之间的关系表，返回多条记录
	 * 
	 * @param uid
	 *            fid 好友关系双方id
	 * @return 返回单条记录
	 */
	public List<Friend> queryFriendsById(long uid, long fid) {
		MongoCursor<Document> cursor = null;
		List<Friend> all = null;
		try {
			all = new ArrayList<Friend>();
			BasicDBObject ubb = new BasicDBObject();
			ubb.put("uid", uid);
			ubb.put("friendUid", fid);
			BasicDBObject fbb = new BasicDBObject();
			fbb.put("uid", fid);
			fbb.put("friendUid", uid);
			BasicDBObject filter = new BasicDBObject().append(QueryOperators.OR, new BasicDBObject[] { ubb, fbb });
			cursor = find(dbName, collName, filter);
			while (cursor.hasNext()) {
				Document item = cursor.next();
				String id = item.get("_id").toString();
				Friend friend = (Friend) BeanTransUtils.document2Bean(item, Friend.class);
				friend.setId(id);
				all.add(friend);
			}
		} catch (Exception e) {
			log.error("FriendDao queryFriendById:", e);
		} finally {
			cursorClose(cursor);
		}
		return all;
	}

	/**
	 * 获得用户的好友关系表相关
	 * @param uid
	 * @param time  时间戳
	 * @return status 0 未通过 返回好友申请记录，1 已通过 返回好友关系表， 2 拒绝，返回拒绝记录
	 */
	public List<Friend> queryFriends(long uid, long time,PageInfo pageInfo) {
		MongoCursor<Document> cursor = null;
		List<Friend> list = new ArrayList<Friend>();
		try {
			BasicDBObject filter = new BasicDBObject();
			filter.put("uid", uid);
			//filter.put("status", status);
			filter.put("updateTime", new BasicDBObject("$gte", time));
			MongoCollection<Document> coll = getCollection(dbName, collName);
			Bson sort = new BasicDBObject("updateTime", 1);
			//cursor = find(dbName, collName, filter);
			int count = (int)coll.count(filter);

			cursor = coll.find(filter).sort(sort).skip((pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize()).limit(pageInfo.getPageSize()).iterator();
			pageInfo.setTotalResult(count);
			pageInfo.calculate();

			while (cursor.hasNext()) {
				Document item = cursor.next();
				Friend been = (Friend) BeanTransUtils.document2Bean(item, Friend.class);
				list.add(been);
			}
		} catch (Exception e) {
			log.error("FriendDao queryFriends:", e);
		} finally {
			cursorClose(cursor);
		}
		return list;
	}

}

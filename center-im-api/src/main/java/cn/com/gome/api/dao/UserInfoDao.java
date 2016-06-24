package cn.com.gome.api.dao;

import cn.com.gome.api.model.UserInfo;
import cn.com.gome.api.utils.BeanTransUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;

/**
 * 数据库用户信息
 */
public class UserInfoDao extends BaseDao {
    private final static String collName = "t_user_info";

	/**
	 * 保存用户信息
	 * @param userInfo
	 */
	public boolean saveUserInfo(String appId,UserInfo userInfo) {
        boolean success = false;
        String dbName = getDatabaseName(appId);
        Document document = BeanTransUtils.bean2Document(userInfo);
		success = this.insert(dbName, collName, document);
        return success;
	}

    /**
     * 更新用户信息
     * @param userInfo
     */
    public boolean updateUserInfo(String appId,UserInfo userInfo) {
        boolean success = false;
        String dbName = getDatabaseName(appId);
        BasicDBObject filter = new BasicDBObject();
//        filter.put("appId", userInfo.getAppId());
        filter.put("thirdUid", userInfo.getThirdUid());
        Document doc = new Document();
        if(StringUtils.isNotEmpty(userInfo.getNickName())){
            doc.put("nickName", userInfo.getNickName());
        }
        if(StringUtils.isNotEmpty(userInfo.getDeviceToken())){
            doc.put("deviceToken", userInfo.getDeviceToken());
        }
        if(userInfo.getDeviceType() > 0){
            doc.put("deviceType", userInfo.getDeviceType());
        }
        if(StringUtils.isNotEmpty(userInfo.getToken())){
            doc.put("token", userInfo.getToken());
        }
        if(userInfo.getTokenExpires() > 0){
            doc.put("tokenExpires", userInfo.getTokenExpires());
        }
        if(userInfo.getUpdateTime() > 0){
            doc.put("updateTime", userInfo.getUpdateTime());
        }
//        UpdateOptions option = new UpdateOptions();
        success = this.update(dbName, collName, filter, doc);
        return success;
    }

    public UserInfo getUserInfoByThirdUid(String appId, String thirdUid){
        MongoCursor<Document> cursor = null;
        String dbName = getDatabaseName(appId);
        UserInfo userInfo = null;
        try {
            BasicDBObject filter = new BasicDBObject();
            filter.put("thirdUid", thirdUid);
            cursor = find(dbName, collName, filter);
            if (cursor.hasNext()) {
                Document item = cursor.next();
                userInfo = (UserInfo) BeanTransUtils.document2Bean(item, UserInfo.class);
            }
        } catch (Exception e) {
            log.error("UserInfoDao getUserInfoByThirdUid error:", e);
        } finally {
            cursorClose(cursor);
        }
        return userInfo;
    }

    public UserInfo getUserInfoByImUserId(String appId,long uid){
        MongoCursor<Document> cursor = null;
        String dbName = getDatabaseName(appId);
        UserInfo userInfo = null;
        try {
            BasicDBObject filter = new BasicDBObject();
            filter.put("uid", uid);
            cursor = find(dbName, collName, filter);
            if (cursor.hasNext()) {
                Document item = cursor.next();
                userInfo = (UserInfo) BeanTransUtils.document2Bean(item, UserInfo.class);
            }
        } catch (Exception e) {
            log.error("UserInfoDao getUserInfoByThirdUid error:", e);
        } finally {
            cursorClose(cursor);
        }
        return userInfo;
    }

}

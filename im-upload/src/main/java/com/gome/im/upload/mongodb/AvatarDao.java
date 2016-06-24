package com.gome.im.upload.mongodb;

import org.bson.Document;

import com.gome.im.upload.model.AvatarModel;
import com.gome.im.upload.utils.BeanTransUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;

public class AvatarDao extends BaseDao {
	private final static String collName = "t_avatar_url";
	public void updateInsertAvatar(AvatarModel avatarModel) {
        try{
            Document doc = BeanTransUtils.bean2Document(avatarModel);
            BasicDBObject filter = new BasicDBObject();
            filter.put("uid", avatarModel.getUid());
            UpdateOptions option = new UpdateOptions();
            MongoCollection<Document> coll = getCollection(dbName, collName);
            coll.updateOne(filter, new Document("$set",doc),option.upsert(true));
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
	/**
	 * 获取小头像Name
	 * @param uid
	 * @return
	 */
	public String getAvatarName(long uid) {
		MongoCursor<Document> cursor_avatar = null; 
		String avatarName = "";
        try{
            MongoCollection<Document> coll = getCollection(dbName, collName);
            BasicDBObject filter = new BasicDBObject();
            filter.put("uid", uid);
            cursor_avatar = coll.find(filter).iterator();
            Document avatarDocument = cursor_avatar.next();
            avatarName = avatarDocument.getString("avatarSmallName");            
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return avatarName;
    }

}

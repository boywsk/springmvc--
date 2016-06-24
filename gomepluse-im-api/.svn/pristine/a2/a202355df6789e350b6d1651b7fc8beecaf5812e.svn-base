package com.gomeplus.im.api.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gomeplus.im.api.model.GroupMemberMark;
import com.gomeplus.im.api.utils.BeanTransUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

/**
 * 群组成员备注
 */
public class GroupMemberMarkDao extends BaseDao {
    private static Logger log = LoggerFactory.getLogger(GroupMemberMarkDao.class);
    private final static String collName = "t_group_memberMark";

    /**
     * 添加成员备注
     * @param appId
     * @param memberMark
     */
    public boolean saveMemberMark(String appId,GroupMemberMark memberMark){
        String dbName = getDatabaseName(appId);
        Document doc = BeanTransUtils.bean2Document(memberMark);
        return insert(dbName, collName, doc);
    }

    public boolean updateMemberMark(String appId,String groupId,long userId,long markedUserId,String mark){
        String dbName = getDatabaseName(appId);
        BasicDBObject filter = new BasicDBObject();
        filter.append("groupId", groupId).append("userId",userId).append("markedUserId",markedUserId);
        Document doc = new Document();
        doc.put("mark", mark);
        return update(dbName, collName, filter,doc);
    }

    /**
     * 删除所有用户对该用户的备注
     * @param appId
     * @param groupId
     * @param userId
     */
    public boolean delMemberMark(String appId,String groupId,long userId){
        String dbName = getDatabaseName(appId);
        BasicDBObject del = new BasicDBObject();
        del.put("groupId", groupId);
        del.put("markedUserId",userId);
        return delete(dbName, collName, del);
    }

    /**
     * 删除群组相关的所有备注(解散群用)
     * @param appId
     * @param groupId
     */
    public boolean delAllMemberMark(String appId,String groupId){
        String dbName = getDatabaseName(appId);
        BasicDBObject del = new BasicDBObject();
        del.put("groupId", groupId);
        return delete(dbName, collName, del);
    }

    /**
     * 获取某人所有的成员备注信息
     * @param appId
     * @param groupId
     * @param userId
     * @return
     */
    public List<GroupMemberMark> getMemberMarks(String appId,String groupId,long userId){
        List<GroupMemberMark> list = new ArrayList<>();
        String dbName = getDatabaseName(appId);
        BasicDBObject filter = new BasicDBObject();
        filter.put("groupId",groupId);
        filter.put("userId", userId);
        MongoCursor<Document> cursor = find(dbName, collName, filter);
        while (cursor.hasNext()) {
            Document item = cursor.next();
            GroupMemberMark been = (GroupMemberMark) BeanTransUtils.document2Bean(item, GroupMemberMark.class);
            list.add(been);
        }
        return list;
    }

    /**
     * 获取某人对群组某成员的备注信息
     * @param appId
     * @param groupId
     * @param userId
     * @return
     */
    public GroupMemberMark getMemberMark(String appId,String groupId,long userId,long markedUserId){
        GroupMemberMark groupMemberMark = null;
        String dbName = getDatabaseName(appId);
        BasicDBObject filter = new BasicDBObject();
        filter.put("groupId",groupId);
        filter.put("userId", userId);
        filter.put("markedUserId",markedUserId);
        MongoCursor<Document> cursor = find(dbName, collName, filter);
        while (cursor.hasNext()) {
            Document item = cursor.next();
            groupMemberMark = (GroupMemberMark) BeanTransUtils.document2Bean(item, GroupMemberMark.class);
            break;
        }
        return groupMemberMark;
    }
    
    /**
     * 批量保存群成员信息
     *
     * @return 是否插入成功
     */
    public boolean save(String appId, List<GroupMemberMark> groupMemberMarkList) {
        List<Long> list = new ArrayList<>();
        //加入到mongodb
        String dbName = getDatabaseName(appId);
        List<Document> docs = new ArrayList<Document>();
        for (int i = 0; i < groupMemberMarkList.size(); i++) {
            GroupMemberMark groupMember = groupMemberMarkList.get(i);
            Document doc = BeanTransUtils.bean2Document(groupMember);
            docs.add(doc);
        }
        //加入到redis
        return insertBatch(dbName, collName, docs);
    }

}

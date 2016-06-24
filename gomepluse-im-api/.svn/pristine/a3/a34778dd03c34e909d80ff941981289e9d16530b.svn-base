package com.gomeplus.im.api.mongo;

import com.gomeplus.im.api.global.Constant;
import com.gomeplus.im.api.global.Global;
import com.gomeplus.im.api.model.GroupMember;
import com.gomeplus.im.api.utils.BeanTransUtils;
import com.gomeplus.im.api.utils.JedisClusterClient;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

/**
 * 群成员数据库操作类
 */
public class GroupMemberDao extends BaseDao {
    private static Logger log = LoggerFactory.getLogger(GroupMemberDao.class);
    private final static String collName = "t_group_member";
    private final static int DEFAULT_GROUP_PAGE_SIZE=100;
    /**
     * redis中添加群组成员userId
     *
     * @param appId
     * @param groupId
     * @param userIds
     */
    private static void addGroupMembersUid2Redis(String appId, String groupId, List<Long> userIds) {
        JedisCluster jedis = JedisClusterClient.INSTANCE.getJedisCluster();
        String key = appId + "_" + groupId + Global.GROUP_MEMBERS_SUFFIX;
        log.info("addGroupMembersUid2Redis start,key:{},groupMember:{}", key, userIds.toString());
        if (userIds != null && !userIds.isEmpty()) {
            List<String> list = convertStringList(userIds);
            String[] groupMembers = list.toArray(new String[list.size()]);
            jedis.sadd(key, groupMembers);
            String groupMemberInitSeqKey = appId + "_" + groupId + Global.GROUP_MEMBER_INITSEQ_SUFFIX;
            for (Long uid : userIds) {
                String memberInitSeqKey = appId + "_" + uid + Global.GROUP_INITSEQ_INCR_SUFFIX;
                String initSeq = jedis.hget(memberInitSeqKey, groupId);
                int seq = 0;
                if (StringUtils.isNotBlank(initSeq)) {
                    seq = Integer.parseInt(initSeq);
                }
                jedis.hset(groupMemberInitSeqKey, String.valueOf(uid), String.valueOf(seq));
            }
        }
        log.info("addGroupMembersUid2Redis success,key:{},groupMember:{}", key, userIds.toString());
    }

    private static List<String> convertStringList(List<Long> userIds) {
        List<String> list = new ArrayList<>();
        for (Long uid : userIds) {
            list.add(String.valueOf(uid));
        }
        return list;
    }

    /**
     * 移除redis中群组成员uid
     *
     * @param appId
     * @param groupId
     * @param userIds
     */
    private static void delGroupMembersUid2Redis(String appId, String groupId, List<Long> userIds) {
        JedisCluster jedis = JedisClusterClient.INSTANCE.getJedisCluster();
        String key = appId + "_" + groupId + Global.GROUP_MEMBERS_SUFFIX;
        log.info("delGroupMembersUid2Redis start,key:{},groupMember:{}", key, userIds.toString());
        if (userIds != null && !userIds.isEmpty()) {
            List<String> list = convertStringList(userIds);
            String[] groupMembers = list.toArray(new String[list.size()]);
            jedis.srem(key, groupMembers);
        }
        log.info("delGroupMembersUid2Redis success,key:{},groupMember:{}", key, userIds.toString());
    }

    /**
     * 移除redis中全部成员
     *
     * @param appId
     * @param groupId
     */
    private static void delAllGroupMember2Redis(String appId, String groupId) {
        JedisCluster jedis = JedisClusterClient.INSTANCE.getJedisCluster();
        String key = appId + "_" + groupId + groupId + Global.GROUP_MEMBERS_SUFFIX;
        log.info("delAllGroupMember2Redis start,key:{}", key);
        jedis.del(key);
        log.info("delAllGroupMember2Redis success,key:{}", key);
    }

    /**
     * 保存群成员信息
     *
     * @return 是否插入成功
     */
    public boolean save(String appId, GroupMember groupMember) {
        //加入到redis
        List<Long> list = new ArrayList<>();
        list.add(groupMember.getUserId());
        addGroupMembersUid2Redis(appId, groupMember.getGroupId(), list);
        //加入到mongodb
        String dbName = getDatabaseName(appId);
        Document doc = BeanTransUtils.bean2Document(groupMember);
        return insert(dbName, collName, doc);
    }

    /**
     * 修改seq
     *
     * @param groupMember
     * @return
     */
    public boolean updateSeq(String appId, GroupMember groupMember) {
        String dbName = getDatabaseName(appId);
        String groupId = groupMember.getGroupId();
        long userId = groupMember.getUserId();
        //long initSeq = groupMember.getInitSeq();
//        long readSeq = groupMember.getReadSeq();

        BasicDBObject filter = new BasicDBObject();
        filter.put("groupId", groupId);
        filter.put("userId", userId);

        Document newdoc = new Document();
//        if (initSeq >= 0) {
//            newdoc.put("initSeq", initSeq);
//        }
//        if (readSeq >= 0) {
//            newdoc.put("readSeq", readSeq);
//        }
        newdoc.put("updateTime", System.currentTimeMillis());

        return update(dbName, collName, filter, newdoc);
    }

    /**
     * 修改成员状态
     *
     * @param groupId
     * @param userId
     * @param status
     * @return
     */
    public boolean updateStatus(String appId, String groupId, long userId, int status) {
        String dbName = getDatabaseName(appId);
        BasicDBObject filter = new BasicDBObject();
        filter.put("groupId", groupId);
        filter.put("userId", userId);

        Document newdoc = new Document();
        if (status >= 0) {
            newdoc.put("status", status);
        }
        newdoc.put("updateTime", System.currentTimeMillis());

        return update(dbName, collName, filter, newdoc);
    }
    
    /**
     * 修改成员状态
     *
     * @param groupId
     * @param userId
     * @param status
     * @return
     */
    public boolean updateNickName(String appId, String groupId, long userId, String nickName) {
        String dbName = getDatabaseName(appId);
        BasicDBObject filter = new BasicDBObject();
        filter.put("groupId", groupId);
        filter.put("userId", userId);

        Document newdoc = new Document();
        newdoc.put("nickName", nickName);
        newdoc.put("updateTime", System.currentTimeMillis());

        return update(dbName, collName, filter, newdoc);
    }

    /**
     * 修改seq和成员状态
     *
     * @param groupMember
     * @param status
     * @return
     */
    public boolean updateSeqAndStatus(String appId, GroupMember groupMember, int status) {
        String dbName = getDatabaseName(appId);
        String groupId = groupMember.getGroupId();
        long userId = groupMember.getUserId();
        //long initSeq = groupMember.getInitSeq();
//        long readSeq = groupMember.getReadSeq();

        BasicDBObject filter = new BasicDBObject();
        filter.put("groupId", groupId);
        filter.put("userId", userId);

        Document newdoc = new Document();
//        if (initSeq >= 0) {
//            newdoc.put("initSeq", initSeq);
//        }
//        if (readSeq >= 0) {
//            newdoc.put("readSeq", readSeq);
//        }
        if (status >= 0) {
            newdoc.put("status", status);
        }
        newdoc.put("updateTime", System.currentTimeMillis());

        return update(dbName, collName, filter, newdoc);
    }

    /**
     * 修改identity
     *
     * @param groupId
     * @param userId
     * @param identity
     * @return
     */
    public boolean updateIdentity(String appId, String groupId, long userId, int identity) {
        String dbName = getDatabaseName(appId);
        BasicDBObject filter = new BasicDBObject();
        filter.put("groupId", groupId);
        filter.put("userId", userId);

        Document newdoc = new Document();
        if (identity >= 0) {
            newdoc.put("identity", identity);
        }
        newdoc.put("updateTime", System.currentTimeMillis());

        return update(dbName, collName, filter, newdoc);
    }

    /**
     * 修改屏蔽群消息状态
     *
     * @param groupId
     * @param userId
     * @param isShield
     * @return
     */
    public boolean updateShield(String appId, String groupId, long userId, int isShield) {
        String dbName = getDatabaseName(appId);
        BasicDBObject filter = new BasicDBObject();
        filter.put("groupId", groupId);
        filter.put("userId", userId);

        Document newdoc = new Document();
        if (isShield >= 0) {
            newdoc.put("isShield", isShield);
        }
        newdoc.put("updateTime", System.currentTimeMillis());

        return update(dbName, collName, filter, newdoc);
    }

    /**
     * 修改群置顶状态
     *
     * @param groupId
     * @param userId
     * @param isTop
     * @return
     */
    public boolean updateIsTop(String appId, String groupId, long userId, int isTop) {
        String dbName = getDatabaseName(appId);
        BasicDBObject filter = new BasicDBObject();
        filter.put("groupId", groupId);
        filter.put("userId", userId);
        Document newdoc = new Document();
        if (isTop >= 0) {
            newdoc.put("isTop", isTop);
        }
        newdoc.put("updateTime", System.currentTimeMillis());

        return update(dbName, collName, filter, newdoc);
    }


    /**
     * 批量保存群成员信息
     *
     * @return 是否插入成功
     */
    public boolean save(String appId, List<GroupMember> groupMembers) {
        List<Long> list = new ArrayList<>();
        String groupId = "";
        //加入到mongodb
        String dbName = getDatabaseName(appId);
        List<Document> docs = new ArrayList<Document>();
        for (int i = 0; i < groupMembers.size(); i++) {
            GroupMember groupMember = groupMembers.get(i);
            Document doc = BeanTransUtils.bean2Document(groupMember);
            docs.add(doc);
            list.add(groupMember.getUserId());
            if (groupId.isEmpty()) {
                groupId = groupMember.getGroupId();
            }
        }
        //加入到redis
        addGroupMembersUid2Redis(appId, groupId, list);
        return insertBatch(dbName, collName, docs);
    }

    /**
     * 删除群组关系信息(删除单条)
     *
     * @return boolean 是否删除成功
     */
    public int delGroupMemberById(String appId, String id) {
        String dbName = getDatabaseName(appId);
        return deleteById(dbName, collName, id);
    }

    /**
     * 根据 群组id删除群组所有成员
     *
     * @return 是否删除成功
     */
    public boolean delGroupAllMember(String appId, String groupId) {
        //从redis移除
        delAllGroupMember2Redis(appId, groupId);
        //从mongodb移除
        String dbName = getDatabaseName(appId);
        BasicDBObject del = new BasicDBObject();
        del.put("groupId", groupId);
        return delete(dbName, collName, del);
    }

    /**
     * 根据 群组id 和 用户id 删除记录
     *
     * @return 是否删除成功
     */
    public boolean delGroupMember(String appId, String groupId, long userId) {
        //从redis移除
        List<Long> list = new ArrayList<>();
        list.add(userId);
        delGroupMembersUid2Redis(appId, groupId, list);
        //从mongodb移除
        String dbName = getDatabaseName(appId);
        BasicDBObject del = new BasicDBObject();
        del.put("groupId", groupId);
        del.put("userId", userId);
        return delete(dbName, collName, del);
    }

    /**
     * 删除多个成员
     *
     * @return 是否删除成功
     */
    public boolean delGroupMembers(String appId, String groupId, List<Long> members) {
        List<Long> list = new ArrayList<>();
        //从mongodb移除
        String dbName = getDatabaseName(appId);
        BasicDBObject[] deleteObjArr = new BasicDBObject[members.size()];
        for (int i = 0; i < members.size(); i++) {
            long memberId = members.get(i);
            list.add(memberId);
            BasicDBObject del = new BasicDBObject();
            del.put("groupId", groupId);
            del.put("userId", memberId);
            deleteObjArr[i] = del;
        }
        //从redis移除
        delGroupMembersUid2Redis(appId, groupId, list);
        BasicDBObject delObject = new BasicDBObject().append(QueryOperators.OR, deleteObjArr);
        return deleteAll(dbName, collName, delObject);
    }

    /**
     * 设置群成员读取群消息的位置
     *
     * @return 是否设置成功
     */
    public boolean setReadSeq(String appId, String groupId, long userId, long readSeq) {
        String dbName = getDatabaseName(appId);
        BasicDBObject filter = new BasicDBObject();
        filter.put("groupId", groupId);
        filter.put("userId", userId);
        Document newdoc = new Document();
        newdoc.put("readSeq", readSeq);

        return update(dbName, collName, filter, newdoc);
    }

    /**
     * 获取成员读取群消息的位置
     *
     * @return 读取群消息的位置 如果是-1，表示读取失败
     */
    public long getReadSeq(String appId, String groupId, long userId) {
        String dbName = getDatabaseName(appId);
        MongoCursor<Document> cursor = null;
        long readSeq = -1L;
        try {
            BasicDBObject filter = new BasicDBObject();
            filter.put("groupId", groupId);
            filter.put("userId", userId);

            cursor = find(dbName, collName, filter);
            if (cursor.hasNext()) {
                Document item = cursor.next();
                readSeq = item.getLong("readSeq");
            }
        } catch (Exception e) {
            log.error("GroupMemberDao getReadSeq:", e);
        } finally {
            cursorClose(cursor);
        }
        return readSeq;
    }

    /**
     * 获取群内 所有已通过的成员关系
     *
     * @param groupId
     * @return
     */
    public List<GroupMember> listGroupMembers(String appId, String groupId) {
        String dbName = getDatabaseName(appId);
        List<GroupMember> list = new ArrayList<GroupMember>();
        MongoCollection<Document> coll = this.getCollection(dbName, collName);
        BasicDBObject where = new BasicDBObject();
        where.put("groupId", groupId);
        where.put("status", Constant.GROUP_STATUS.E_GROUP_STATUS_OK.value);
        MongoCursor<Document> cursor = null;
        cursor = coll.find(where).iterator();
        if (cursor == null) {
            return list;
        }
        while (cursor.hasNext()) {
            Document item = cursor.next();
            GroupMember been = (GroupMember) BeanTransUtils.document2Bean(item, GroupMember.class);
            list.add(been);
        }

        return list;
    }

    /**
     * 分页获取群内 所有成员关系
     *
     * @param groupId
     * @param time
     */
    public List<GroupMember> listGroupMembers(String appId, String groupId, long time, int status,int page, int size) {
        String dbName = getDatabaseName(appId);
        List<GroupMember> list = new ArrayList<GroupMember>();
        MongoCollection<Document> coll = this.getCollection(dbName, collName);
        BasicDBObject where = new BasicDBObject();
        where.put("groupId", groupId);
        if (status >= 0) {
            where.put("status", status);
        }
        where.put("updateTime", new BasicDBObject("$gte", time));
        Bson sort = new BasicDBObject("joinTime", 1);
        MongoCursor<Document> cursor = null;
        if (page<=0) {
        	cursor = coll.find(where).iterator();
		}else{
			if (size<=0) {
				size=DEFAULT_GROUP_PAGE_SIZE;//默认为100
			}
			cursor = coll.find(where).sort(sort).skip((page - 1) * size).limit(size).iterator();
		}
        if (cursor == null) {
            return list;
        }
        while (cursor.hasNext()) {
            Document item = cursor.next();
            GroupMember been = (GroupMember) BeanTransUtils.document2Bean(item, GroupMember.class);
            list.add(been);
        }

        return list;
    }

    /**
     * 获取指定的群成员关系
     *
     * @param groupId
     * @param userId
     */
    public GroupMember getGroupMemberByUid(String appId, String groupId, long userId) {
        String dbName = getDatabaseName(appId);
        MongoCursor<Document> cursor = null;
        try {
            BasicDBObject filter = new BasicDBObject();
            filter.put("groupId", groupId);
            filter.put("userId", userId);
            cursor = find(dbName, collName, filter);
            if(cursor.hasNext()) {
                Document item = cursor.next();
                GroupMember been = (GroupMember) BeanTransUtils.document2Bean(item, GroupMember.class);
                return been;
            }
        } catch (Exception e) {
            log.error("GroupMemberDao getGroupUsers:", e);
        } finally {
            cursorClose(cursor);
        }

        return null;
    }


    /**
     * 根据用户Id，获取其关联所有的群
     *
     * @param userId
     */
    public List<GroupMember> listMemberGroups(String appId, long userId) {
        String dbName = getDatabaseName(appId);
        MongoCursor<Document> cursor = null;
        List<GroupMember> groupMembers = null;
        try {
            groupMembers = new ArrayList<>();
            BasicDBObject filter = new BasicDBObject();
            filter.put("userId", userId);
            cursor = find(dbName, collName, filter);
            while (cursor.hasNext()) {
                Document item = cursor.next();
                GroupMember been = (GroupMember) BeanTransUtils.document2Bean(item, GroupMember.class);
                groupMembers.add(been);
            }
        } catch (Exception e) {
            log.error("GroupMemberDao listMemberGroups:", e.toString());
        }
        return groupMembers;
    }

    /**
     * 统计群成员数
     *
     * @param groupId
     * @return
     */
    public long countGroupMember(String appId, String groupId) {
        String dbName = getDatabaseName(appId);
        BasicDBObject filter = new BasicDBObject();
        filter.put("groupId", groupId);
        return getCount(dbName, collName, filter);
    }

}

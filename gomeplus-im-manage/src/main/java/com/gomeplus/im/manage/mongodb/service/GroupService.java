package com.gomeplus.im.manage.mongodb.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gomeplus.im.manage.model.Group;
import com.gomeplus.im.manage.mongodb.dao.GroupDao;
import com.gomeplus.im.manage.mongodb.dao.GroupMemberDao;
import com.gomeplus.im.manage.mongodb.model.GroupMember;
import com.gomeplus.im.manage.mongodb.model.GroupSearchModel;
import com.gomeplus.im.manage.pageSupport.PageInfo;
import com.gomeplus.im.manage.utils.TimeUtil;

/**
 * Created by wangshikai on 2015/12/16.
 */
public class GroupService {
    private static Logger log = LoggerFactory.getLogger(GroupService.class);

    private GroupDao groupDao = new GroupDao();

    private GroupMemberDao groupMemberDao = new GroupMemberDao();
    
    /**
     * 获取所有群组信息--Cb-whf-20160219
     * @return
     */
    public List<Group> getAllGroupList(String appId,String startTime,String endTime,PageInfo pageInfo){
        List<Group> groups = null;
        try {
        	if(StringUtils.isNotEmpty(startTime)){
        		startTime = TimeUtil.normalTimeToMillisecondRS(startTime+":00");
        	}
        	if(StringUtils.isNotEmpty(endTime)){
        		endTime = TimeUtil.normalTimeToMillisecondRS(endTime+":59");
        	}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
        try{
            groups = groupDao.listAllGroups(appId,startTime,endTime,pageInfo);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return groups;
    }
    /**
     * 获取所有群组信息--Cb-whf-20160429
     * @return
     */
    public Group getGroupInfo(String appId, String groupId,PageInfo pageInfo){
        Group group = null;
        try{
            group = groupDao.getGroupInfo(appId,groupId,pageInfo);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			group.setFormateCreateTime(formatter.format(new Date(group.getCreateTime())));
			group.setFormateUpdateTime(formatter.format(new Date(group.getUpdateTime())));
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return group;
    }
    /**
     * 获取用户群组列表
     * @param uid
     * @param time
     * @return
     */
    public List<Group> getGroupList(String appId,String type, String userId,long time,PageInfo pageInfo){
        List<Group> groups = null;
        GroupSearchModel groupSearchModel = new GroupSearchModel();
        groupSearchModel.setAppId(appId);
        groupSearchModel.setUserId(userId);
        groupSearchModel.setType(type);
        try{
            List<GroupMember> members = groupMemberDao.listMemberGroups(groupSearchModel,pageInfo);
            List<String> groupIds = new ArrayList<String>();
            for(GroupMember member : members) {
                groupIds.add(member.getGroupId());
            }
            groups = groupDao.listGroup(groupIds,groupSearchModel.getAppId(), time);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return groups;
    }

    /**
     * 查看群组内成员信息
     * @return
     */
    public List<GroupMember> getGroupMembers(String appId, String type, String groupId, long time, int status,PageInfo pageInfo){
        List<GroupMember> list = null;
        GroupSearchModel groupSearchModel = new GroupSearchModel();
        groupSearchModel.setAppId(appId);
        groupSearchModel.setGroupId(groupId);
        groupSearchModel.setStatus(status);
        groupSearchModel.setType(type);
        try{
            list = groupMemberDao.listGroupMembers(groupSearchModel,pageInfo);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return list;
    }

    /**
     * 查看群组内指定的某个成员信息
     * @return
     */
    public GroupMember getMember(String groupId, long uid){
        GroupMember member = null;
        try{
            member = groupMemberDao.getGroupMemberByUid(groupId,uid);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return member;
    }

}

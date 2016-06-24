package cn.com.gome.api.service.impl;

import cn.com.gome.api.dao.GroupDao;
import cn.com.gome.api.dao.GroupMemberDao;
import cn.com.gome.api.dao.GroupQuitMemberDao;
import cn.com.gome.api.global.Constant;
import cn.com.gome.api.model.*;
import cn.com.gome.api.model.request.ReqGroup;
import cn.com.gome.api.model.request.ReqMessage;
import cn.com.gome.api.service.GroupService;
import cn.com.gome.api.service.MessageService;
import cn.com.gome.api.service.UserInfoService;
import cn.com.gome.api.threadPool.ThreadPool;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

	private static Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

	private final static GroupDao groupDao = new GroupDao();
	private final static GroupMemberDao groupMemberDao = new GroupMemberDao();
	private final static GroupQuitMemberDao quitMemberDao = new GroupQuitMemberDao();

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 创建群组
	 */
	public ResultModel<Object> addGroup(ReqGroup reqGroup, final String appId) {
//		String groupId = cn.com.gome.api.utils.StringUtils.getUuid();
		String groupId = reqGroup.getGroupId();
		long imUserId = reqGroup.getImUserId();//群创建者imUserId
		boolean isValid = userInfoService.checkImUserId(appId,imUserId);
		if(!isValid){
			log.error("addGroup,用户不合法,未注册,appId:{},uid:{}",appId,imUserId);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误,imUserId未注册,imUserId="+imUserId,new HashMap<>());
		}
		if(!StringUtils.isNotEmpty(appId)||!StringUtils.isNotEmpty(groupId)){
			log.error("参数错误,appId:{},groupId:{}",appId,groupId);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误",new HashMap<>());
		}
		List<Member> members = reqGroup.getMembers();
		long time = System.currentTimeMillis();
		Group group = groupDao.getGroupById(appId, groupId);
		if(group != null){
			log.error("群组已存在,addGroup, appId:{},groupId:{},imUserId:{}",appId,groupId,imUserId);
			if(group.getUid() == imUserId){
				return new ResultModel<Object>(ResultModel.RESULT_OK, "群组已存在",new HashMap<>());
			}
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "groupId参数错误，群组已存在",new HashMap<>());
		}
		//保存群组信息
		group = new Group();
		group.setGroupId(groupId);
		group.setType(Constant.CHAT_TYPE.E_CHAT_TYP_GROUP.value);
		group.setUid(imUserId);
		group.setSeq(0L);
		group.setIsDele(0);
		group.setCreateTime(time);
		group.setUpdateTime(time);
		groupDao.save(appId,group);

		//保存群创建者
		GroupMember member = new GroupMember();
		member.setGroupId(groupId);
		member.setUid(imUserId);
		member.setJoinTime(time);
		member.setIdentity(Constant.GROUP_MEMBER_IDENTITY.E_MEMBER_CREATOR.value);
		member.setUpdateTime(time);
		member.setInitSeq(0);
		member.setReadSeq(0);
		groupMemberDao.save(appId,member);
		if(members != null){
			List<GroupMember> groupMemberList = new ArrayList<>();
			for(Member mem : members){
				GroupMember m = new GroupMember();
				m.setGroupId(groupId);
				m.setUid(mem.getImUserId());
				m.setJoinTime(time);
				m.setIdentity(Constant.GROUP_MEMBER_IDENTITY.E_MEMBER_ORDINARY.value);
				m.setInitSeq(0);
				m.setReadSeq(0);
				groupMemberList.add(m);
			}
			groupMemberDao.save(appId,groupMemberList);
		}
		GroupExtra extra = reqGroup.getExtra();
		if(extra != null ){
			String senderName = extra.getNickName();
			String groupName = extra.getGroupName();
			String msgBody = extra.getMsgBody();
			String ext = extra.getExt();
			if(StringUtils.isNotEmpty(senderName) && StringUtils.isNotEmpty(groupName)){
				final ReqMessage reqMessage = getReqMessage(groupId,groupName,imUserId,senderName,true,msgBody,ext);
				ThreadPool pool = ThreadPool.getInstance();
				pool.addTask(new Runnable() {
					@Override
					public void run() {
						messageService.notifyMessage(reqMessage,appId,true);
					}
				});
			}
		}
		log.info("addGroup success,appId{},groupId:{},uid:{}",appId,groupId,imUserId);
		return new ResultModel<Object>(ResultModel.RESULT_OK, "创建成功",group);
	}
	
	/**
	 * 加入群
	 */
	public ResultModel<Object> joinGroup(ReqGroup reqGroup, final String appId) {
		String groupId = reqGroup.getGroupId();
		List<Member> members = reqGroup.getMembers();
		if(members == null || members.isEmpty()){
			log.error("joinGroup,members参数错误");
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "members参数错误",new HashMap<>());
		}
		if(!StringUtils.isNotEmpty(appId)){
			log.error("joinGroup,参数错误,appId:{}",appId);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "appId参数错误",new HashMap<>());
		}
		Group group = groupDao.getValidGroup(appId, groupId);
		if(group == null){
			log.error("joinGroup,群组不存在,appId:{},groupId:{}",appId,groupId);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "groupId参数错误，群不存在",new HashMap<>());
		}
		//long seq = group.getSeq() + 1;
		long seq = group.getSeq();
		long time = System.currentTimeMillis();
		List<GroupMember> memberList = new ArrayList<>();
		List<Long> imUserIdList = new ArrayList<>();
		for(Member m : members){
			long imUserId = m.getImUserId();
			if(!userInfoService.checkImUserId(appId,imUserId)){
				//不合法的未注册的imUserId,跳过
				log.error("joinGroup,appId={},未注册的imUserId={}",appId,imUserId);
				continue;
			}
			if(imUserIdList.contains(imUserId)){
				log.info("joinGroup,加入成员的imUserId参数重复出现,appId:{},groupId:{},imUserId:{}",appId,groupId,imUserId);
				continue;
			}else{
				imUserIdList.add(imUserId);
			}
			quitMemberDao.delQuitMember(appId,groupId,imUserId);
			GroupMember member = groupMemberDao.getGroupMemberByUid(appId,groupId,imUserId);
			if(member == null) {
				member = new GroupMember();
				member.setGroupId(groupId);
				member.setUid(imUserId);
				member.setIdentity(Constant.GROUP_MEMBER_IDENTITY.E_MEMBER_ORDINARY.value);
				member.setJoinTime(time);
				member.setInitSeq(seq);
				member.setReadSeq(seq);
				memberList.add(member);
			}else{
				log.info("joinGroup,成员已在群组内,appId:{},groupId:{},imUserId:{}",appId,groupId,imUserId);
			}
		}
		if(!memberList.isEmpty()){
			groupMemberDao.save(appId,memberList);
		}
		GroupExtra extra = reqGroup.getExtra();
		if(extra != null ){
			String senderName = extra.getNickName();
			String groupName = extra.getGroupName();
			String msgBody = extra.getMsgBody();
			String ext = extra.getExt();
			if(StringUtils.isNotEmpty(senderName) && StringUtils.isNotEmpty(groupName)){
				final ReqMessage reqMessage = getReqMessage(groupId,groupName,extra.getImUserId(),senderName,true,msgBody,ext);
				ThreadPool pool = ThreadPool.getInstance();
				pool.addTask(new Runnable() {
					@Override
					public void run() {
						messageService.notifyMessage(reqMessage,appId,true);
					}
				});

			}
		}
		log.info("joinGroup success,appId:{},groupId:{}", appId, groupId);
		return new ResultModel<Object>(ResultModel.RESULT_OK, "加入成功",reqGroup);
	}

	/**
	 * 退出群
	 */
	public ResultModel<Object> quitGroup(ReqGroup reqGroup, final String appId) {
		String groupId = reqGroup.getGroupId();
		long uid = reqGroup.getImUserId();
		if(!StringUtils.isNotEmpty(appId)){
			log.error("quitGroup,appId参数错误,appId:{}",appId);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "appId参数错误",new HashMap<>());
		}
		Group group = groupDao.getValidGroup(appId, groupId);
		if(group == null) {
			log.error("quitGroup,群组不存在,appId:{},groupId:{}",appId,groupId);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误",new HashMap<>());
		}
		GroupMember member = groupMemberDao.getGroupMemberByUid(appId,groupId, uid);
		if(member == null) {
			log.error("quitGroup,imUserId不在群组内,appId:{},groupId:{},uid:{}", appId, groupId, uid);
			return new ResultModel<Object>(ResultModel.RESULT_OK, "imUserId不在群组内",new HashMap<>());
		}
		GroupQuitMember quitMember = new GroupQuitMember(uid, groupId);
		quitMemberDao.save(appId, quitMember);
		groupMemberDao.delGroupMember(appId, groupId, uid);

		//判断群组成员为0是，设置群组为删除状态
		long count = groupMemberDao.countGroupMember(appId,groupId);
		if(count <= 0) {
			groupDao.setGroupIsDel(appId,groupId, Constant.GROUP_DEL.E_GROUP_DEL_OK.value);
		}

		GroupExtra extra = reqGroup.getExtra();
		if(extra != null ){
			String senderName = extra.getNickName();
			String groupName = extra.getGroupName();
			String msgBody = extra.getMsgBody();
			String ext = extra.getExt();
			if(StringUtils.isNotEmpty(senderName) && StringUtils.isNotEmpty(groupName)){
				final ReqMessage reqMessage = getReqMessage(groupId,groupName,uid,senderName,true,msgBody,ext);
				ThreadPool pool = ThreadPool.getInstance();
				pool.addTask(new Runnable() {
					@Override
					public void run() {
						messageService.notifyMessage(reqMessage,appId,true);
					}
				});
			}
		}
		log.info("quitGroup success,appId:{},groupId:{},uid:{}", appId, groupId, uid);
		return new ResultModel<Object>(ResultModel.RESULT_OK, "退出成功",new HashMap<>());
	}
	
	/**
	 * 踢人
	 */
	public ResultModel<Object> kickGroup(ReqGroup reqGroup, final String appId) {
		String groupId = reqGroup.getGroupId();
		if(!StringUtils.isNotEmpty(appId)){
			log.error("kickGroup,appId参数错误,appId:{}",appId);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "appId参数错误",new HashMap<>());
		}
		List<Long> memberIds = reqGroup.getMemberIds();
		if(memberIds == null || memberIds.size() <= 0) {
			log.error("kickGroup,参数错误,memberIds为空");
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误",new HashMap<>());
		}
		Group group = groupDao.getValidGroup(appId, groupId);
		if(group == null) {
			log.error("kickGroup,群组不存在,appId:{},groupId:{}",appId,groupId);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误",new HashMap<>());
		}
		long uid = reqGroup.getImUserId();
		GroupMember sender = groupMemberDao.getGroupMemberByUid(appId,groupId, uid);
		if(sender == null) {
			log.error("kickGroup,imUserId不是群创建者,群内无此人,appId:{},groupId:{},imUserId:{}",appId,groupId,uid);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误,imUserId不在群组内",new HashMap<>());
		}
		List<GroupMember> members = new ArrayList<GroupMember>();
		members.add(sender);
		int identity = sender.getIdentity();
		if(identity == Constant.GROUP_MEMBER_IDENTITY.E_MEMBER_ORDINARY.value) {
			log.error("kickGroup,无权限,imUserId身份不是群创建者,appId:{},groupId:{},imUserId:{}",appId,groupId,uid);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "无权限",new HashMap<>());
		}

		//有效的被踢成员
		List<Long> kickMembers = new ArrayList<>();

		for(long memberId : memberIds) {
			GroupMember member = groupMemberDao.getGroupMemberByUid(appId,groupId, memberId);
			if(member != null){
				members.add(member);
				GroupQuitMember quitMember = new GroupQuitMember(memberId, groupId);
				quitMemberDao.save(appId,quitMember);
				kickMembers.add(memberId);
			}
		}
		if(kickMembers.size() > 0){
			groupMemberDao.delGroupMembers(appId,groupId, kickMembers);
		}

		log.info("members size=[{}]", members.size());
		GroupExtra extra = reqGroup.getExtra();
		if(extra != null ){
			String senderName = extra.getNickName();
			String groupName = extra.getGroupName();
			String msgBody = extra.getMsgBody();
			String ext = extra.getExt();
			if(StringUtils.isNotEmpty(senderName) && StringUtils.isNotEmpty(groupName)){
				final ReqMessage reqMessage = getReqMessage(groupId, groupName, uid, senderName, true, msgBody,ext);
				ThreadPool pool = ThreadPool.getInstance();
				pool.addTask(new Runnable() {
					@Override
					public void run() {
						messageService.notifyMessage(reqMessage,appId,true);
					}
				});
			}
		}
		log.info("kickGroup success,appId:{},groupId:{},imUserId:{},kickMembers:{}", appId, groupId, uid, kickMembers.toString());
		return new ResultModel<Object>(ResultModel.RESULT_OK, groupId,new HashMap<>());
	}
	
	/**
	 * 解散群
	 */
	public ResultModel<Object> disbandGroup(ReqGroup reqGroup, final String appId) {
		String groupId = reqGroup.getGroupId();
		if(!StringUtils.isNotEmpty(appId)){
			log.error("disbandGroup,appId参数错误,appId:{}",appId);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "appId参数错误",new HashMap<>());
		}
		Group group = groupDao.getValidGroup(appId, groupId);
		if(group == null || group.getIsDele() == Constant.GROUP_DEL.E_GROUP_DEL_OK.value) {
			log.error("disbandGroup,群组不存在,appId:{},groupId:{}",appId,groupId);
			return new ResultModel<Object>(ResultModel.RESULT_OK, "群组不存在",new HashMap<>());
		}
		long uid = reqGroup.getImUserId();
		GroupMember sender = groupMemberDao.getGroupMemberByUid(appId,groupId, uid);
		if(sender == null) {
			log.error("disbandGroup,群创建者imUserId不存在,appId:{},groupId:{},imUserId:{}",appId,groupId,uid);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误",new HashMap<>());
		}
		int identity = sender.getIdentity();
		if(identity == Constant.GROUP_MEMBER_IDENTITY.E_MEMBER_ORDINARY.value) {
			log.error("disbandGroup,无权限,imUserId不是群创建者,appId:{},groupId:{},imUserId:{}",appId,groupId,uid);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "无权限",new HashMap<>());
		}
		groupDao.setGroupIsDel(appId,groupId, Constant.GROUP_DEL.E_GROUP_DEL_OK.value);

		List<GroupMember> members = groupMemberDao.listGroupMembers(appId,groupId);
		List<GroupQuitMember> groupQuitMembers = new ArrayList<>();
		for(GroupMember member: members) {
			GroupQuitMember quitMember = new GroupQuitMember(member.getUid(), groupId);
			groupQuitMembers.add(quitMember);
		}
		quitMemberDao.saveGroupQuitMembers(appId,groupQuitMembers);
		groupMemberDao.delGroupAllMember(appId,groupId);

		GroupExtra extra = reqGroup.getExtra();
		if(extra != null ){
			String senderName = extra.getNickName();
			String groupName = extra.getGroupName();
			String msgBody = extra.getMsgBody();
			String ext = extra.getExt();
			if(StringUtils.isNotEmpty(senderName) && StringUtils.isNotEmpty(groupName)){
				final ReqMessage reqMessage = getReqMessage(groupId, groupName, uid, senderName, true, msgBody,ext);
				ThreadPool pool = ThreadPool.getInstance();
				pool.addTask(new Runnable() {
					@Override
					public void run() {
						messageService.notifyMessage(reqMessage,appId,true);
					}
				});
			}
		}
		log.info("disbandGroup success,appId:{},groupId:{},imUserId:{}",appId,groupId,uid);
		return new ResultModel<Object>(ResultModel.RESULT_OK, "成功解散群组", reqGroup);
	}

	public ReqMessage getReqMessage(String groupId,String groupName,long senderId,String senderName,boolean isPerrsist,String msgBody,String ext){
		ReqMessage reqMessage = new ReqMessage();
		reqMessage.setSenderId(senderId);
		reqMessage.setSenderName(senderName);
		reqMessage.setGroupType(2);
		reqMessage.setGroupId(groupId);
		reqMessage.setGroupName(groupName);
		reqMessage.setIsPersist(isPerrsist);
		reqMessage.setMsgBody(msgBody);
		reqMessage.setExtra(ext);
		return reqMessage;
	}

}

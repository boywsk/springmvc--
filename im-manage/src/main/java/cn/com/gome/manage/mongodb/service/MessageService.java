package cn.com.gome.manage.mongodb.service;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.gome.manage.mongodb.dao.MsgDao;
import cn.com.gome.manage.mongodb.dao.MsgStatsDao;
import cn.com.gome.manage.mongodb.model.GroupMsg;
import cn.com.gome.manage.mongodb.model.MsgStats;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.utils.TimeUtil;

/**
 * Created by wangshikai on 2015/12/16.
 */
public class MessageService {
    private static Logger log = LoggerFactory.getLogger(MessageService.class);

    private MsgDao msgDao = new MsgDao();
    private MsgStatsDao msgStatsDao = new MsgStatsDao();
    /**
     * 查询群组离线消息
     * @param groupId
     * @return
     */
    public List<GroupMsg> getGroupMsgById(String appId,String groupId,String senderId,String senderName,String startTime,String endTime,PageInfo pageInfo){
        List<GroupMsg> msgList = null;
		try {
			if(StringUtils.isNotEmpty(startTime)){
				startTime = TimeUtil.normalTimeToMillisecondRS(startTime+":00");
			}
			if(StringUtils.isNotEmpty(endTime)){
				endTime = TimeUtil.normalTimeToMillisecondRS(endTime+":59");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        try{
        	if(StringUtils.isNotEmpty(groupId)){
        		msgList = msgDao.listGroupMsg(appId,groupId,senderId,senderName,startTime,endTime,pageInfo);
        	}else{
        		msgList = msgDao.listGroupMsg(appId,senderId,senderName,startTime,endTime,pageInfo);
        	}
            
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return msgList;
    }

    public MsgStats getMsgStats(String day){
        MsgStats msgStats = null;
        try{
            msgStats = msgStatsDao.findMsgStats(day);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return msgStats;
    }

}

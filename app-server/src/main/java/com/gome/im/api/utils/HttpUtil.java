package com.gome.im.api.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gome.im.api.global.Constant;
import com.gome.im.api.global.Global;
import com.gome.im.api.model.Member;
import com.gome.im.api.model.request.ReqMessage;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangshikai on 2016/2/24.
 */
public class HttpUtil {
    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static String getSignature(long timestamp) {
        String signature = null;
        try {
            signature = Md5Util.md5Encode(Global.APP_ID + "|" +timestamp+"|"+ Global.APP_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signature;
    }

    private static String appInfo = "?appId="+ Global.APP_ID;

    private static String CENTER_IM_API_URL ="http://localhost:8080/center-im-api";

    public static String GET_APPINFO_PATH = "/app/getAppInfo.json";  //获取appId 和appKey

    public static String GET_USER_TOKEN_PATH = "/user/register.json";

    public static String SEND_NOTIFY_MSG_PATH ="/message/notifyMessage.json";

    public static String SEND_NOTIFY_CMD_MSG_PATH ="/message/notifyCommandMsg.json";

    public static String BROADCAST_MSG_PATH ="/message/broadcastMessage.json";

    public static String CREATE_GROUP_PATH = "/group/addGroup.json";

    public static String JOIN_GROUP_PATH ="/group/joinGroup.json";

    public static String QUIT_GROUP_PATH = "/group/quitGroup.json";

    public static String KICK_GROUP_PATH ="/group/kickGroup.json";

    public static String EDIT_GROUP_PATH = "/group/editGroup.json";

    public static String DISBAND_GROUP_PATH = "/group/disbandGroup.json";

    public static String httpRequest(String path, String parms) {
        HttpClient httpClient = new HttpClient();
        HttpMethod method = null;
        String json = null;
        try {
            long timestamp = System.currentTimeMillis();
            String url = CENTER_IM_API_URL + path + appInfo+"&signature="+getSignature(timestamp)+"&timestamp="+timestamp;
            method = postMethod(url, parms);
            httpClient.setTimeout(5000);
            httpClient.executeMethod(method);
            json = method.getResponseBodyAsString();
        } catch (IOException e) {
            log.error("error:"+e);
        }
        return json;
    }


    private static HttpMethod postMethod(String url,String parms) throws IOException{
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Connection", "close");
        post.setRequestHeader("Content-Type","application/json;charset=utf-8");//Content-Type","application/x-www-form-urlencoded;charset=gbk
        post.setRequestBody(parms);
        post.releaseConnection();
        return post;
    }

    public static String notifyAddGroup(long imUserId,String groupId,String content,String nickName,List<Member> memberList,String groupName){
        //调用center-im-api 创建群组接口  返回一个groupId
        HashMap<String,Object> parm = new HashMap<>();
        parm.put("groupId",groupId);
        parm.put("imUserId",imUserId);

        JSONObject extra = new JSONObject();
        JSONObject contentObject = new JSONObject();
        contentObject.put("content",content);
        contentObject.put("type", Constant.MSG_CONTENT_TYPE.CREATE_GROUP.value);
        extra.put("msgBody",content);
        extra.put("ext",contentObject.toJSONString());
        extra.put("nickName",nickName);
        extra.put("groupName",groupName);
        parm.put("extra",extra);

        List<Object> members = new ArrayList<>();
        for(Member m : memberList){
            JSONObject o = new JSONObject();
            o.put("imUserId",m.getImUserId());
            o.put("memberName",m.getUserName());
            members.add(o);
        }
        parm.put("members",members);
        String json = HttpUtil.httpRequest(HttpUtil.CREATE_GROUP_PATH, JSON.toJSONString(parm));
        return json;
    }

    public static String joinGroup(String groupId,List<Member> memberList,String content,String groupName,String nickName,long imUserId){
        HashMap<String,Object> parm = new HashMap<>();
        parm.put("groupId", groupId);

        JSONObject extra = new JSONObject();
        JSONObject contentObject = new JSONObject();
        contentObject.put("content",content);
        contentObject.put("type", Constant.MSG_CONTENT_TYPE.AUDIT_GROUP.value);

        extra.put("msgBody",content);
        extra.put("ext",contentObject.toJSONString());
        extra.put("imUserId",imUserId);
        extra.put("nickName",nickName);
        extra.put("groupName",groupName);
        parm.put("extra",extra);

        List<Object> members = new ArrayList<>();
        for(Member m : memberList){
            JSONObject o = new JSONObject();
            o.put("imUserId",m.getImUserId());
            o.put("memberName",m.getUserName());
            members.add(o);
        }
        parm.put("members",members);
        String json = HttpUtil.httpRequest(HttpUtil.JOIN_GROUP_PATH, JSON.toJSONString(parm));
        return json;
    }

    public static String quitGroup(long imUserId,String groupId,String content,String nickName,String groupName){
        HashMap<String,Object> parm = new HashMap<>();
        parm.put("groupId", groupId);
        parm.put("imUserId", imUserId);

        JSONObject extra = new JSONObject();

        JSONObject contentObject = new JSONObject();
        contentObject.put("content",content);
        contentObject.put("type", Constant.MSG_CONTENT_TYPE.QUIT_GROUP.value);

        extra.put("msgBody",content);
        extra.put("ext",contentObject.toJSONString());
        extra.put("nickName",nickName);
        extra.put("groupName",groupName);
        parm.put("extra",extra);

        String json = HttpUtil.httpRequest(HttpUtil.QUIT_GROUP_PATH, JSON.toJSONString(parm));
        return json;
    }

    public static String kickGroup(long imUserId,String groupId,List<Long> memberIds,String content,String nickName,String groupName){
        HashMap<String,Object> parm = new HashMap<>();
        parm.put("groupId", groupId);
        parm.put("imUserId", imUserId);
        parm.put("memberIds", memberIds);
        JSONObject extra = new JSONObject();

        JSONObject contentObject = new JSONObject();
        contentObject.put("content",content);
        contentObject.put("type", Constant.MSG_CONTENT_TYPE.KICK_GROUP.value);
        extra.put("msgBody",content);
        extra.put("ext",contentObject.toJSONString());
        extra.put("nickName",nickName);
        extra.put("groupName",groupName);
        parm.put("extra",extra);

        String json = HttpUtil.httpRequest(HttpUtil.KICK_GROUP_PATH, JSON.toJSONString(parm));
        return json;
    }

//    public static String editGroup(long imUserId,String groupId,String content,String nickName,String groupName,String desc){
//        HashMap<String,Object> parm = new HashMap<>();
//        parm.put("appId", Global.APP_ID);
//        parm.put("groupId", groupId);
//        parm.put("imUserId", imUserId);
//        parm.put("content", content);
//        parm.put("nickName", nickName);
//        parm.put("groupName", groupName);
//        parm.put("desc", desc);
//        String json = HttpUtil.httpRequest(HttpUtil.EDIT_GROUP_PATH, JSON.toJSONString(parm));
//        return json;
//    }

    public static String disbandGroup(long imUserId,String groupId,String content,String nickName,String groupName){
        HashMap<String,Object> parm = new HashMap<>();
        parm.put("groupId", groupId);
        parm.put("imUserId", imUserId);
        JSONObject extra = new JSONObject();
        JSONObject contentObject = new JSONObject();
        contentObject.put("content",content);
        contentObject.put("type", Constant.MSG_CONTENT_TYPE.DISBAND_GROUP.value);
        extra.put("msgBody",content);
        extra.put("ext",contentObject.toJSONString());
        extra.put("nickName",nickName);
        extra.put("groupName",groupName);
        parm.put("extra",extra);
        String json = HttpUtil.httpRequest(HttpUtil.DISBAND_GROUP_PATH, JSON.toJSONString(parm));
        return json;
    }

    public static String sendSimpleMsg(long senderId,String senderName,boolean isPersist,long receiveId,String content,int type,boolean senderReceiveMsg){
        ReqMessage msg = new ReqMessage();
        msg.setAppId(Global.APP_ID);
        msg.setGroupType(1);
        msg.setSenderId(senderId);
        msg.setSenderName(senderName);
        msg.setIsPersist(isPersist);
        msg.setReceiveId(receiveId);
        msg.setMsgType(1);
        msg.setSenderReceiveMsg(senderReceiveMsg);
        JSONObject object = new JSONObject();
        object.put("content",content);
        object.put("type",type);
        msg.setMsgBody(content);
        msg.setExtra(object.toJSONString());
        String json = HttpUtil.httpRequest(HttpUtil.SEND_NOTIFY_MSG_PATH, JSON.toJSONString(msg));
        return json;
    }

    public static String sendSimpleCmdMsg(long senderId,String senderName,boolean isPersist,long receiveId,String content,int type,boolean senderReceiveMsg){
        ReqMessage msg = new ReqMessage();
        msg.setAppId(Global.APP_ID);
        msg.setGroupType(1);
        msg.setSenderId(senderId);
        msg.setSenderName(senderName);
        msg.setIsPersist(isPersist);
        msg.setReceiveId(receiveId);
        msg.setMsgType(1);
        msg.setSenderReceiveMsg(senderReceiveMsg);
        JSONObject object = new JSONObject();
        object.put("content",content);
        object.put("type",type);
        msg.setMsgBody(content);
        msg.setExtra(object.toJSONString());
        String json = HttpUtil.httpRequest(HttpUtil.SEND_NOTIFY_CMD_MSG_PATH, JSON.toJSONString(msg));
        return json;
    }

    public static String sendGroupMsg(long senderId,String senderName,boolean isPersist,String groupId,String groupName,String content,int type){
        ReqMessage msg = new ReqMessage();
        msg.setAppId(Global.APP_ID);
        msg.setGroupType(2);
        msg.setSenderId(senderId);
        msg.setSenderName(senderName);
        msg.setGroupId(groupId);
        msg.setMsgType(1);
        msg.setGroupName(groupName);
        msg.setIsPersist(isPersist);
        JSONObject object = new JSONObject();
        object.put("content",content);
        object.put("type",type);
        msg.setMsgBody(content);
        msg.setExtra(object.toJSONString());
        String json = HttpUtil.httpRequest(HttpUtil.SEND_NOTIFY_MSG_PATH, JSON.toJSONString(msg));
        return json;
    }
}

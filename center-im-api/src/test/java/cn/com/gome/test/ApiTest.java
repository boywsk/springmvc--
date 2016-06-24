package cn.com.gome.test;

import cn.com.gome.api.utils.StringUtils;
import cn.com.gome.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Random;

/**
 * Created by wangshikai on 2016/1/22.
 */
public class ApiTest {
    private static final String url = "http://10.125.3.51:8090/center-im-api/";  //  http://10.125.3.51:8080/im-api/

    private static int threadSize = 200;

    public static void main(String[] args) {
        //测试项:center-im-api服务 1.并发数量 2.相应时间
        for(int i = 0; i < threadSize; i++){
            new Thread(new Runnable() {
                public void run() {
                    long responseTime = 0;
                    for(int j = 0;j<10;j++){  //模拟5次   发送消息或者创建群，加入群操作，更新用户信息操作
                        try {
                            long time1 = System.currentTimeMillis();
                            sendMessage();
                            long time2 = System.currentTimeMillis();
                            if ((time2 - time1) > responseTime) {
                                responseTime = time2 - time1;
                            }
                            Thread.sleep(100);

                            String groupId = StringUtils.getUuid();
                            long userId = StringUtils.getUuid().hashCode() + new Random().nextInt(10000);
                            long time3 = System.currentTimeMillis();
                            addGroup(groupId);
                            long time4 = System.currentTimeMillis();
                            if ((time4 - time3) > responseTime) {
                                responseTime = time4 - time3;
                            }
                            Thread.sleep(100);

                            long time5 = System.currentTimeMillis();
                            joinGroup(groupId, userId);
                            long time6 = System.currentTimeMillis();
                            if ((time6 - time5) > responseTime) {
                                responseTime = time6 - time5;
                            }
                            Thread.sleep(100);

                            long time7 = System.currentTimeMillis();
                            updateUserInfo();
                            long time8 = System.currentTimeMillis();
                            if ((time8 - time7) > responseTime) {
                                responseTime = time8 - time7;
                            }
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("最大响应时间:" + responseTime);
                }
            }).start();

        }

    }

    //发送消息
    public static String sendMessage(){
        String jsonParms = "{\n" +
                "            \"requestId\": \"123456\",\n" +
                "            \"appServerName\": \"BS-Server1\",\n" +
                "            \"senderId\": 100000,\n" +
                "            \"senderName\": \"senderName\",\n" +
                "            \"groupType\": 1,\n" +
                "            \"receiveId\": 100001,\n" +
                "            \"content\": \"{\\\"msgData\\\":{\\\"msg\\\":\\\"内容\\\",\\\"type\\\":2},\\\"msgType\\\":{\\\"action\\\":\\\"cmd\\\",\\\"type\\\":1}}\"\n" +
                "            }";
        return HttpUtil.httpRequest(url + "message/notifyMessage.json", jsonParms, "POST");
    }

    //创建群组
    public static String addGroup(String groupId){
        String jsonParms = "{\n" +
                "            \"requestId\": \"123456\",\n" +
                "            \"appServerName\": \"BS-Server1\",\n" +
                "            \"uid\": 100000,\n" +
                "            \"nickName\": \"昵称\",\n" +
                "            \"groupId\": \"643542cea444450b9efed3ac75a64fca\",\n" +
                "            \"groupName\": \"群名称\",\n" +
                "            \"desc\": \"描述\",\n" +
                "            \"content\": \"内容\"\n" +
                "            }";
        JSONObject jsonObject = JSON.parseObject(jsonParms);
        jsonObject.put("groupId",groupId); //替换群组id
        jsonParms = jsonObject.toJSONString();
        return HttpUtil.httpRequest(url + "group/addGroup.json", jsonParms, "POST");
    }

    //加入群组
    public static String joinGroup(String groupId,long userId){
        String jsonParms = "{\n" +
                "            \"requestId\": \"123456\",\n" +
                "            \"appServerName\": \"BS-Server1\",\n" +
                "            \"groupId\": \"643542cea444450b9efed3ac75a64fca\",\n" +
                "            \"uid\": 1000006,\n" +
                "            \"nickName\": \"昵称\",\n" +
                "            \"content\": \"xxx加入群组\"\n" +
                "            }";
        JSONObject jsonObject = JSON.parseObject(jsonParms);
        jsonObject.put("groupId",groupId); //替换群组id
        jsonObject.put("uid",userId); //替换uid
        jsonParms = jsonObject.toJSONString();
        return HttpUtil.httpRequest(url + "group/joinGroup.json", jsonParms, "POST");
    }

    //更新用户信息
    public static String updateUserInfo(){
        String jsonParms = "{\n" +
                "            \"requestId\": \"123456\",\n" +
                "            \"appServerName\": \"BS-Server1\",\n" +
                "            \"opt\": 1,\n" +
                "            \"userInfoList\": [\n" +
                "            {\n" +
                "            \"uid\": 100000,\n" +
                "            \"nickName\": \"用户昵称\",\n" +
                "            \"deviceType\": 1,\n" +
                "            \"deviceToken\": \"\"\n" +
                "            }\n" +
                "            ]\n" +
                "            }";
        return HttpUtil.httpRequest(url + "user/userInfo.json", jsonParms, "POST");
    }
}

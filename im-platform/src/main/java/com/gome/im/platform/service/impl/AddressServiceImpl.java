package com.gome.im.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gome.im.platform.dao.UserInfoDao;
import com.gome.im.platform.global.Constant;
import com.gome.im.platform.global.Global;
import com.gome.im.platform.model.ResultModel;
import com.gome.im.platform.model.UserInfo;
import com.gome.im.platform.model.response.RspAddress;
import com.gome.im.platform.service.AddressService;
import com.gome.im.platform.utils.CuratorClient;
import com.gome.im.platform.utils.HttpUtil;
import com.gome.im.platform.utils.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wangshikai on 2015/12/21.
 */
@Service
public class AddressServiceImpl implements AddressService {
    private static Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
    private final static UserInfoDao userInfoDao = new UserInfoDao();
    private static volatile RspAddress SOURCES = null;
    private static long FIVE_MINUTES = 5 * 60 * 1000;

    @Override
    public ResultModel<Object> getAddress(long imUserId, String token, String appId) {
        ResultModel<Object> resultModel = checkUser(appId,imUserId,token);
        if (resultModel != null){
            return resultModel;
        }
//        try {
//            long time = System.currentTimeMillis();
//            RspAddress  sources = null;
//            if (SOURCES != null) {
//                sources = new RspAddress();
//                sources = SOURCES;
//                Collections.shuffle(sources.getImServerList());
//                Collections.shuffle(sources.getFileServerList());
//            }
//            if (sources != null && time - sources.getTime() < FIVE_MINUTES) {
//                log.info("getAddress success(5 minutes),rspAddress:{}", JSON.toJSONString(sources));
//                return new ResultModel<Object>(ResultModel.RESULT_OK, "服务器列表获取成功", sources);
//            }
//            RspAddress rspAddress = getRspAddress();
//            if (rspAddress != null) {
//                rspAddress.setTime(time);
//                SOURCES = rspAddress;
//            } else {
//                log.error("getAddress, gatekeeper服务返回服务器地址失败,检查zookeeper或gatekeeper...,返回缓存中的资源地址:{}", JSON.toJSONString(SOURCES));
//                if (SOURCES == null) {
//                    return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "服务器列表获取失败", SOURCES);
//                }
//                return new ResultModel<Object>(ResultModel.RESULT_OK, "服务器列表获取成功", sources);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("getAddress,error:{}", e.toString());
//        }
//        log.info("getAddress success,rspAddress:{}", JSON.toJSONString(SOURCES));

            //--------------TODO  这段代码实属无奈 “坑” 死配置-----------------------------------------
            long time = System.currentTimeMillis();
        if (SOURCES == null) {
            SOURCES = new RspAddress();
            SOURCES.setImServerList(Global.IM_SERVERS_LIST);
            SOURCES.setFileServerList(Global.FILE_SERVERS_LIST);
        }
        RspAddress sources = new RspAddress();
        sources = SOURCES;
        Collections.shuffle(sources.getImServerList());
        Collections.shuffle(sources.getFileServerList());
        sources.setTime(time);
        log.info("getAddress success,rspAddress:{}", JSON.toJSONString(sources));
        //----------------------------------------------------------------------------------------

        return new ResultModel<Object>(ResultModel.RESULT_OK, "服务器列表获取成功", SOURCES);
    }

    public static ResultModel<Object> checkUser(String appId, long imUserId, String token) {
        UserInfo info = userInfoDao.getUserInfoByImUserId(appId, imUserId);
        if (info == null) {
            log.error("用户不存在,appId:{},imUserId:{},token:{}", appId, imUserId, token);
            return new ResultModel<Object>(ResultModel.USER_ERROR, "用户未注册", new HashMap<>());
        }
        String userToken = info.getToken();
        if (!userToken.equals(token)) {
            log.error("用户token错误,appId:{},imUserId:{},token:{},正确的token={}", appId, imUserId, token, userToken);
            return new ResultModel<Object>(ResultModel.TOKEN_ERROR, "用户token错误", new HashMap<>());
        }
        return null;
    }

    public RspAddress getRspAddress() {
        RspAddress rsp = new RspAddress();
        try {
            List<String> gateKeeperIPList = new ArrayList<>();
            List<String> paths = CuratorClient.getZkGateKeeperPaths();
            if (paths.size() == 0) {
                log.error("getAddress,error: zookeeper中gatekeeper服务器地址为空,检查zookeeper服务器", paths.size());
            }
            for (String p : paths) {
                log.info("path:" + p);
                String[] str = p.split(":");
                String ip = IPUtils.longToIP(Long.parseLong(str[0]));
                log.info("ip:" + ip);
                gateKeeperIPList.add(ip + ":" + str[1]);
            }
            List<Integer> parm = new ArrayList<>();
            parm.add(Constant.SERVER_TYPE.GATEWAY.value);
            parm.add(Constant.SERVER_TYPE.FILE.value);
            int index = 0;
            int size = gateKeeperIPList.size();
            if (gateKeeperIPList.size() > 0) {
                Random random = new Random();
                index = random.nextInt(size);
            }
            String gatekeeperUrl = "http://" + gateKeeperIPList.get(index) + "/getServerRes";
            String json = HttpUtil.httpRequest(gatekeeperUrl, JSON.toJSONString(parm));
            log.info("json:" + json);
            if (json == null) {
                log.error("getAddress error,请求gatekeeper服务器返回结果json为:{}", json);
                return null;
            }
            List<String> imServerList = null;
            List<String> fileServerList = null;
            String code = JSON.parseObject(json).getString("code");
            if (code != null && code.equals("0")) {
                JSONObject object = JSON.parseObject(JSON.parseObject(json).getString("data"));
                String gateWayResource = object.getString(String.valueOf(Constant.SERVER_TYPE.GATEWAY.value));
                if (gateWayResource != null) {
                    imServerList = getServerList(gateWayResource);
                    rsp.setImServerList(imServerList);
                } else {
                    log.error("getAddress,IM接入层服务器地址列表为空,检查gatekeeper服务器!");
                    return null;
                }
                String fileResource = object.getString(String.valueOf(Constant.SERVER_TYPE.FILE.value));
                if (fileResource != null) {
                    fileServerList = getServerList(fileResource);
                    rsp.setFileServerList(fileServerList);
                } else {
                    log.error("getAddress,文件服务器地址列表为空,检查gatekeeper服务器!");
                    return null;
                }
            } else {
                log.error("getAddress,gatekeeper服务器返回数据错误,返回数据:{}", json);
                return null;
            }
        } catch (Exception e) {
            rsp = null;
            e.printStackTrace();
            log.error("getAddress,error:{}", e.toString());
        }
        return rsp;
    }

    public List<String> getServerList(String json) {
        List<String> list = new ArrayList<>();
        List<Map> gateServerList = JSON.parseObject(json, List.class);
        for (Map o : gateServerList) {
            String ip = o.get("serverIp").toString();
            String port = o.get("serverPort").toString();
            list.add(ip + ":" + port);
        }
        return list;
    }

}

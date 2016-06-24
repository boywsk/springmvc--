package com.gome.im.platform.controller;

import com.gome.im.platform.model.ResultModel;
import com.gome.im.platform.service.UserInfoService;
import com.gome.im.platform.utils.Md5Util;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by wangshikai on 2016/3/18.
 */
@Controller
@RequestMapping("token")
public class UserTokenController {
    private Logger log = LoggerFactory.getLogger(UserTokenController.class);
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取用户token
     * @param appId
     * @param imUserId
     * @param timestamp
     * @param signature
     */
    @RequestMapping(value = "getToken", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel<Object> getToken(@RequestParam String appId,@RequestParam long imUserId,
                                            @RequestParam long timestamp,@RequestParam String signature) {
        log.info("getToken appId={}, imUserId={},timestamp={},signature={}",appId,imUserId,timestamp,signature);
        if(StringUtils.isNotEmpty(appId) && StringUtils.isNotEmpty(signature) && imUserId > 0) {
            String secret = null;
            try {
                secret = Md5Util.md5Encode(appId+imUserId+timestamp);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Md5Util.md5Encode() error!");
            }
            if(secret == null || !secret.equals(signature)){
                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "验签出错，获取失败", new HashMap<>());
            }
            return userInfoService.getUserToken(imUserId,appId);
        } else{
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误", new HashMap<>());
        }
    }

    /**
     * 用户退出,退出时清理用户的apnsToken
     * @param appId
     * @param imUserId
     * @param token
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel<Object> logout(@RequestParam String appId,@RequestParam long imUserId,@RequestParam String token) {
        log.info("clearApnsToken parms: appId={},imUserId={},token={},parm={}", appId,imUserId,token);
        return userInfoService.clearApnsToken(appId,imUserId,token);
    }
}

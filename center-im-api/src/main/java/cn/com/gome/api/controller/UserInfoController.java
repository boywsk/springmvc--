package cn.com.gome.api.controller;

import cn.com.gome.api.global.Global;
import cn.com.gome.api.model.ResultModel;
import cn.com.gome.api.model.request.ReqUserInfo;
import cn.com.gome.api.service.UserInfoService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by wangshikai on 2016/1/12.
 */
@Controller
@RequestMapping("user")
public class UserInfoController {
    Logger log = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 注册用户信息
     * @param reqUserInfo
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> register(@RequestBody ReqUserInfo reqUserInfo,@RequestParam String appId) {
        log.info("register , parms:"+ JSON.toJSONString(reqUserInfo));
        if(reqUserInfo != null){
//            int opt = reqUserInfo.getOpt();
//            // opt = 1 : 注册用户操作   opt = 2 :更新用户token
//            if(opt == 1){
                return userInfoService.register(reqUserInfo,appId, Global.TOKEN_EXPIRES_TIME);
//            }else if(opt == 2){
//                //对BS隐藏更新用户token的接口
//                return userInfoService.updateUserInfo(reqUserInfo,appId);
//            }else{
//                return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误",new HashMap<>());
//            }
        }else{
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误",new HashMap<>());
        }
    }

    /**
     * 注册客服系统特殊用户信息
     * @param reqUserInfo
     */
    @RequestMapping(value = "registerCustomer", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> registerCustomer(@RequestBody ReqUserInfo reqUserInfo,@RequestParam String appId) {
        log.info("registerCustomer , parms:"+ JSON.toJSONString(reqUserInfo));
        if(reqUserInfo != null){
            long expiresTime = 4000000000000L; //不过期(取极大值过期时间)
            return userInfoService.register(reqUserInfo,appId, expiresTime);
        }else{
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误",new HashMap<>());
        }
    }

    /**
     * 检测用户合法性
     * @param appId
     * @param imUserId
     */
    @RequestMapping(value = "checkUser", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel<Object> checkUser(@RequestParam String appId,@RequestParam long imUserId) {
        log.info("checkUser , appId:{},imUserId:{}",appId,imUserId);
        if(StringUtils.isNotEmpty(appId) && imUserId > 0){
            boolean isRegister = userInfoService.checkImUserId(appId,imUserId);
            int code = 0;
            HashMap<String,Object> map = new HashMap<>();
            map.put("imUserId",imUserId);
            if(isRegister){
                code = 1; //注册过的合法用户
            }
            map.put("registerCode",code);
            return new ResultModel<Object>(ResultModel.RESULT_OK, "操作成功",map);
        }else{
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "参数错误",new HashMap<>());
        }
    }
}

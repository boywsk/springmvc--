package com.gome.im.api.controller;

import com.alibaba.fastjson.JSON;
import com.gome.im.api.db.model.User;
import com.gome.im.api.model.ResultModel;
import com.gome.im.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by wangshikai on 2016/2/29.
 */
@Controller
@RequestMapping("user")
public class UserController {
    Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * 注册用户
     * @param user
     */
    @RequestMapping(value = "registerUser", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> registerUser(@RequestBody User user) {
        log.info("registerUser , parms:"+ JSON.toJSONString(user));
        if(user != null) {
            return userService.saveUser(user);
        } else{
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "操作失败", new HashMap<>());
        }
    }

    /**
     * 用户更新token
     * @param user
     */
    @RequestMapping(value = "updateToken", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> updateToken(@RequestBody User user) {
        log.info("updateToken , parms:"+ JSON.toJSONString(user));
        if(user != null) {
            return userService.updateUserInfo(user);
        } else{
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "操作失败", new HashMap<>());
        }
    }

    /**
     * 获取用户信息
     * @param user
     */
    @RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> getUserInfo(@RequestBody User user) {
        log.info("getUserInfo , parms:"+ JSON.toJSONString(user));
        if(user != null) {
            return userService.getUserInfo(user);
        } else{
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "操作失败", new HashMap<>());
        }
    }

    /**
     * 用户登录，得到token
     * @param user
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> login(@RequestBody User user,HttpServletRequest request) {
        log.info("login , parms:"+ JSON.toJSONString(user));
        if(user != null) {
            return userService.login(user,request);
        } else{
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "操作失败", new HashMap<>());
        }
    }

    /**
     * 查询用户接口
     * @param user
     */
    @RequestMapping(value = "findUser", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel<Object> findUser(@RequestBody User user,HttpServletRequest request) {
        log.info("findUser , parms:"+ JSON.toJSONString(user));
        if(user != null) {
            return userService.findUser(user);
        } else{
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "操作失败", new HashMap<>());
        }
    }
}

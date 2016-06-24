package com.gome.im.platform.controller;

import com.gome.im.platform.model.ResultModel;
import com.gome.im.platform.service.AddressService;
import com.gome.im.platform.utils.CuratorClient;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
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
@RequestMapping("address")
public class IMServerAddressController {
    private Logger log = LoggerFactory.getLogger(IMServerAddressController.class);
    @Autowired
    private AddressService addressService;

    /**
     * 初始化zookeeper
     */
//    private static CuratorFramework zkClient = CuratorClient.init();

    /**
     * 获取IM服务器地址列表
     * @param appId
     * @param imUserId
     * @param token
     */
    @RequestMapping(value = "getIMAddress", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel<Object> getIMAddress(@RequestParam String appId,
                                            @RequestParam long imUserId,@RequestParam String token) {
        log.info("getIMAddress appId={}, imUserId={},token={}",appId,imUserId,token);
        if(StringUtils.isNotEmpty(appId) && imUserId > 0) {
            return addressService.getAddress(imUserId,token,appId);
        } else{
            return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "操作失败,用户id不存在", new HashMap<>());
        }
    }
}

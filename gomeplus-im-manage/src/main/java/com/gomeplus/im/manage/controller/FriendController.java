package com.gomeplus.im.manage.controller;

import com.alibaba.fastjson.JSON;
import com.gomeplus.im.manage.model.Friend;
import com.gomeplus.im.manage.model.User;
import com.gomeplus.im.manage.pageSupport.PageInfo;
import com.gomeplus.im.manage.pageSupport.PageInfoFactory;
import com.gomeplus.im.manage.service.FriendService;
import com.gomeplus.im.manage.service.UserService;
import com.gomeplus.im.manage.utils.ParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshikai on 2015/12/29.
 */
@Controller
@RequestMapping("/friend")
public class FriendController {
    private static Logger LOG = LoggerFactory.getLogger(FriendController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @RequestMapping(value="friend", method = RequestMethod.GET)
    public String friend() {
        return "friend/friend";
    }

    @RequestMapping(value="getFriend", method = RequestMethod.GET)
    public void getFriend(HttpServletRequest request, HttpServletResponse response,Model model) {
        String userName = request.getParameter("userName");
        int pageNo =  ParamUtil.getIntParams(request, "page", 1);
        int pageSize =  ParamUtil.getIntParams(request, "pagesize", 10);
        PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);
        User user = null;
        if(userName != null){
            Map<String,Object> parms = new HashMap<>();
            parms.put("userNameHashId",userName.hashCode());
            parms.put("userName",userName);
            user = userService.getUserByName(parms);
        }
        List<Friend> list = new ArrayList<>();
        if(user != null){
            list = friendService.getFriendsByUserId(user.getId(),pageInfo);
        }
        model.addAttribute("userName",userName);
        Map<String, Object> map = new HashMap<>();
        map.put("Rows", list);
        map.put("Total",pageInfo.getTotalResult());
        responseJson(response, JSON.toJSONString(map));
    }

    public static void responseJson(HttpServletResponse response, String json){
    	System.out.println(json);
        PrintWriter out = null;
        try {
            response.setHeader("Content-type", "text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setContentLength(json.getBytes("UTF-8").length);
            out = response.getWriter();
            out.write(json);
            out.flush();
        } catch (Exception e) {
            LOG.error("responseJson error{}",e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}

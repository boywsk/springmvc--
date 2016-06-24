package cn.com.gome.manage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.gome.manage.mongodb.model.Friend;
import cn.com.gome.manage.mongodb.service.FriendService;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pageSupport.PageInfoFactory;
import cn.com.gome.manage.utils.HttpUtil;
import cn.com.gome.manage.utils.ParamUtil;

/**
 * Created by wangshikai on 2015/12/29.
 */
@Controller
@RequestMapping("/friend")
public class FriendController {

    private FriendService friendService = new FriendService();

    @RequestMapping(value="friend", method = RequestMethod.GET)
    public String friend() {
        return "friend/friend";
    }

    @RequestMapping(value="friendList", method = RequestMethod.GET)
    public void friendList(HttpServletRequest request, HttpServletResponse response) {
        long userId = ParamUtil.getLongParams(request, "userId", 0);
        int pageNo =  ParamUtil.getIntParams(request, "page", 1);
        int pageSize =  ParamUtil.getIntParams(request, "pagesize", 10);
        PageInfo pageInfo = PageInfoFactory.getPageInfo(pageSize, pageNo);

        List<Friend> list = friendService.getFriendList(userId,0,pageInfo);
        //格式化群组创建时间
        for(Friend friend : list){
            long createTime = friend.getCreateTime();
            long updateTime = friend.getUpdateTime();
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String createDate = formatter.format(new Date(createTime));
                String updateDate = formatter.format(new Date(updateTime));
                friend.setFormatCreateTime(createDate);
                friend.setFormatUpdateTime(updateDate);
            }catch (Exception e){
                friend.setFormatCreateTime(String.valueOf(createTime));
                friend.setFormatUpdateTime(String.valueOf(updateTime));
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Rows", list);
        map.put("Total",pageInfo.getTotalResult()); //数据总数
        HttpUtil.writeResult(response, map);
    }

    @RequestMapping(value="getRelation", method = RequestMethod.GET)
    public void getRelation(HttpServletRequest request, HttpServletResponse response) {
        long userId = ParamUtil.getLongParams(request, "userId", 0);
        long friendId = ParamUtil.getLongParams(request, "friendId", 0);

        List<Friend> list = friendService.getRelation(userId,friendId);
        //格式化群组创建时间
        for(Friend friend : list){
            long createTime = friend.getCreateTime();
            long updateTime = friend.getUpdateTime();
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String createDate = formatter.format(new Date(createTime));
                String updateDate = formatter.format(new Date(updateTime));
                friend.setFormatCreateTime(createDate);
                friend.setFormatUpdateTime(updateDate);
            }catch (Exception e){
                friend.setFormatCreateTime(String.valueOf(createTime));
                friend.setFormatUpdateTime(String.valueOf(updateTime));
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Rows", list);
        HttpUtil.writeResult(response, map);
    }
}

package com.gome.im.platform.interceptor;

import com.gome.im.platform.utils.LRUCache;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangshikai on 2015/12/28.
 */
public class TokenInterceptor implements HandlerInterceptor {
    private Logger log = LoggerFactory.getLogger(TokenInterceptor.class);
    //key : appId_imUserId
    private static LRUCache<String,Long> USER_REQ_MAP = new LRUCache<>(200000);

    private final static long VALID_TIME = 0*60*1000;//1分钟有效时间，用户每1分钟可以请求一次token或地址信息
    private static int ERROR_CODE = 401;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String appId = request.getParameter("appId");
        String imUserIdParm = request.getParameter("imUserId");
        log.info("appId={},imUserId={}",appId,imUserIdParm);
        long imUserId = 0;
        try {
            imUserId = Long.parseLong(imUserIdParm);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            log.error("imUserId error,imUserId={}", imUserIdParm);
        }
        if(!StringUtils.isNotEmpty(appId) || imUserId <= 0){
            response.setStatus(ERROR_CODE);
            return false;
        }
        String key = appId+"_"+imUserId;
        long nowTime = System.currentTimeMillis();
        Long userReqTimeObj = USER_REQ_MAP.get(key);
        if(userReqTimeObj != null &&  (nowTime - userReqTimeObj.longValue() < VALID_TIME)){
            response.setStatus(ERROR_CODE);
            return false;
        }
        USER_REQ_MAP.put(key, nowTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

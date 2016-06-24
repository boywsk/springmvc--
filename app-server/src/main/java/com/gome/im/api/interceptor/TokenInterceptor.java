package com.gome.im.api.interceptor;

import com.gome.im.api.db.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by wangshikai on 2015/12/28.
 */
public class TokenInterceptor implements HandlerInterceptor {
    private Logger log = LoggerFactory.getLogger(TokenInterceptor.class);

    private final static int LOGIN_ERROR_CODE = 500;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
//        //验证行为
//        if(user == null){
//            response.setStatus(LOGIN_ERROR_CODE);
//            return false;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}

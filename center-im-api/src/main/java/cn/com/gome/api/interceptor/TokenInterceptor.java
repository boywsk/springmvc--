package cn.com.gome.api.interceptor;

import cn.com.gome.api.dao.AppDao;
import cn.com.gome.api.model.App;
import cn.com.gome.api.utils.Md5Util;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangshikai on 2015/12/28.
 */
public class TokenInterceptor implements HandlerInterceptor {
    private Logger log = LoggerFactory.getLogger(TokenInterceptor.class);
    private static ConcurrentHashMap<String,App> APP_MAP = new ConcurrentHashMap<>();
    private static AppDao appDao = new AppDao();
    private static int ERROR_CODE = 401;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String appId = request.getParameter("appId");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        log.info("appId={},signature={}",appId,signature);
        if(StringUtils.isNotEmpty(appId) && StringUtils.isNotEmpty(signature)){
            App app = APP_MAP.get(appId);
            if(app == null){
                app = appDao.getApp(appId);
                if(app == null){
                    response.setStatus(ERROR_CODE);
                    log.error("appId:{},不存在!",appId);
                    return false;
                }else{
                    APP_MAP.put(appId,app);
                }
            }
            app = APP_MAP.get(appId);
            String md5Str = Md5Util.md5Encode(appId+"|"+timestamp+"|"+app.getAppKey());
            if(md5Str.equals(signature)){
                return true;
            }else{
                response.setStatus(ERROR_CODE);
                log.error("签名错误,正确的appKey:{}和md5Signature:{};请求参数:appId:{},timestamp:{},signature:{}",app.getAppKey(),md5Str,appId,timestamp,signature);
                return false;
            }
        }else{
            response.setStatus(ERROR_CODE);
            log.error("appId或signature参数为空!");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

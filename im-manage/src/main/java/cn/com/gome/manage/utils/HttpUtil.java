package cn.com.gome.manage.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangshikai on 2015/12/22.
 */
public class HttpUtil {
    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static  HttpClient httpClient = new HttpClient();

    public static String httpRequest(String url, String parms, String requestType) {
        HttpMethod method = null;
        String json = null;
        try {
            if (requestType.equalsIgnoreCase("get")) {
                method = getMethod(url, parms);
            } else if(requestType.equalsIgnoreCase("post")){
                method = postMethod(url, parms);
            }
            httpClient.executeMethod(method);
            String response = method.getResponseBodyAsString();
            json = response;
        } catch (IOException e) {
            log.error("error:"+e);
        }
        return json;
    }

    private static HttpMethod getMethod(String url,String param) throws IOException {
        GetMethod get = new GetMethod(url+"?"+param);
        get.releaseConnection();
        return get;
    }

    private static HttpMethod postMethod(String url,String parms) throws IOException{
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Connection", "close");
        post.setRequestHeader("Content-Type","application/json;charset=utf-8");//Content-Type","application/x-www-form-urlencoded;charset=gbk
        post.setRequestBody(parms);
        post.releaseConnection();
        return post;
    }
    
    /**
     * 写回请求数据
     * 
     * @param data
     *            list或map对象
     */
    public static void writeResult(HttpServletResponse response, Object dataObj) {
        if (dataObj == null) {
            return;
        }
        String str = JSON.toJSONString(dataObj);
        writeResult(response, str);
    }

    /**
     * 写回请求数据
     * 
     * @param data
     *            字符串内容
     */
    public static void writeResult(HttpServletResponse response, String dataStr) {
        if (dataStr == null) {
            return;
        }
        PrintWriter out=null;
        
		try {
			response.setHeader("Content-type", "text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.setContentLength(dataStr.getBytes("UTF-8").length);
			out = response.getWriter();
			out.write(dataStr);
			out.flush();
		} catch (Exception e) {
			log.error("写回数据出现异常： ",e);
		} finally {
			if (out != null) {
				out.close();
			}
		}		
        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().print(dataStr);
        } catch (IOException e) {
            log.error("发生异常！", e);
        }
    }

    
}

package com.gome.im.platform.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by wangshikai on 2016/3/31.
 */
public class HttpUtil {

    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static String httpRequest(String url, String parms) {
        HttpClient httpClient = new HttpClient();
        HttpMethod method = null;
        String json = null;
        try {
            method = postMethod(url, parms);
            httpClient.setTimeout(5000);
            httpClient.executeMethod(method);
            json = method.getResponseBodyAsString();
        } catch (IOException e) {
            log.error("error:"+e);
        }
        return json;
    }

    private static HttpMethod postMethod(String url,String parms) throws IOException{
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Connection", "close");
        post.setRequestHeader("Content-Type","application/json;charset=utf-8");//Content-Type","application/x-www-form-urlencoded;charset=gbk
        post.setRequestBody(parms);
        post.releaseConnection();
        return post;
    }
}

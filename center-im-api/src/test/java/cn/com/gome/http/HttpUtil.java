package cn.com.gome.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by wangshikai on 2015/12/22.
 */
public class HttpUtil {
    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static  HttpClient httpClient = new HttpClient();

    public static String httpRequest(String url, String parms, String requestType) {
        HttpClient httpClient = new HttpClient();
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
//            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
//            StringBuffer stringBuffer = new StringBuffer();
//            String str = "";
//            while((str = reader.readLine())!=null){
//                stringBuffer.append(str);
//            }
//            String ts = stringBuffer.toString();

            json = response;
            //String response = new String(method.getResponseBodyAsString().getBytes("UTF-8"));
            //System.out.println(response);
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
        post.setRequestHeader("Content-Type","application/json;charset=utf-8");//Content-Type","application/x-www-form-urlencoded;charset=gbk
        post.setRequestBody(parms);
        post.releaseConnection();
        return post;
    }
}

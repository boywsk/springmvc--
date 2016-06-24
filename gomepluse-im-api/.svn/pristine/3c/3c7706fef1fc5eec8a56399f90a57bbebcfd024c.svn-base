package com.gomeplus.im.api.test;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.alibaba.fastjson.JSON;
import com.gomeplus.im.api.utils.Md5Util;

//import com.thunisoft.dzfy.utils.TestUtils;
//import com.thunisoft.susong51.util.AppSecretUtil;

/**
 * 测试接口 ApiTester
 * 
 * @author dingjsh
 * @time 2015-10-29下午04:24:15
 */
public class ApiTester {

    private static final Log LOG = LogFactory.getLog(ApiTester.class);

    private static final int DEFAULT_SO_TIMEOUT = 8000;

    private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;

    // private static HttpClient cachedHttpClient = null;

    private static ThreadLocal<HttpClient> httpClientPerThread = new ThreadLocal<HttpClient>();
	public static final String APP_ID="100";
	public static final String APP_ID_URL="?appId="+APP_ID;
    /**
     * 请改成你要进行测试的地址
     */
//    protected static final String DZFY_WW_URL = "http://172.16.30.100:8080/dzfy-ww";
    protected static final String GOMEPLUS_IM_API = "http://10.69.16.23:8085/gomeplus-im-api";
    
    // protected static final String DZFY_WW_URL =
    // "http://172.18.54.33:9080/dzfy-ww";
    
    /**得到url参数
     * @param token
     * @param userId
     * @return
     */
    public String getURLParams(String token,long userId){
		long timestamp=System.currentTimeMillis();
		String appId=APP_ID;
		String signature=appId+"|"+token+"|"+userId+"|"+timestamp;
		try {
			signature=Md5Util.md5Encode(signature);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String urlParams=APP_ID_URL+"&userId="+userId+"&signature="+signature+"&timestamp="+timestamp;
		return urlParams;
	}

    public static String get(String uri) {
        return get(uri, null);
    }

    public static String get(String uri, Map<String, Object> params) {
        return sendHttpRequest("get", uri, params);
    }

    public static String post(String uri) {
        return post(uri, null);
    }

    public static String post(String uri, Map<String, Object> params) {
        return sendHttpRequest("post", uri, params);
    }

    private static String sendHttpRequest(String httpMethod, String uri,
            Map<String, Object> params) {
        long start = System.currentTimeMillis();
        String result = StringUtils.EMPTY;
        try {
            uri = getFullUri(uri);
            HttpUriRequest method = null;
            if ("get".equals(httpMethod)) {
                StringBuilder urlBuilder = new StringBuilder(uri);
                if (null != params && !params.isEmpty()) {
                    if (!uri.contains("?")) {
                        urlBuilder.append("?");
                    }
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        String valueStr = null == value ? "" : value.toString();
                        if (!urlBuilder.toString().endsWith("?")) {
                            urlBuilder.append("&");
                        }
                        urlBuilder.append(key).append("=")
                                .append(URLEncoder.encode(valueStr, "utf-8"));
                    }
                }
                HttpGet httpGet = new HttpGet(urlBuilder.toString());
                method = httpGet;
            } else {
                HttpPost httpPost = new HttpPost(uri);
                if (null != params && !params.isEmpty()) {
                    httpPost.setEntity(new StringEntity(
                            JSON.toJSONString(params),
                            ContentType.APPLICATION_JSON));
                }else {
                	httpPost.setEntity(new StringEntity(
                			JSON.toJSONString(params),
                			ContentType.APPLICATION_JSON));
					
				}
                method = httpPost;
            }
            HttpClient client = getHttpClient(null, null, false);
            HttpResponse httpResponse = client.execute(method);
            // 此处如果是做文件下载的话httpResponse.getEntity().getContent()就是输入流了，写到输出流就可以了
            result = IOUtils.toString(httpResponse.getEntity().getContent());
            long duraction = System.currentTimeMillis() - start;
            LOG.debug("调用http接口【" + uri + "】结束，耗时【" + duraction + "ms】");
            LOG.debug("返回值【" + result + "】");
        } catch (IOException e) {
            LOG.error("调用http接口【" + uri + "】出错", e);
        }
        return result;
    }

    private static String getFullUri(String uri) {
        if (StringUtils.isEmpty(uri)) {
            throw new IllegalArgumentException("uri不能为空");
        }
        if (uri.startsWith("http://") || uri.startsWith("https://")) {
            return uri;
        }
        if (GOMEPLUS_IM_API.endsWith("/") && uri.startsWith("/")) {
            return GOMEPLUS_IM_API.substring(0, GOMEPLUS_IM_API.length() - 1) + uri;
        } else if (!GOMEPLUS_IM_API.endsWith("/") && !uri.startsWith("/")) {
            return GOMEPLUS_IM_API + "/" + uri;
        } else {
            return GOMEPLUS_IM_API + uri;
        }
    }

    protected static HttpClient getHttpClient(Integer soTimeout,
            Integer connectionTimeout, boolean createNew) {
        HttpClient cacheHttpClient = httpClientPerThread.get();
        if (null == cacheHttpClient || createNew) {
            if (null == soTimeout) {
                soTimeout = DEFAULT_SO_TIMEOUT;
            }
            if (null == connectionTimeout) {
                connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
            }
            DefaultHttpClient client = new DefaultHttpClient();
            client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                    CookiePolicy.BEST_MATCH);
            HttpParams my_httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(my_httpParams,
                    connectionTimeout);
            HttpConnectionParams.setSoTimeout(my_httpParams, soTimeout);
            cacheHttpClient = client;
            httpClientPerThread.set(cacheHttpClient);
        }
        return cacheHttpClient;
    }

//    public static String encrpt(String value) {
//        return AppSecretUtil.encodeAppString(value);
//    }
//
//    public static String decrypt(String value) {
//        return AppSecretUtil.decodeAppString(value);
//    }

    public static String getResultInfo(String api, String result) {
        return "调用【" + api + "】，结果为【" + result + "】";
    }

    public static void outputResultInfo(String api, String result) {
        System.out.println(getResultInfo(api, result));
    }

    public static List<Header> getHeaders(List<Header> headers,
            String headerName) {
        List<Header> matchedHeaders = new ArrayList<Header>();
        if (null != headers) {
            for (Header header : headers) {
                if (header.getName().equalsIgnoreCase(headerName)) {
                    matchedHeaders.add(header);
                }
            }
        }
        return matchedHeaders;
    }

    /**
     * 多线程测试 DJSTODO dingjsh commented in 20160128 下次再加每个线程的调用次数
     * 
     * @param task
     *            任务
     * @param taskName
     *            任务名称
     * @param threadCount
     *            任务数
     * @author dingjsh
     * @time 2016-1-27下午01:13:42
     */
    public static void multiThreadTest(Runnable task, String taskName,
            int threadCount) {
        //TestUtils.multiThreadTest(task, taskName, threadCount);
    }

}

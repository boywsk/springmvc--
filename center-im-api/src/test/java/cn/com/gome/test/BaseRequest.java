package cn.com.gome.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 所有RequestBody中model的基类
 */
public class BaseRequest {
	private static Logger log = LoggerFactory.getLogger(BaseRequest.class);

	private String requestId;//请求的requestId

	private String appServerName; //请求的应用服务器名称

	public String getAppServerName() {
		return appServerName;
	}

	public void setAppServerName(String appServerName) {
		this.appServerName = appServerName;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}


}

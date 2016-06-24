package com.gome.im.api.model.request;

/**
 * 所有RequestBody中model的基类
 */
public class BaseRequest {

	/**
	 * token值，所有请求需携带
	 */
	private String appId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
}

package com.gome.im.api.model.request;

import java.io.Serializable;

/**
 * 二维码业务 Request model
 */
public class ReqQRcode extends BaseRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int type;
	private String id;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

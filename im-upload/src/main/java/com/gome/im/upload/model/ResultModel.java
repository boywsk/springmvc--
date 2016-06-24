package com.gome.im.upload.model;

import java.io.Serializable;

public class ResultModel<T> implements Serializable {

	/**
	 * 操作成功的响应码
	 */
	public static int RESULT_OK = 0;

	/**
	 * 操作失败的响应码
	 */
	public static int RESULT_FAILURE = 1;

	/**
	 * 操作异常或者错误的响应码
	 */
	// public static int RESULT_ERROR = 2;

	private static final long serialVersionUID = 13489783434L;

	private String message;

	/**
	 * 返回码
	 */
	private int code;

	/**
	 * 返回数据(请求错误时，返回错误信息)
	 */
	private Object data;

	public ResultModel() {
	}

	public ResultModel(int code, String message, Object data) {
		this.message = message;
		this.code = code;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}

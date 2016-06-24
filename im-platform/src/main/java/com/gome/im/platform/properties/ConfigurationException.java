package com.gome.im.platform.properties;

/**
 * 操作配置文件时异常
 */
public class ConfigurationException extends Exception {
	private static final long serialVersionUID = -3145511931705048960L;

	public ConfigurationException() {
	}

	public ConfigurationException(String msg) {
		super(msg);
	}
}
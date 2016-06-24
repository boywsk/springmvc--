package com.gome.im.platform.global;

import com.gome.im.platform.properties.ConfigurationException;
import com.gome.im.platform.properties.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放文件配置信息或者全局变量配置信息
 */
public class Global {
	static Logger log = LoggerFactory.getLogger(Global.class);

	public static long TOKEN_EXPIRES_TIME = 7*24*3600*1000; //7天

	public static String MSG_BLOCKED_REDIS_SUFFIX = "_msgBlocked";

	// 消息队列host
	public static String MQ_HOST;
	// 消息队列port
	public static int MQ_PORT;
	// 消息队列virtualHost
	public static String MQ_VIRTUALHOST;
	// 消息队列账号
	public static String MQ_USERNAME;
	// 消息队列密码
	public static String MQ_PASSWORD;
	// 消息队列名称
	public static String MQ_QUEUENAME;
	//mongondb库名
	public static String MONGODB_DBNAME="db_im_sdk";

	//zookeeper
	public static String ZK_IP_PORT;
	public static String ZK_PATH;

	// im server url
	public static String IM_SERVERS;

	public static List<String> IM_SERVERS_LIST = new ArrayList<>();

	// FILE server url
	public static String FILE_SERVERS;

	public static List<String> FILE_SERVERS_LIST = new ArrayList<>();

	static {
		Properties properties = Properties.getInstance();
		MQ_HOST = properties.getValue("mq.host");
		MQ_PORT = properties.getIntValue("mq.port");
		MQ_VIRTUALHOST = properties.getValue("mq.virtualHost");
		MQ_USERNAME = properties.getValue("mq.username");
		MQ_PASSWORD = properties.getValue("mq.password");
		MQ_QUEUENAME = properties.getValue("mq.queueName");
		MONGODB_DBNAME = properties.getValue("mongodb.dbName");

		ZK_IP_PORT = properties.getValue("zookeeperAddress");
		ZK_PATH = properties.getValue("zookeeperPath");

		IM_SERVERS = properties.getValue("imServerUrl");
		String[]  imUrls = IM_SERVERS.split(";");
		for(String imUrl : imUrls){
			IM_SERVERS_LIST.add(imUrl);
		}
		FILE_SERVERS = properties.getValue("fileServerUrl");
		String[]  fileUrls = FILE_SERVERS.split(";");
		for(String fileUrl : fileUrls){
			FILE_SERVERS_LIST.add(fileUrl);
		}
	}

	public static void main(String[] args) throws ConfigurationException {
		log.info("MQ_HOST:[{}]", Global.MQ_HOST);
	}
}

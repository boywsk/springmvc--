package cn.com.gome.api.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.gome.api.properties.ConfigurationException;
import cn.com.gome.api.properties.Properties;

/**
 * 存放文件配置信息或者全局变量配置信息
 */
public class Global {
	static Logger log = LoggerFactory.getLogger(Global.class);

	public static long TOKEN_EXPIRES_TIME = 7*24*3600*1000; //7天

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
	// MQ消息交换器名称
	public static String MQ_EXCHANGE_NAME;
	// 消息队列名称
	public static String MQ_QUEUENAME;
	//mongondb库名
	public static String MONGODB_DBNAME="db_im_sdk";

	//二维码配置
	public static int QRCODE_HEIGHT = 200; //默认
	public static int QRCODE_WIDTH = 200; //默认
	public static String QRCODE_URL_1 = "";
	public static String QRCODE_URL_2 = "";

	// 聊天消息分库模值
	public static int MSG_DB_MODULO;
	// 聊天消息分表模值
	public static int MSG_TABLE_MODULO;

	public static int REDIS_INDEX;

	static {
		Properties properties = Properties.getInstance();
		MQ_HOST = properties.getValue("mq.host");
		MQ_PORT = properties.getIntValue("mq.port");
		MQ_VIRTUALHOST = properties.getValue("mq.virtualHost");
		MQ_USERNAME = properties.getValue("mq.username");
		MQ_PASSWORD = properties.getValue("mq.password");
		MQ_EXCHANGE_NAME = properties.getValue("mq.exchangeName");
		MQ_QUEUENAME = properties.getValue("mq.queueName");
		MONGODB_DBNAME = properties.getValue("mongodb.dbName");

		//二维码配置
		QRCODE_HEIGHT = properties.getIntValue("qrcode_image_height");
		QRCODE_WIDTH = properties.getIntValue("qrcode_image_weight");
		QRCODE_URL_1 = properties.getValue("qrcode_url1");
		QRCODE_URL_2 = properties.getValue("qrcode_url2");

		MSG_DB_MODULO = properties.getIntValue("msg.db.modulo");
		MSG_TABLE_MODULO = properties.getIntValue("msg.table.modulo");

		REDIS_INDEX = properties.getIntValue("redis_index");
	}

	public static void main(String[] args) throws ConfigurationException {
		log.info("MQ_HOST:[{}]", Global.MQ_HOST);
	}
}

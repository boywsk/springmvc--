package cn.com.gome.api.utils;

import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import cn.com.gome.api.global.Global;

import java.io.IOException;

public class MQSender {
	static Logger log = LoggerFactory.getLogger(MQSender.class);
	final static String QUEUE_NAME = Global.MQ_QUEUENAME;
	final static String QUEUE_EXCHANGE_NAME = Global.MQ_EXCHANGE_NAME;
	private static Channel channel = null;
	private static Connection connection = null;
	private static MQSender instance = null;
	
	private MQSender() {
		
	}
	
	public static MQSender getInstance() {
		if(instance == null) {
			instance = new MQSender();
		}
		return instance;
	}

	static {
		reInitChannel();
	}
	
	/**
	 * 发送消息 (这里可能需要同步处理，否则异步关闭连接时可能会导致其他线程操作出现问题)
	 * @param msg
	 */
	public synchronized void sendMsg(String msg) {
		try {
			if(channel == null || connection == null) {
				close();
				reInitChannel();
			}
			channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes("UTF-8"));
			log.info("MQ send Msg:" + msg);
		} catch (Exception e) {
			close();
			try {
				//尝试重发
				reInitChannel();
				channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes("UTF-8"));
				log.info("尝试重发第1次，成功!");
			} catch (IOException e1) {
				close();
				try {
					//尝试重发
					reInitChannel();
					channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes("UTF-8"));
					log.info("尝试重发第2次，成功!");
				} catch (IOException e2) {
					log.error("MQ send Msg 重发第2次error:" + e2 + "\tsendMsg:" + msg);
				}
				log.error("MQ send Msg 重发第1次error:" + e1 + "\tsendMsg:" + msg);
			}
			log.error("MQ send Msg error:" + e + "\tsendMsg:" + msg);
		}
	}

	/**
	 * 关闭
	 */
	public static void close() {
		try {
			if(channel != null) {
				channel.close();
			}
			if(connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			log.error("close:", e);
		} finally {
			channel = null;
			connection = null;
		}
	}
	
	private static void reInitChannel() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(Global.MQ_HOST);
			factory.setPort(Global.MQ_PORT);
			factory.setUsername(Global.MQ_USERNAME);
			factory.setPassword(Global.MQ_PASSWORD);
			factory.setVirtualHost(Global.MQ_VIRTUALHOST);
			factory.setAutomaticRecoveryEnabled(true);
			connection = factory.newConnection();
			factory.setConnectionTimeout(2);  //2秒超时
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		} catch (Exception e) {
			log.error("init Channel:", e);
		}
	}
}

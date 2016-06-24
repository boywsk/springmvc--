package com.gome.im.platform.utils;

import com.gome.im.platform.global.Global;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQSender {
	static Logger log = LoggerFactory.getLogger(MQSender.class);
	final static String QUEUE_NAME = Global.MQ_QUEUENAME;
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
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(Global.MQ_HOST);
			factory.setPort(Global.MQ_PORT);
			factory.setUsername(Global.MQ_USERNAME);
			factory.setPassword(Global.MQ_PASSWORD);
			factory.setVirtualHost(Global.MQ_VIRTUALHOST);
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		} catch (Exception e) {
			log.error("init Channel:", e);
		}
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
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes("UTF-8"));
			log.info("MQ send Msg:" + msg);
		} catch (Exception e) {
			close();
			log.error("MQ send Msg error:" + e + "\n\t msg:" + msg);
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
	
	private void reInitChannel() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(Global.MQ_HOST);
			factory.setPort(Global.MQ_PORT);
			factory.setUsername(Global.MQ_USERNAME);
			factory.setPassword(Global.MQ_PASSWORD);
			factory.setVirtualHost(Global.MQ_VIRTUALHOST);
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		} catch (Exception e) {
			log.error("init Channel:", e);
		}
	}
}

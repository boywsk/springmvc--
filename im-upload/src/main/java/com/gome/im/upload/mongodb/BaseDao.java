package com.gome.im.upload.mongodb;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.client.MongoCollection;

import com.gome.im.upload.mongodb.MongoDBConfig;
import com.gome.im.upload.mongodb.BaseDao;

public class BaseDao {
	
	static Logger log = LoggerFactory.getLogger(BaseDao.class);
	private static MongoClient client = null;
	protected final static String dbName = MongoDBConfig.MONGODB_DBNAME;

	static {
		try {
			String host = MongoDBConfig.HOST;
			int port = MongoDBConfig.PORT;
			int poolSize = MongoDBConfig.CONNECTIONS_PER_HOST;
			int connectionMultiplier = MongoDBConfig.CONNECTION_MULTIPLIER;

			Builder builder = new MongoClientOptions.Builder();
			builder.connectionsPerHost(poolSize);
			builder.threadsAllowedToBlockForConnectionMultiplier(connectionMultiplier);
			builder.maxWaitTime(30000);
			builder.connectTimeout(10000);
			builder.socketKeepAlive(true);
			builder.socketTimeout(3000);// 套接字超时时间，0无限制
			builder.writeConcern(WriteConcern.SAFE);
			client = new MongoClient(new ServerAddress(host, port),
					builder.build());
		} catch (Exception e) {
			log.error("Can't connect MongoDB!", e);
		}
	}
	
	/**
	 * 获取collection对象
	 * 
	 * @param dbName
	 * @param collName
	 * @return
	 */
	public MongoCollection<Document> getCollection(String dbName,
			String collName) {
		if (null == collName || "".equals(collName)) {
			return null;
		}
		if (null == dbName || "".equals(dbName)) {
			return null;
		}
		// DBCollection coll = mongoClient.getDB(dbName).getDB(collName);
		MongoCollection<Document> collection = client.getDatabase(dbName)
				.getCollection(collName);
		return collection;
	}
	
	
	/**
	 * 插入
	 * 
	 * @param dbName
	 * @param collName
	 * @param doc
	 * @return
	 */
	public boolean insert(String dbName, String collName, Document doc) {
		try {
			MongoCollection<Document> coll = getCollection(dbName, collName);
			coll.insertOne(doc);
		} catch (Exception e) {
			log.error("BaseDAO insert:", e);
			return false;
		}
		return true;
	}
	/**
	 * 更新
	 * @param dbName
	 * @param collName
	 * @param doc
	 * @return
	 */
	public boolean update(String dbName, String collName, Bson filter, Bson update) {
		try {
			MongoCollection<Document> coll = getCollection(dbName, collName);
			coll.updateOne(filter, new Document("$set", update));
		} catch (Exception e) {
			log.error("BaseDAO update:", e);
			return false;
		}
		return true;
	}

}

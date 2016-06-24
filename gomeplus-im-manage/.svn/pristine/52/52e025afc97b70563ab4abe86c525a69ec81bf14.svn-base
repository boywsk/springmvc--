package com.gomeplus.im.manage.mongodb;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gomeplus.im.manage.utils.PropertiesUtils;

/**
 * 存放文件配置信息或者全局变量配置信息
 */
public class MongoDBConfig {

    public static final String mongodbProperties = "mongodb.properties"; //配置文件

    static Logger log = LoggerFactory.getLogger(MongoDBConfig.class);

    //mongondb库名
    public static String MONGODB_DBNAME = "db_im";

    public static String HOST = "10.125.3.21";
    
    public static String FILE_BASIC_PATH = "http://10.125.31.23/v1/img/";

    public static int PORT = 27019;

    public static int CONNECTIONS_PER_HOST = 300;

    public static int CONNECTION_MULTIPLIER = 5000;

    static {
        try {
            Properties properties = PropertiesUtils.LoadProperties(mongodbProperties);
            MONGODB_DBNAME = properties.getProperty("mongodb.dbName");
            HOST = properties.getProperty("host");
            FILE_BASIC_PATH = properties.getProperty("fileBasicPath");
            PORT = Integer.parseInt(properties.getProperty("port"));
            CONNECTIONS_PER_HOST = Integer.parseInt(properties.getProperty("connectionsPerHost"));
            CONNECTIONS_PER_HOST = Integer.parseInt(properties.getProperty("connectionMultiplier"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

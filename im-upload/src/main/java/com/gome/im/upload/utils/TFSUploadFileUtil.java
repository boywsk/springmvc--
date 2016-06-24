package com.gome.im.upload.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.common.tfs.DefaultTfsManager;

public class TFSUploadFileUtil {
	
	private static Logger log = LoggerFactory.getLogger(TFSUploadFileUtil.class);

	private static String TFS_MAXWAITTHREAD;
	private static String TFS_TIMEOUT;
	private static String TFS_NSIP;
	private static String TFS_CLUSTERINDEX;
	private static String TFS_MAXCACHEITEMCOUNT;
	private static String TFS_MAXCACHETIME;
	// private static String TFS_UNIQUESERVERLIST;
	private static String TFS_GROUPNAME;
	private static String TFS_NAMESPACE;
	private static DefaultTfsManager tfsManager = null;

	static {
		Properties prop = new Properties();
		// InputStream in = Object.class.getResourceAsStream("/tfs.properties");
		InputStream in = TFSUploadFileUtil.class.getClassLoader().getResourceAsStream(
				"/tfs.properties");
		try {
			prop.load(in);
			TFS_MAXWAITTHREAD = prop.getProperty("tfs_maxWaitThread").trim();
			TFS_TIMEOUT = prop.getProperty("tfs_timeout").trim();
			TFS_NSIP = prop.getProperty("tfs_nsip").trim();
			TFS_CLUSTERINDEX = prop.getProperty("tfs_ClusterIndex").trim();
			TFS_MAXCACHEITEMCOUNT = prop.getProperty("tfs_maxCacheItemCount")
					.trim();
			TFS_MAXCACHETIME = prop.getProperty("tfs_maxCacheTime").trim();
			// TFS_UNIQUESERVERLIST =
			// prop.getProperty("tfs_uniqueServerList").trim();
			TFS_GROUPNAME = prop.getProperty("tfs_groupName").trim();
			TFS_NAMESPACE = prop.getProperty("tfs_namespace").trim();
			init();
		} catch (IOException e) {
			//log.error("error:"+e.getMessage());
		}
	}

	/**
	 * 获取tfsmanager
	 * 
	 * @return
	 */
	private static void init() {
		if (tfsManager == null) {
			tfsManager = new DefaultTfsManager();
			tfsManager.setMaxWaitThread(Integer.parseInt(TFS_MAXWAITTHREAD));
			tfsManager.setTimeout(Integer.parseInt(TFS_TIMEOUT));
			tfsManager.setNsip(TFS_NSIP);
			tfsManager.setTfsClusterIndex((char) Integer
					.parseInt(TFS_CLUSTERINDEX));
			tfsManager.setMaxCacheItemCount(Integer
					.parseInt(TFS_MAXCACHEITEMCOUNT));
			tfsManager.setMaxCacheTime(Integer.parseInt(TFS_MAXCACHETIME));
			tfsManager.setGroupName(TFS_GROUPNAME);
			tfsManager.setNamespace(Integer.parseInt(TFS_NAMESPACE));
			tfsManager.init();
		}
	}

	/**
	 * 获取文件名
	 * @return
	 */
	public static String getFileName() {
		String fileName = "";
		try {
			log.info("tfs serverIp:{}",tfsManager.getTfsSession().getNameServerIp());
			fileName = tfsManager.newTfsFileName("");
		} catch (Exception e) {
			log.error("error msg : tfsManager.newTfsFileName() return null"+e.getMessage().toString());
		}
		return fileName;
	}

	/**
	 * 上传文件
	 *
	 * @param bytes  文件流
	 * @param suffix 文件格式  (后缀扩展名  如: ".jpg" 或 ".png" )
	 * @return 返回文件名
	 */
	public static String uploadFile(String newTfsFileName,byte[] bytes, String suffix, String traceId) {
		String tfsFileName = null;
		try {
			if (bytes != null && suffix != null && !suffix.equals("")) {
				log.info("traceId:"+traceId+"; tfs serverIp:{}",tfsManager.getTfsSession().getNameServerIp()+"; fileSize:(bytes.length)"+bytes.length);
//				String tfsReturnFileName = tfsManager.saveFile(bytes, newTfsFileName, suffix);
				String tfsReturnFileName = tfsManager.saveFile(bytes, newTfsFileName, suffix,true);
				if (tfsReturnFileName == null) {
					log.error("traceId:"+traceId+"; error msg : tfsManager.saveFile return null");
					return null;
				}
//				tfsFileName = tfsReturnFileName + suffix;
				tfsFileName = tfsReturnFileName;
			}
		} catch (Exception e) {
			log.error("traceId:"+traceId+"; error:"+e.getMessage());
		}
		return tfsFileName;
	}

}

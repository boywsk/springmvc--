package cn.com.gome.manage.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import cn.com.gome.manage.mongodb.MongoDBConfig;
import cn.com.gome.manage.mongodb.model.FileModel;
import cn.com.gome.manage.mongodb.model.file.AvatarModel;
import cn.com.gome.manage.mongodb.model.file.FileCount;
import cn.com.gome.manage.mongodb.model.file.ImageUrl;
import cn.com.gome.manage.mongodb.model.file.VedioModel;
import cn.com.gome.manage.mongodb.model.file.VoiceModel;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.utils.BeanTransUtils;

public class FileDao extends BaseDao{
	Logger log = LoggerFactory.getLogger(FileDao.class);
	private final static String databaseName = "db_im";
	private final static String TFILECOUNT = "t_filecount";
	
	/**
	 * 获取统计数据 
	 * @param doc,collName
	 */
	public List<FileCount> getFileCountInfo(PageInfo pageInfo, String startDate, String endDate){
		List<FileCount> fileCountList = new ArrayList<FileCount>();
		MongoCursor<Document> cursor = null;
		MongoCollection<Document> coll = getCollection(databaseName,TFILECOUNT);
		BasicDBObject filter = new BasicDBObject();
		/*if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
			BasicDBList condition = new BasicDBList();
			condition.put("countDate", new BasicDBObject(QueryOperators.GTE,startDate));
			condition.put("countDate", new BasicDBObject(QueryOperators.LTE,endDate));
			filter.put(QueryOperators.AND, condition);
		}*/
		if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
			filter.append(QueryOperators.AND, new BasicDBObject[]{new BasicDBObject().append("countDate",new BasicDBObject().append(QueryOperators.GTE, startDate)),new BasicDBObject().append("countDate",new BasicDBObject().append(QueryOperators.LTE, endDate))});
		}else if(StringUtils.isNotEmpty(startDate)){
			filter.put("countDate", new BasicDBObject().append(QueryOperators.GTE, startDate));  
		}else if(StringUtils.isNotEmpty(endDate)){
			filter.put("countDate", new BasicDBObject().append(QueryOperators.LTE, endDate));
		}
		log.info("DBName:"+databaseName+";collName:"+TFILECOUNT+";filter:"+filter.toJson().toString());
		Bson sort = new BasicDBObject("countDate",-1);
		cursor = coll.find(filter).sort(sort).skip((pageInfo.getCurrentPage()-1)*pageInfo.getPageSize()).limit(pageInfo.getPageSize()).iterator();
		long count = coll.count(filter);
		pageInfo.setTotalResult((int)count);
		pageInfo.calculate();
		while(cursor.hasNext()){
			Document item = cursor.next();
			FileCount fc = (FileCount)BeanTransUtils.document2Bean(item, FileCount.class);
			fileCountList.add(fc);
		}
		return fileCountList;
	}
	
	/**
	 * 插入数据统计 
	 * @param doc,collName
	 */
	public void insertFileCount(Document doc, String collName){
		insert(databaseName,collName,doc);
	}
	
	/**
	 * 数据统计 
	 * @param startTime,endTime,collName
	 * @return count
	 */
	public long fileCount(long startTime, long endTime, String collName){
		long count = 0 ;
		log.info("collName:"+collName+";startTime:"+startTime+";endTime:"+endTime);
		MongoCollection<Document> coll = getCollection(databaseName,collName);
		//BasicDBObject filter = new BasicDBObject();
			//BasicDBList condition = new BasicDBList();
			Bson condition = Filters.and(Filters.gte("uploadTime",startTime),Filters.lte("uploadTime",endTime));
				//condition.put("uploadTime", new BasicDBObject(QueryOperators.GTE,startTime));
				//condition.put("uploadTime", new BasicDBObject(QueryOperators.LTE,endTime));
		//filter.put(QueryOperators.AND,condition);
		log.info("condition:"+condition.toString());
		count = coll.count(condition);
		return count;
	}
	
	public List<FileModel> getFileInfo(PageInfo pageInfo, FileModel fileModel){
		List<FileModel> fileInfoList = new ArrayList<FileModel>();
		MongoCursor<Document> cursor = null;
		try{
			MongoCollection<Document> coll = getCollection(databaseName, fileModel.getSearchType());
			BasicDBObject filter = new BasicDBObject();
			if(StringUtils.isNotEmpty(fileModel.getUid())){
				filter.put("uid", Long.parseLong(fileModel.getUid()));
			}			
			if(StringUtils.isNotEmpty(fileModel.getStartTime()) && StringUtils.isNotEmpty(fileModel.getEndTime())){
				/*BasicDBList condition = new BasicDBList();
				condition.put("createTime", new BasicDBObject(QueryOperators.GTE, Long.parseLong(fileModel.getStartTime())));
				condition.put("createTime", new BasicDBObject(QueryOperators.LTE, Long.parseLong(fileModel.getEndTime())));
				filter.put(QueryOperators.AND,condition);*/
				filter.append(QueryOperators.AND, new BasicDBObject[]{new BasicDBObject().append("uploadTime",new BasicDBObject().append(QueryOperators.GTE, Long.parseLong(fileModel.getStartTime()))),new BasicDBObject().append("uploadTime",new BasicDBObject().append(QueryOperators.LTE, Long.parseLong(fileModel.getEndTime())))});
			}else if(StringUtils.isNotEmpty(fileModel.getStartTime())){
				filter.put("uploadTime",Long.parseLong(fileModel.getStartTime()));
			}else if(StringUtils.isNotEmpty(fileModel.getEndTime())){
				filter.put("uploadTime",Long.parseLong(fileModel.getEndTime()));
			}
			Bson sort = new BasicDBObject("uploadTime",-1);
			cursor = coll.find(filter).sort(sort).skip((pageInfo.getCurrentPage()-1)*pageInfo.getPageSize()).limit(pageInfo.getPageSize()).iterator();
			log.info("DBName:"+databaseName+";collName:"+fileModel.getSearchType()+"; filter:"+filter.toJson().toString());
			long count = coll.count(filter);
			pageInfo.setTotalResult((int)count);
			pageInfo.calculate();
			while(cursor.hasNext()){
				Document item = cursor.next();
				FileModel file = new FileModel();
				if(fileModel.getSearchType().equals("t_avatar_url")){
					AvatarModel avatarInfo = (AvatarModel)BeanTransUtils.document2Bean(item, AvatarModel.class);
					file.setUid(Long.toString(avatarInfo.getUid()));
					file.setCreateTime(Long.toString(avatarInfo.getUploadTime()));
					file.setFileName(avatarInfo.getAvatarSmallName());
					file.setFilePath(MongoDBConfig.FILE_BASIC_PATH + avatarInfo.getAvatarSmallName());
				}else if(fileModel.getSearchType().equals("t_image_url")){
					ImageUrl imageInfo = (ImageUrl)BeanTransUtils.document2Bean(item, ImageUrl.class);
					file.setUid(Long.toString(imageInfo.getUid()));
					file.setCreateTime(Long.toString(imageInfo.getUploadTime()));
					file.setFileName(imageInfo.getImgSmallName());
					file.setFilePath(MongoDBConfig.FILE_BASIC_PATH + imageInfo.getImgSmallName());
				}else if(fileModel.getSearchType().equals("t_voice_url")){
					VoiceModel voiceInfo = (VoiceModel)BeanTransUtils.document2Bean(item, VoiceModel.class);
					file.setUid(Long.toString(voiceInfo.getUid()));
					file.setCreateTime(Long.toString(voiceInfo.getUploadTime()));
					file.setFileName(voiceInfo.getVoiceName());
					file.setFilePath(MongoDBConfig.FILE_BASIC_PATH + voiceInfo.getVoiceName());
				}else if(fileModel.getSearchType().equals("t_vedio_url")){
					VedioModel vedioModel = (VedioModel)BeanTransUtils.document2Bean(item, VedioModel.class);
					file.setUid(Long.toString(vedioModel.getUid()));
					file.setCreateTime(Long.toString(vedioModel.getUploadTime()));
					file.setFileName(vedioModel.getVedioName());
					file.setFilePath(MongoDBConfig.FILE_BASIC_PATH + vedioModel.getVedioName());
				}
				fileInfoList.add(file);
			}
			
		}catch(Exception e){
			log.info(e.toString());
		}
		return fileInfoList;
	}

}

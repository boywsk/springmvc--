package cn.com.gome.manage.timedJob;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.com.gome.manage.mongodb.dao.FileDao;

@Component
public class fileTimedJob {
	Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * 时间设置
	 * 每5秒执行一次：			0/5 * * * * *
	 * 第分钟执行一次：			0 * * * * *
	 * 每天凌晨4：30执行执行一次：	0 30 4 * * ?
	 * */
    @Scheduled(cron = "0 30 4 * * ?") 		 
    public void run() {
    	long yesterdayStartTimeM = 0;
		long yesterdayEndTimeM = 0;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DATE, -1);
    	String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    	String yesterdayStartTime = yesterday + " 00:00:00";
    	String yesterdayEndTime = yesterday + " 59:59:59";
    	log.info("yesterdayStartTime: "+yesterdayStartTime+";  yesterdayEndTime: "+yesterdayEndTime);
    	try {
			yesterdayStartTimeM = sdf.parse(yesterdayStartTime).getTime();
			yesterdayEndTimeM = sdf.parse(yesterdayEndTime).getTime();
			log.info("yesterdayStartTimeM: "+yesterdayStartTimeM+";  yesterdayEndTimeM: "+yesterdayEndTimeM);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	FileDao fileDao= new FileDao();
    	long avatarCount = fileDao.fileCount(yesterdayStartTimeM, yesterdayEndTimeM, "t_avatar_url");
    	long imageCount = fileDao.fileCount(yesterdayStartTimeM, yesterdayEndTimeM, "t_image_url");
    	long voiceCount = fileDao.fileCount(yesterdayStartTimeM, yesterdayEndTimeM, "t_voice_url");
    	long vedioCount = fileDao.fileCount(yesterdayStartTimeM, yesterdayEndTimeM, "t_vedio_url");
    	Document document = new Document();
    	document.append("countDate", yesterday);
    	document.append("avatar", avatarCount);
    	document.append("image", imageCount);
    	document.append("voice", voiceCount);
    	document.append("vedio", vedioCount);
    	fileDao.insertFileCount(document, "t_filecount");
    	log.info("countDate:"+ yesterday+"; avatar:"+avatarCount+"; image:"+imageCount+"; voice:"+voiceCount+"; vedio"+vedioCount);
    }
}

package cn.com.gome.manage.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.com.gome.manage.mongodb.dao.FileDao;
import cn.com.gome.manage.mongodb.model.FileModel;
import cn.com.gome.manage.mongodb.model.file.FileCount;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.service.FileService;
@Service
public class FileServiceImpl implements FileService {
	//private static Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
	private final static FileDao fileDao = new FileDao();
	@Override
	public List<FileModel> getFileInfo(PageInfo pageInfo, FileModel fileModel) {
			try {
				if(StringUtils.isNotEmpty(fileModel.getStartTime())){
					fileModel.setStartTime(timeToMs(fileModel.getStartTime()+" 00:00:00"));
				}
				if(StringUtils.isNotEmpty(fileModel.getEndTime())){
					fileModel.setEndTime(timeToMs(fileModel.getEndTime()+" 23:59:59"));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		List<FileModel> fileInfo = fileDao.getFileInfo(pageInfo, fileModel);
		for (FileModel list : fileInfo) {
			long createTime = Long.parseLong(list.getCreateTime());
			try{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = formatter.format(createTime);
				list.setCreateTime(date);
			}catch(Exception e){
				list.setCreateTime(String.valueOf(createTime));
			}
		}
		return fileInfo;
	}
	public List<FileCount> getFileCountInfo(PageInfo pageInfo, String startDate, String endDate){
		List <FileCount> fileCount = fileDao.getFileCountInfo(pageInfo, startDate, endDate);
		return fileCount;
	}
	
	public String timeToMs(String time) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String msTime = Long.toString(sdf.parse(time).getTime());
		return msTime;
	}

}

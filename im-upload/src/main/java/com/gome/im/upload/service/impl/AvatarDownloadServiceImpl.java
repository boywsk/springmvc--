package com.gome.im.upload.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.im.upload.mongodb.AvatarDao;
import com.gome.im.upload.service.AvatarDownloadService;
import com.gome.im.upload.utils.AvatarUtil;

public class AvatarDownloadServiceImpl implements AvatarDownloadService {
	private static Logger log = LoggerFactory.getLogger(AvatarDownloadServiceImpl.class);

	public String getAvatarUrl(long uid) {
		String url = AvatarUtil.TFS_URL;
		String avatarUrl = "";
		try{
			AvatarDao avatarDao= new AvatarDao();
			String smallAvatarName = avatarDao.getAvatarName(uid);
			if(smallAvatarName != null && smallAvatarName.length() != 0){
				avatarUrl = url +smallAvatarName;
			}else{
				avatarUrl = url + AvatarUtil.DEFAULT_AVATAR;
			}
			
		}catch(Exception e){
			log.error(e.toString());
		}
		return avatarUrl;
	}

}

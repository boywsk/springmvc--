package com.gome.im.upload.service;

import java.io.InputStream;

import com.gome.im.upload.global.Constant.FILE_SUFFIX;
import com.gome.im.upload.model.ResultModel;

public interface AvatarUploadService {
	/**
	 * 上传头像图片
	 * @param input 输入流
	 * @param suffix 文件后缀名
	 * @param originalImage 是否原图
	 * */
	ResultModel<Object> uploadAvatar(InputStream input, FILE_SUFFIX suffix, long uid, String traceId);

}

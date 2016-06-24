package com.gome.im.upload.service;

import java.io.InputStream;

import com.gome.im.upload.model.ResultModel;

public interface VoiceUploadService {
	
	/**
	 * 上传语音 Video
	 * 
	 * @param input
	 *            输入流
	 * 
	 * @param suffix
	 *            文件后缀名
	 * 
	 * */
	ResultModel<Object> uploadVoice(InputStream input,String suffix, long uid, String traceId);

}

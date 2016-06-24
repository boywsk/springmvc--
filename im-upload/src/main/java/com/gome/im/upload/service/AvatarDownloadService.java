package com.gome.im.upload.service;

public interface AvatarDownloadService {
	/**
	 * 上传头像图片
	 * @param input 输入流
	 * @param suffix 文件后缀名
	 * @param originalImage 是否原图
	 * */
	public String getAvatarUrl(long uid);
}

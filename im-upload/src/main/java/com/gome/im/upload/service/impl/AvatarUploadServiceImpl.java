package com.gome.im.upload.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.im.upload.global.Constant.FILE_SUFFIX;
import com.gome.im.upload.model.AvatarModel;
import com.gome.im.upload.model.AvatarResponseModel;
import com.gome.im.upload.model.ImageReSizeModel;
import com.gome.im.upload.model.ResultModel;
import com.gome.im.upload.mongodb.AvatarDao;
import com.gome.im.upload.service.AvatarUploadService;
import com.gome.im.upload.utils.AvatarUtil;
import com.gome.im.upload.utils.ImageUtil;
import com.gome.im.upload.utils.TFSUploadFileUtil;

public class AvatarUploadServiceImpl implements AvatarUploadService {
	private static Logger log = LoggerFactory.getLogger(AvatarUploadServiceImpl.class);
	private static ExecutorService executorService = Executors.newCachedThreadPool();

	@Override
	public ResultModel<Object> uploadAvatar(InputStream input, FILE_SUFFIX suffix, long uid, String traceId) {
		AvatarResponseModel arm = new AvatarResponseModel();
		final AvatarModel avatarModel = new AvatarModel();
		avatarModel.setUid(uid);
		String URL_PATH = ImageUtil.TFS_URL;
		
		try {
			int len = input.available();

			byte[] in = getByteArrayBySize(input,len);

			String newTfsFileName = TFSUploadFileUtil.getFileName();//文件名
			if(StringUtils.isNotEmpty(newTfsFileName)){
				avatarModel.setAvatarOriginName(newTfsFileName);
	
				// 将原图存入到tfs中
				String fileSuffix = "." + suffix.value;//文件后缀
				String originalImg = TFSUploadFileUtil.uploadFile(newTfsFileName,in, fileSuffix,traceId);
					if(StringUtils.isNotEmpty(originalImg)){
						ImageReSizeModel dataResult = AvatarUtil.AvatarOriginSize(new ByteArrayInputStream(in));
						avatarModel.setAvatarOriginHeight((int)dataResult.getHeight());
						avatarModel.setAvatarOriginWidth((int)dataResult.getWidth());
						
						// 将图片压缩至180存入tfs中			
						ImageReSizeModel irmSmall = AvatarUtil.resizeChatIcon(new ByteArrayInputStream(in), suffix.value);
						fileSuffix = AvatarUtil.SMALL_SIZE + "." + suffix.value;//文件后缀
						String smallPath = TFSUploadFileUtil.uploadFile(newTfsFileName,irmSmall.getData(), fileSuffix, traceId);
							if(StringUtils.isNotEmpty(smallPath)){
								avatarModel.setAvatarSmallName(smallPath);			
								avatarModel.setAvatarSmallHeight((int)irmSmall.getHeight());
								avatarModel.setAvatarSmallWidth((int)irmSmall.getWidth());
								arm.setSmallAvatarUrl(URL_PATH+smallPath);
								arm.setWidth((int)irmSmall.getWidth());
								arm.setHeight((int)irmSmall.getHeight());
								
								// 当上传图片是原图时，将图片压缩至480存入tfs中
								ImageReSizeModel irmLarge = AvatarUtil.resizeImage(new ByteArrayInputStream(in), suffix.value);
								fileSuffix = AvatarUtil.BIG_SIZE + "." + suffix.value;//文件后缀
								String LargePath=TFSUploadFileUtil.uploadFile(newTfsFileName,irmLarge.getData(), fileSuffix, traceId);
								if(StringUtils.isEmpty(LargePath)){
									log.error("traceId:"+traceId+"; error msg : TFSUploadFileUtil.uploadFile(),save LargeAvatar return null");
									return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传图片文件异常",new HashMap<>());
								}
								avatarModel.setAvatarLargeName(LargePath);
								avatarModel.setAvatarLargeHeight((int)irmLarge.getHeight());
								avatarModel.setAvatarLargeWidth((int)irmLarge.getWidth());
								
								log.info("Test url:"+URL_PATH+smallPath);
								long uploadTime = System.currentTimeMillis();
								avatarModel.setUploadTime(uploadTime);
								arm.setUploadTime(uploadTime);
								executorService.submit(new Runnable() {
									@Override
									public void run() {
										AvatarDao avatarDao = new AvatarDao();					
										avatarDao.updateInsertAvatar(avatarModel);
									}
								});
							}else{
								log.error("traceId:"+traceId+"; error msg : TFSUploadFileUtil.uploadFile(),save smallAvatar return null");
								return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传图片文件异常",new HashMap<>());
							}
							
					}else{
						log.error("traceId:"+traceId+"; error msg : TFSUploadFileUtil.uploadFile(),save originalAvatar return null");
						return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传图片文件异常",new HashMap<>());
					}
			}else{
				log.error("traceId:"+traceId+"; error msg : tfsManager.newTfsFileName() return null");
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传图片文件异常",new HashMap<>());
			}
		} catch (Exception e) {
			return new ResultModel<Object>(ResultModel.RESULT_OK, "图片储存失败",new HashMap<>());
		} finally {
			try {
				if(input != null){
					input.close();
				}
			} catch (IOException e) {
				log.error("error:" + e.getMessage());
			}
		}
		return new ResultModel<Object>(ResultModel.RESULT_OK, "图片储存成功",arm);
	}
	
	/**
	 *  将输入流转为 byte[]
	 * @param input 输入流
	 * @param byteSize  byte[]大小
	 * @return
	 */
	public byte[] getByteArrayBySize(InputStream input,int byteSize){
		byte[] byteArray = null;
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		int readLength = -1;
		byte bytes[] = new byte[byteSize];
		try {
			while ((readLength = input.read(bytes, 0, byteSize)) != -1) {
				byteStream.write(bytes, 0, readLength);
			}
			byteArray = byteStream.toByteArray();
		} catch (IOException e) {
			log.error("error:" + e.getMessage());
		}finally {
			try {
				input.close();
			} catch (IOException e) {
				log.error("error:"+e.getMessage());
			}
		}
		return byteArray;
	}

}

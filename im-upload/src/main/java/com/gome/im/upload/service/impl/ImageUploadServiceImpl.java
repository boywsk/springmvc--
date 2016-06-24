package com.gome.im.upload.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.im.upload.global.Constant.FILE_SUFFIX;
import com.gome.im.upload.model.ImageReSizeModel;
import com.gome.im.upload.model.ImageUrl;
import com.gome.im.upload.model.ResponseModel;
import com.gome.im.upload.model.ResultModel;
import com.gome.im.upload.mongodb.ImageDao;
import com.gome.im.upload.service.ImageUploadService;
import com.gome.im.upload.utils.ImageUtil;
import com.gome.im.upload.utils.TFSUploadFileUtil;

//@Service
public class ImageUploadServiceImpl implements ImageUploadService {
	private static Logger log = LoggerFactory.getLogger(ImageUploadServiceImpl.class);
	private static ExecutorService executorService = Executors.newCachedThreadPool();

	public ResultModel<Object> uploadImg(InputStream input, FILE_SUFFIX suffix, boolean originalImage, long uid,String traceId) {

		ResponseModel rm = new ResponseModel();
		final ImageUrl imu = new ImageUrl();
		imu.setUid(uid);

		String URL_PATH = ImageUtil.TFS_URL;
		try {
			int len = input.available();
			byte[] in = getByteArrayBySize(input,len);
			if(in != null){
				String newTfsFileName = TFSUploadFileUtil.getFileName();//文件名
				if(StringUtils.isNotEmpty(newTfsFileName)){
					imu.setImgOriginName(newTfsFileName);
					log.info("traceId:"+traceId+"; START UPLOADING……");
					// 将原图存入到tfs中
					String fileSuffix = "." + suffix.value;//文件后缀
					String originalImg =TFSUploadFileUtil.uploadFile(newTfsFileName,in, fileSuffix,traceId);
					if(StringUtils.isNotEmpty(originalImg)){
						ImageReSizeModel dataResult = ImageUtil.imgOriginSize(new ByteArrayInputStream(in));
						imu.setImgOriginHeight(dataResult.getHeight());
						imu.setImgOriginWidth(dataResult.getWidth());
						log.info("traceId:"+traceId+"; save imgOriginSize succeed! width:"+dataResult.getWidth()+";height:"+dataResult.getHeight());
						// 将图片压缩至360存入tfs中			
						ImageReSizeModel irmSmall = ImageUtil.resizeChatIcon(new ByteArrayInputStream(in), suffix.value);
						fileSuffix = ImageUtil.SMALL_SIZE + "." + suffix.value;//文件后缀
						String smallPath = TFSUploadFileUtil.uploadFile(newTfsFileName,irmSmall.getData(), fileSuffix, traceId);
						if(StringUtils.isNotEmpty(smallPath)){
							imu.setImgSmallName(smallPath);			
							imu.setImgSmallHeight(irmSmall.getHeight());
							imu.setImgSmallWidth(irmSmall.getWidth());
							rm.setImgSmallName(smallPath);
							rm.setWidth((int)irmSmall.getWidth());
							rm.setHeight((int)irmSmall.getHeight());
							log.info("traceId:"+traceId+"; save SmallOriginSize succeed! width:"+irmSmall.getWidth()+";height:"+irmSmall.getHeight());
							if(originalImage) {
								// 当上传图片是原图时，将图片压缩至480存入tfs中
								ImageReSizeModel irmMiddle = ImageUtil.resizeImage(new ByteArrayInputStream(in), suffix.value);
								fileSuffix = ImageUtil.MIDDLE_SIZE + "." + suffix.value;//文件后缀
								String middlePath=TFSUploadFileUtil.uploadFile(newTfsFileName,irmMiddle.getData(), fileSuffix, traceId);
								if(StringUtils.isEmpty(middlePath)){
									log.error("traceId:"+traceId+"; error msg : TFSUploadFileUtil.uploadFile(),save middleImg return null");
									return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传图片文件异常",new HashMap<>());
								}
								imu.setImgMiddleName(middlePath);
								imu.setImgMiddleHeight(irmMiddle.getHeight());
								imu.setImgMiddleWidth(irmMiddle.getWidth());
								log.info("traceId:"+traceId+"; save MiddleOriginSize succeed! width:"+irmMiddle.getWidth()+";height:"+irmMiddle.getHeight());
							}
							log.info("traceId:"+traceId+"; Test url:"+URL_PATH+smallPath);
							long uploadTime = System.currentTimeMillis();
							imu.setUploadTime(uploadTime);
							rm.setUploadTime(uploadTime);
							executorService.submit(new Runnable() {
								@Override
								public void run() {
									ImageDao id = new ImageDao();
									id.addImageUrl(imu);
								}
							});
							log.info("traceId:"+traceId+"; END UPLOADING……");
						}else{
							log.error("traceId:"+traceId+"; error msg : TFSUploadFileUtil.uploadFile(),save smallImg return null");
							return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传图片文件异常",new HashMap<>());
						}
					}else{
						log.error("traceId:"+traceId+"; error msg : TFSUploadFileUtil.uploadFile(),save originalImg return null");
						return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传图片文件异常",new HashMap<>());
					}
				}else{
					log.error("traceId:"+traceId+"; error msg : tfsManager.newTfsFileName() return null");
					return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传图片文件异常",new HashMap<>());
				}
			}else{
				log.error("traceId:"+traceId+"; error msg : Image byte[] return null");
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传图片文件异常",new HashMap<>());
			}
		} catch (Exception e) {
			log.error("traceId:"+traceId+"; during upload, save file failed! "+e.getMessage());
			return new ResultModel<Object>(ResultModel.RESULT_OK, "图片储存失败",new HashMap<>());
		} finally {
			try {
				if(input != null){
					input.close();
				}
			} catch (IOException e) {
				log.error("traceId:"+traceId+"; error:" + e.getMessage());
			}
		}
		return new ResultModel<Object>(ResultModel.RESULT_OK, "图片储存成功",rm);
	}

	public ResultModel<Object> uploadVoice(InputStream input,FILE_SUFFIX suffix, String traceId) {
		int byteSize = 1024; //byte[]大小
		try{
			byteSize = input.available();
		}catch (Exception e){
			log.error("error:" + e.getMessage());
		}
		ResultModel<Object> result = uploadFile(input,suffix,byteSize, traceId);
		return result;
	}

	public ResultModel<Object> uploadVideo(InputStream input, FILE_SUFFIX suffix, String traceId) {
		int byteSize = 1024;//byte[]大小
		try{
			byteSize = input.available();
		}catch (Exception e){
			log.error("error:" + e.getMessage());
		}
		ResultModel<Object> result = uploadFile(input,suffix,byteSize, traceId);
		return result;
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

	/**
	 *   TODO ：这里对于不同类型的文件（比如图片、音频、视频）可能上传或下载方式以及URL地址可能会有不同 (待考虑)
	 * 上传文件
	 * @param input 输入流
	 * @param suffix  文件后缀
	 * @param byteSize  byte[]大小
	 * @return
	 */
	public ResultModel<Object> uploadFile(InputStream input, FILE_SUFFIX suffix, int byteSize, String traceId){
		Map<String, Object> data = new HashMap<String, Object>();
		try{
			byte[] bytes = getByteArrayBySize(input,byteSize);
			if(bytes != null){
				String newTfsFileName = TFSUploadFileUtil.getFileName();
				String fileSuffix = "." + suffix.value;//文件后缀
				String fileName = TFSUploadFileUtil.uploadFile(newTfsFileName,bytes, fileSuffix, traceId);
				if (fileName == null) {
					return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传文件异常",new HashMap<>());
				}
				String url = ImageUtil.TFS_URL + fileName;
				data.put("url",url);
			}else{
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传文件异常",new HashMap<>());
			}
		}catch (Exception e){
			log.error("error:"+e.getMessage());
		}
		return new ResultModel<Object>(ResultModel.RESULT_OK, "上传文件成功",data);
	}

}

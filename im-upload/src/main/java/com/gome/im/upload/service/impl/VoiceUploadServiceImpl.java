package com.gome.im.upload.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.im.upload.model.ResultModel;
import com.gome.im.upload.model.VoiceModel;
import com.gome.im.upload.mongodb.VoiceDao;
import com.gome.im.upload.service.VoiceUploadService;
import com.gome.im.upload.utils.ImageUtil;
import com.gome.im.upload.utils.TFSUploadFileUtil;

public class VoiceUploadServiceImpl implements VoiceUploadService {
	
	private static Logger log = LoggerFactory.getLogger(VoiceUploadServiceImpl.class);
	private static ExecutorService executorService = Executors.newCachedThreadPool();

	@Override
	public ResultModel<Object> uploadVoice(InputStream input, String suffix, long uid, String traceId) {
		int byteSize = 1024; //byte[]大小
		try{
			byteSize = input.available();
		}catch (Exception e){
			log.error("traceId:"+traceId+"; error:" + e.getMessage());
		}
		ResultModel<Object> result = uploadVoiceFile(input,suffix,byteSize,uid, traceId);
		return result;
	}
	/**
	 * 上传音频文件
	 * @param input 输入流
	 * @param suffix  文件后缀
	 * @param byteSize  byte[]大小
	 * @return
	 */
	public ResultModel<Object> uploadVoiceFile(InputStream input, String suffix, int byteSize,long uid, String traceId){
		final VoiceModel vm = new VoiceModel();
		try{
			byte[] bytes = getByteArrayBySize(input,byteSize, traceId);
			if(bytes != null){
				String newTfsFileName = TFSUploadFileUtil.getFileName();
				if(StringUtils.isNotEmpty(newTfsFileName)){
					String fileSuffix = "." + suffix;//文件后缀
					String fileName = TFSUploadFileUtil.uploadFile(newTfsFileName,bytes, fileSuffix,traceId);
					if (fileName == null) {
						return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传语音文件异常",new HashMap<>());
					}
					vm.setUid(uid);
					vm.setVoiceName(fileName);
					vm.setUploadTime(System.currentTimeMillis());				
					executorService.submit(new Runnable() {
						@Override
						public void run() {
							VoiceDao vd = new VoiceDao();
							vd.addVoiceUrl(vm);
						}
					});								
					String url = ImageUtil.TFS_URL + fileName;
					log.info("traceId:"+traceId+"; Test url:"+url);
				}else{
					log.error("traceId:"+traceId+"; error msg : tfsManager.newTfsFileName() return null");
					return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传语音文件异常",new HashMap<>());
				}
			}else{
				log.error("traceId:"+traceId+"; error msg : Voice byte[] return null");
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传语音文件异常",new HashMap<>());
			}
		}catch (Exception e){
			log.error("traceId:"+traceId+"; error:"+e.getMessage());
		}
		return new ResultModel<Object>(ResultModel.RESULT_OK, "上传语音文件成功",vm);
	}
	
	/**
	 *  将输入流转为 byte[]
	 * @param input 输入流
	 * @param byteSize  byte[]大小
	 * @return
	 */
	public byte[] getByteArrayBySize(InputStream input,int byteSize, String traceId){
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
			log.error("traceId:"+traceId+"; error:" + e.getMessage());
		}finally {
			try {
				input.close();
			} catch (IOException e) {
				log.error("traceId:"+traceId+"; error:"+e.getMessage());
			}
		}
		return byteArray;
	}

}

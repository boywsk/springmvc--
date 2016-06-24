package com.gome.im.api.service.impl;
import com.gome.im.api.global.Constant;
import com.gome.im.api.model.ResultModel;
import com.gome.im.api.service.UploadService;
import com.gome.im.api.utils.ImageUtil;
import com.gome.im.api.utils.QRCodeUtil;
import com.gome.im.api.utils.TFSUploadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import com.gome.im.api.global.Constant.FILE_SUFFIX;

@Service
public class UploadServiceImpl implements UploadService {
	private static Logger log = LoggerFactory.getLogger(UploadServiceImpl.class);

	@Override
	public ResultModel<Object> uploadImg(InputStream input, FILE_SUFFIX suffix, boolean originalImage) {

		Map<String, Object> dataPath = new HashMap<String, Object>();

		String URL_PATH = ImageUtil.TFS_URL;
		try {
			int len = input.available();

			byte[] in = getByteArrayBySize(input,len);

			String newTfsFileName = TFSUploadFileUtil.getFileName();//文件名

			// 将原图存入到tfs中
			String fileSuffix = "." + suffix.value;//文件后缀
			TFSUploadFileUtil.uploadFile(newTfsFileName,in, fileSuffix);

			// 将图片压缩至180存入tfs中
			byte[] data = ImageUtil.resizeChatIcon(new ByteArrayInputStream(in), suffix.value);
			fileSuffix = "_" +  (int) ImageUtil.SMQLL_SIZE + "." + suffix.value;//文件后缀
			String smallPath = TFSUploadFileUtil.uploadFile(newTfsFileName,data, fileSuffix);
			smallPath = URL_PATH + smallPath;

			if(originalImage) {
				// 当上传图片是原图时，将图片压缩至480存入tfs中
				data = ImageUtil.resizeImage(new ByteArrayInputStream(in), suffix.value);
				fileSuffix = "_" + (int) ImageUtil.NORMAL_SIZE + "." + suffix.value;//文件后缀
				TFSUploadFileUtil.uploadFile(newTfsFileName,data, fileSuffix);
			}

			dataPath.put("url", smallPath);
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
		return new ResultModel<Object>(ResultModel.RESULT_OK, "图片储存成功",dataPath);
	}

	@Override
	public ResultModel<Object> uploadVoice(InputStream input,FILE_SUFFIX suffix) {
		int byteSize = 1024; //byte[]大小
		try{
			byteSize = input.available();
		}catch (Exception e){
			log.error("error:" + e.getMessage());
		}
		ResultModel<Object> result = uploadFile(input,suffix,byteSize);
		return result;
	}

	@Override
	public ResultModel<Object> uploadVideo(InputStream input, FILE_SUFFIX suffix) {
		int byteSize = 1024;//byte[]大小
		try{
			byteSize = input.available();
		}catch (Exception e){
			log.error("error:" + e.getMessage());
		}
		ResultModel<Object> result = uploadFile(input,suffix,byteSize);
		return result;
	}

	@Override
	public String uploadQRCodeImage(String content) {
		String url = null;
		try {
			//生成二维码
			byte[] bytes = QRCodeUtil.buildQRCodeImage(content);

			//将二维码上传   获取tfs文件名
			String suffix = "." + FILE_SUFFIX.JPG.value;//文件后缀
			String newTfsFileName = TFSUploadFileUtil.getFileName();
			String fileName = TFSUploadFileUtil.uploadFile(newTfsFileName,bytes, suffix);
			if (fileName == null) {
				return null;
			}
			url = ImageUtil.TFS_URL + fileName;
		} catch (Exception e) {
			log.error("error:" + e.getMessage());
		}
		return url;
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
	public ResultModel<Object> uploadFile(InputStream input, FILE_SUFFIX suffix, int byteSize){
		Map<String, Object> data = new HashMap<String, Object>();
		try{
			byte[] bytes = getByteArrayBySize(input,byteSize);
			if(bytes != null){
				String newTfsFileName = TFSUploadFileUtil.getFileName();
				String fileSuffix = "." + suffix.value;//文件后缀
				String fileName = TFSUploadFileUtil.uploadFile(newTfsFileName,bytes, fileSuffix);
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

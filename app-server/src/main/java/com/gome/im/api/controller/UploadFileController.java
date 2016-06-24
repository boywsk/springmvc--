package com.gome.im.api.controller;

import com.gome.im.api.global.Constant.FILE_SUFFIX;
import com.gome.im.api.model.ResultModel;
import com.gome.im.api.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Controller
@RequestMapping("upload")
public class UploadFileController {
	
	static Logger log = LoggerFactory.getLogger(UploadFileController.class);

	@Autowired
	private UploadService uploadService;

	/**
	 * 上传文件
	 *
	 * @param token    token
	 * @param uid      用户id
	 * @param originalImage      是否是原图（原图需要多进行一次压缩） 0:不是原图  1：是原图
	 * @param file     上传文件
	 */
	@RequestMapping(value = "uploadImage", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> uploadImage(@RequestParam(value = "token", required = true) String token,
										   @RequestParam(value = "uid", required = true) long uid,
										   @RequestParam(value = "originalImage", required = true) int originalImage,
										   @RequestParam(value = "file", required = true) MultipartFile file) {
		log.info("uploadImage fileName:[{}]", file.getOriginalFilename());
		InputStream input = null;
		FILE_SUFFIX fileSuffix = null;//文件格式 后缀扩展名
		try {
			if (file != null) {
				String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);//上传的文件格式
				fileSuffix = getSuffix(suffix);
				if (fileSuffix == null) {
					//文件格式不存在
					return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "文件格式不支持",new HashMap<>());
				}
				input = file.getInputStream();

				boolean isOriginalImage = false;
				if(originalImage == 1){
					isOriginalImage = true;
				}
				return uploadService.uploadImg(input, fileSuffix,isOriginalImage);
			} else {
				//文件不存在
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传文件不存在",new HashMap<>());
			}
		} catch (IOException e) {
			log.error("UploadFileController uploadFile:", e);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传文件异常",new HashMap<>());
		}
	}


	/**
	 * 上传语音
	 *
	 * @param token    token
	 * @param uid      用户id
	 * @param file     上传文件
	 */
	@RequestMapping(value = "uploadVoice", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> uploadVoice(@RequestParam(value = "token", required = true) String token,
										  @RequestParam(value = "uid", required = true) long uid,
										  @RequestParam(value = "file", required = true) MultipartFile file) {
		log.info("uploadVoice");
		InputStream input = null;
		FILE_SUFFIX fileSuffix = null;//文件格式 后缀扩展名
		try {
			if (file != null) {
				String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1); //上传的文件格式
				fileSuffix = getSuffix(suffix);
				if (fileSuffix == null) {
					//文件格式不存在
					return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "文件格式不支持",new HashMap<>());
				}
				input = file.getInputStream();

				return uploadService.uploadVoice(input, fileSuffix);
			} else {
				//文件不存在
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传文件不存在",new HashMap<>());
			}
		} catch (IOException e) {
			log.error("UploadFileController uploadFile:", e);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传文件异常",new HashMap<>());
		}
	}

	/**
	 * 上传视频
	 *
	 * @param token    token
	 * @param uid      用户id
	 * @param file     上传文件
	 */
	@RequestMapping(value = "uploadVideo", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel<Object> uploadVideo(@RequestParam(value = "token", required = true) String token,
										   @RequestParam(value = "uid", required = true) long uid,
										   @RequestParam(value = "file", required = true) MultipartFile file) {
		log.info("uploadVideo");
		InputStream input = null;
		FILE_SUFFIX fileSuffix = null;//文件格式 后缀扩展名
		try {
			if (file != null) {
				String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1); //上传的文件格式
				fileSuffix = getSuffix(suffix);
				if (fileSuffix == null) {
					//文件格式不存在
					return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "文件格式不支持",new HashMap<>());
				}
				input = file.getInputStream();

				return uploadService.uploadVideo(input, fileSuffix);
			} else {
				//文件不存在
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传文件不存在",new HashMap<>());
			}
		} catch (IOException e) {
			log.error("UploadFileController uploadFile:", e);
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传文件异常",new HashMap<>());
		}
	}

	public FILE_SUFFIX getSuffix(String suffix) {
		FILE_SUFFIX fileSuffix = null;
		FILE_SUFFIX[] fileSuffixs = FILE_SUFFIX.values(); //允许上传的所有文件格式
		for (FILE_SUFFIX suf : fileSuffixs) {
			if (suf.value.equalsIgnoreCase(suffix)) {
				fileSuffix = suf;
				break;
			}
		}
		return fileSuffix;
	}

}

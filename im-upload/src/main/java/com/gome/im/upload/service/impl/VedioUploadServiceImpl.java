package com.gome.im.upload.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.im.upload.model.ResultModel;
import com.gome.im.upload.model.VedioModel;
import com.gome.im.upload.mongodb.VedioDao;
import com.gome.im.upload.service.VedioUploadService;
import com.gome.im.upload.utils.ImageUtil;
import com.gome.im.upload.utils.TFSUploadFileUtil;
import com.gome.im.upload.utils.VideoPrtScr;

public class VedioUploadServiceImpl implements VedioUploadService {
	
	private static Logger log = LoggerFactory.getLogger(VedioUploadServiceImpl.class);
	private static ExecutorService executorService = Executors.newCachedThreadPool();

	@Override
	public ResultModel<Object> uploadVedio(InputStream input, String fileName, long uid, String traceId) {
		int byteSize = 1024; //byte[]大小
		try{
			byteSize = input.available();
		}catch (Exception e){
			log.error("traceId:"+traceId+"; error:" + e.getMessage());
		}
		ResultModel<Object> result = uploadVedioFile(input,fileName,byteSize,uid, traceId);
		return result;
	}
	/**
	 * 上传视频文件
	 * @param input 输入流
	 * @param suffix  文件后缀
	 * @param byteSize  byte[]大小
	 * @return
	 */
	public ResultModel<Object> uploadVedioFile(InputStream input, String fileName, int byteSize,long uid, String traceId){
		final VedioModel vm = new VedioModel();
		String newTfsFileName = null;
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
		try{
			byte[] bytes = getByteArrayBySize(input,byteSize,traceId);
			if(bytes != null){
				newTfsFileName = TFSUploadFileUtil.getFileName();//获取文件名
				if(StringUtils.isNotEmpty(newTfsFileName)){
					log.info("traceId:"+traceId+"; newTfsFileName : "+ newTfsFileName);
					String fileSuffix = "_vedio." + suffix;//文件后缀
					//while(true){
					//上传视频
					String vedioFileName = TFSUploadFileUtil.uploadFile(newTfsFileName,bytes, fileSuffix, traceId);
					if (vedioFileName == null) {
						return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传视频文件异常",new HashMap<>());
					}
					vm.setUid(uid);
					vm.setVedioName(vedioFileName);
					vm.setUploadTime(System.currentTimeMillis());
					executorService.submit(new Runnable() {
						@Override
						public void run() {
							VedioDao vd = new VedioDao();
							vd.addVedioUrl(vm);
						}
					});	
					String vedioUrl = ImageUtil.TFS_URL + vedioFileName;
					log.info("traceId:"+traceId+"; Vedio Test url : "+ vedioUrl);
					//上传截图 (PrtScr--fro short) 之后根据URL视频截图，此处默认为本地数据
					byte[] prtScr =  VideoPrtScr.getVideoPrtScr(fileName,newTfsFileName);
					if(prtScr == null){
						log.info("traceId:"+traceId+"; ERROR Message: get PrtScrFileName is null!");
						return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传视频文件异常",new HashMap<>());
					}
					fileSuffix = "_img.jpg";//文件后缀
					String PrtScrFileName = TFSUploadFileUtil.uploadFile(newTfsFileName,prtScr, fileSuffix, traceId);
					if (PrtScrFileName == null) {
						/*log.info("traceId:"+traceId+"; ERROR Message: Upload PrtScr failed, tfmanager.saveFile return null!");
						return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传视频文件异常",new HashMap<>());*/
						/*由于视频文件太小或截图原因造成失败而添加的默认图片*/
						PrtScrFileName = "T1ZRJTByCT1RXmQRz6_Small.jpg";
					}
					log.info("traceId:"+traceId+"; Vedio_PrtScr Test url : "+ImageUtil.TFS_URL + PrtScrFileName);
					vm.setUid(uid);
					vm.setVedioName(PrtScrFileName);
					vm.setUploadTime(System.currentTimeMillis());
					removeVedioAndPrtscrFile(fileName,PrtScrFileName,traceId);//删除临时文件				
					
					//测试后删除，恢复下面注释的代码（1行）
					/*Thread.currentThread().sleep(10000);
					}*/
				}else{
				log.info("traceId:"+traceId+"; ERROR Message:TFSUploadFileUtil.getFileName() return null!");
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传视频文件异常",new HashMap<>());
				}
			}else{
				log.info("traceId:"+traceId+"; ERROR Message: Vedio bytes[] is null!");
				return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传视频文件异常",new HashMap<>());
			}
			
		}catch(Exception e){
			log.error("traceId:"+traceId+"; "+e.getMessage());
			return new ResultModel<Object>(ResultModel.RESULT_FAILURE, "上传视频文件异常",new HashMap<>());			
		}
		return new ResultModel<Object>(ResultModel.RESULT_OK, "上传视频文件成功",vm);
	}
	/**
	 *  删除临时文件，视频及截图
	 * @param 视频名称fileName,截图名称PrtScrFileName
	 * @return
	 */
	public void removeVedioAndPrtscrFile(String fileName, String PrtScrFileName, String traceId){
//		String path = "C:\\ffmpeg\\input\\";		//windows 路径
		String path = "/data/im-upload/";			//linux 路径	
		File f = new File(path+PrtScrFileName);
		if(f.exists()){
			f.delete();
		}		
		File vedioFile = new File(path+fileName);
		if(vedioFile.exists()){
			vedioFile.delete();
		}
		log.info("traceId:"+traceId+"; 视频名称："+fileName+"；截图名称："+PrtScrFileName+"，已删除临时文件。");
	}
	
	/**
	 *  将输入流转为 byte[]
	 * @param input 输入流
	 * @param byteSize  byte[]大小
	 * @return
	 */
	public byte[] getByteArrayBySize(InputStream input,int byteSize,String traceId){
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

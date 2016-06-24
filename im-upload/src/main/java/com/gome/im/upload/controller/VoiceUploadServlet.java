package com.gome.im.upload.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gome.im.upload.global.Constant.FILE_SUFFIX;
import com.gome.im.upload.model.ResultModel;
import com.gome.im.upload.service.impl.VoiceUploadServiceImpl;
import com.gome.im.upload.utils.Md5Util;

@WebServlet(name="VoiceUploadServlet",urlPatterns="/VoiceUploadServlet")
@MultipartConfig
public class VoiceUploadServlet extends HttpServlet {
	
	private static Logger log = LoggerFactory.getLogger(VoiceUploadServlet.class);
	 /**
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("utf-8");
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("application/json;charset=utf-8");
		 
		 ResultModel<Object> rm = new ResultModel<Object>();
		 String traceId = request.getParameter("traceId");
		 String key = request.getParameter("key");
		 long uid  = Long.parseLong(request.getParameter("uid"));
		 long time = Long.parseLong(request.getParameter("currentTime"));
		 String appId = request.getParameter("appId");
		 log.info("Receive Message: appId:"+appId+", uid:"+uid+", time:"+time+", key:"+key+", traceId:"+traceId);
		 try{
			 String autMd5 = Md5Util.md5Encode(appId+uid+time);
			 if(key.equals(autMd5)){			//判断Md5加密
				 //long nowTime = System.currentTimeMillis();
	             //if(nowTime-time <= 180000){  	//小于等于3分钟，3*60*1000
					 Part part = request.getPart("file");
					 String header = part.getHeader("content-disposition");
					 String fileName = getFileName(header);			 
					 String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
					 //FILE_SUFFIX suffix_F = getSuffix(suffix);
					 //if(suffix_F != null) {		//判断文件格式[暂时不判断]
						 InputStream input = part.getInputStream();
						 VoiceUploadServiceImpl voiceUploadService = new VoiceUploadServiceImpl();
						 log.info("traceId:"+traceId+"; sourceFileName:"+fileName+"; fileSize:"+input.available());
						 rm = voiceUploadService.uploadVoice(input, suffix, uid, traceId);						
//						}else{
//							rm.setCode(1);
//							rm.setMessage("File format Exception!");
//						}
	             /*}else{
	            	 rm.setCode(1);
	            	 rm.setMessage("Time Param Error!");
	            	 log.error("traceId:"+traceId+"; Error message: Time Param Error!");
	             }*/
			 }else{
				 rm.setCode(1);
				 rm.setMessage("Key/Md5 ERROR!");
				 log.error("traceId:"+traceId+"; Error message: Key/Md5 Error!");
			 }
		 }catch(IOException e){
			 rm.setCode(1);
			 rm.setMessage("IO Exception!");
			 log.error("traceId:"+traceId+"; Error message: "+e.getMessage());
		 } catch (Exception e) {
			e.printStackTrace();
		}             
         PrintWriter out = response.getWriter();
         out.write(JSON.toJSONString(rm));
         log.info("traceId:"+traceId+"; "+JSON.toJSONString(rm));       
         out.flush();
         out.close();
     }
 
      /**      
      * @param header 请求头
      * @return 文件名
      */
     public String getFileName(String header) {        
         String[] tempArr1 = header.split(";");
         String[] tempArr2 = tempArr1[2].split("=");
         //获取文件名，兼容各种浏览器的写法
         String fileName = tempArr2[1].substring(tempArr2[1].lastIndexOf("\\")+1).replaceAll("\"", "");
         return fileName;
     }
     
     public void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         this.doGet(request, response);
     }
     
     /**
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
 	 *  将String后缀转为 FILE_SUFFIX
 	 * @param suffix 后缀
 	 * @return
 	 */
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

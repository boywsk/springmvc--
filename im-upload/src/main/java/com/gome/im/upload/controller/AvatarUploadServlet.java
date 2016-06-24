package com.gome.im.upload.controller;

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
import com.gome.im.upload.service.impl.AvatarUploadServiceImpl;
import com.gome.im.upload.utils.Md5Util;

@WebServlet(name="AvatarUploadServlet",urlPatterns="/AvatarUploadServlet")
@MultipartConfig
public class AvatarUploadServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4244196681488479461L;
	private static Logger log = LoggerFactory.getLogger(AvatarUploadServlet.class);

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		
		ResultModel<Object> rm = new ResultModel<Object>();
		 String traceId = request.getParameter("traceId");
		 String key = request.getParameter("key");
		 long uid  = Long.parseLong(request.getParameter("uid"));
		 long currentTime = Long.parseLong(request.getParameter("currentTime"));
		 String appId = request.getParameter("appId");
		 log.info("Receive Message: appId:"+appId+", uid:"+uid+", currentTime:"+currentTime+", key:"+key+", traceId:"+traceId);
		 try{
			 String autMd5 = Md5Util.md5Encode(appId+uid+currentTime);
			 if(key.equals(autMd5)){			//判断Md5加密
				 //long nowTime = System.currentTimeMillis();
	             //if(nowTime-currentTime <= 180000){  	//小于等于3分钟，3*60*1000
					 Part part = request.getPart("file");
					 String header = part.getHeader("content-disposition");
					 String fileName = getFileName(header);			 
					 String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
					 FILE_SUFFIX suffix_F = getSuffix(suffix);
					 if(suffix_F != null) {		//判断文件格式
						 InputStream input = part.getInputStream();
						 AvatarUploadServiceImpl avataruploadService = new AvatarUploadServiceImpl();
						 log.info("traceId:"+traceId+"; sourceFileName:"+fileName+"; fileSize:"+input.available());
						 rm = avataruploadService.uploadAvatar(input, suffix_F, uid,traceId);						
						}else{
							rm.setCode(1);
							rm.setMessage("File format Exception!");
							log.error("traceId:"+traceId+"; File format Exception!");
						}
	             /*}else{
	            	 rm.setCode(1);
	            	 rm.setMessage("Time Param Error!");
	            	 log.error("traceId:"+traceId+"; Time Param Error!");
	             }*/
			 }else{
				 rm.setCode(1);
				 rm.setMessage("Key/Md5 ERROR!");
				 log.error("traceId:"+traceId+"; Key/Md5 ERROR!");
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

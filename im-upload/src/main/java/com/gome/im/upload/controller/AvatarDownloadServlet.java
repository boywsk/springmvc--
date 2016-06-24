package com.gome.im.upload.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gome.im.upload.model.ResultModel;
import com.gome.im.upload.service.impl.AvatarDownloadServiceImpl;

@WebServlet(name="AvatarDownloadServlet",urlPatterns="/AvatarDownloadServlet")

public class AvatarDownloadServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2197815483051601948L;
	private static Logger log = LoggerFactory.getLogger(AvatarDownloadServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		ResultModel<Object> rm = new ResultModel<Object>();
		long uid  = Long.parseLong(request.getParameter("uid"));
//		String traceId = request.getParameter("traceId");
//		String key = request.getParameter("key");
//		long currentTime = Long.parseLong(request.getParameter("currentTime"));
//		String appId = request.getParameter("appId");
//		log.info("Receive Message: appId:"+appId+", uid:"+uid+", currentTime:"+currentTime+", key:"+key+", traceId:"+traceId);
		try{
//			 String autMd5 = Md5Util.md5Encode(appId+uid+currentTime);
//			 if(key.equals(autMd5)){			//判断Md5加密
//				 long nowTime = System.currentTimeMillis();
//	             if(nowTime-currentTime <= 180000){  	//小于等于3分钟，3*60*1000
	            	AvatarDownloadServiceImpl avatarDownloadService = new AvatarDownloadServiceImpl();
	         		String avatarUrl = avatarDownloadService.getAvatarUrl(uid);
	         		log.info("uid:"+uid+"; download avatar url: "+avatarUrl);
	                response.sendRedirect(avatarUrl);					
//	             }else{
//	            	 rm.setCode(1);
//	            	 rm.setMessage("Time Param Error!");
//	            	 log.error("Time Param Error!");
//	             }
//			 }else{
//				 rm.setCode(1);
//				 rm.setMessage("Key/Md5 ERROR!");
//				 log.error("Key/Md5 ERROR!");
//			 }
		 }catch(IOException e){
			 rm.setCode(1);
			 rm.setMessage("IO Exception!");
			 log.error("Error message: "+e.getMessage());
		 } catch (Exception e) {
			e.printStackTrace();
		}              
        PrintWriter out = response.getWriter();         
        out.write(JSON.toJSONString(rm));
        out.flush();
        out.close();
    }
}

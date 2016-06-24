package com.gome.im.upload.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VideoPrtScr {
	private static Logger log = LoggerFactory.getLogger(VideoPrtScr.class);
	
	public static byte[] getVideoPrtScr(String urlVideoFileName, String psName){
		String thumbFilename = psName+"_img.jpg";
		try
        	{
			URL path = Thread.currentThread().getContextClassLoader().getResource("");
			String pathStr = path.toString().replace("%20", " ");
			pathStr = pathStr.substring(5, pathStr.length());
			/*用于Window系统下测试，可保留*/
			/*pathStr = "c:/ffmpeg/";
			 new ProcessBuilder(pathStr+"ffmpeg.exe", "-y",
                "-i", "c:/ffmpeg/input/"+urlVideoFileName, "-vframes", "1", "-ss", 0 + ":" + 0
                        + ":" + 1, "-f", "mjpeg", "-s", 180 + "*" + 180,
                "-an", "C:\\ffmpeg\\input\\"+thumbFilename).start().waitFor();*/
			ProcessBuilder pb = new ProcessBuilder(pathStr+"ffmpeg", "-y",
	                "-i", "/data/im-upload/"+urlVideoFileName, "-vframes", "1", "-ss", 0 + ":" + 0
	                        + ":" + 1, "-f", "mjpeg", "-s", 180 + "*" + 180,
	                "-an", "/data/im-upload/"+thumbFilename);
			Process process = pb.start();
			process.waitFor();
			
			log.info("源文件名："+urlVideoFileName+", 生成截图图片名："+thumbFilename+", 视频路径："+"/data/im-upload/"+urlVideoFileName);
			
//            BufferedInputStream in = new BufferedInputStream(new FileInputStream("c:/ffmpeg/input/"+thumbFilename));
			BufferedInputStream in = new BufferedInputStream(new FileInputStream("/data/im-upload/"+thumbFilename));
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);    
           
            byte[] temp = new byte[1024];        
            int size = 0;        
            while ((size = in.read(temp)) != -1) {        
                out.write(temp, 0, size);        
            }        
            in.close();  
            byte[] content = out.toByteArray(); 
            return content;
        } catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
	}
}

package com.gome.im.upload.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gome.im.upload.service.impl.AvatarDownloadServiceImpl;
@WebServlet(name="ImageDownloadServlet",urlPatterns="/ImageDownloadServlet")
public class ImageDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		long uid  = Long.parseLong(request.getParameter("uid"));
		try { 
		AvatarDownloadServiceImpl avatarDownloadService = new AvatarDownloadServiceImpl();
		String avatarUrl = avatarDownloadService.getAvatarUrl(uid);
        response.sendRedirect(avatarUrl);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}

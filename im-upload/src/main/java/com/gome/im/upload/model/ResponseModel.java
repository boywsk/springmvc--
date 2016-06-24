package com.gome.im.upload.model;

public class ResponseModel {
	private String imgSmallName;
	private long uploadTime;
	private int width;
	private int height;

	public long getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(long uploadTime) {
		this.uploadTime = uploadTime;
	}

	public float getWidth() {
		return width;
	}

	public String getImgSmallName() {
		return imgSmallName;
	}

	public void setImgSmallName(String imgSmallName) {
		this.imgSmallName = imgSmallName;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
}

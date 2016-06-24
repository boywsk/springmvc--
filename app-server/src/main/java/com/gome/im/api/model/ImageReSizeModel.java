package com.gome.im.api.model;

/**
 * Created by wangshikai on 2016/3/22.
 */
public class ImageReSizeModel {
    private byte[] data;
    private int width;
    private int height;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

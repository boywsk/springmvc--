package com.gome.im.platform.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangshikai on 2016/3/18.
 */
public class RspAddress implements Serializable{
    private List<String> fileServerList;
    private List<String> imServerList;
    private List<String> apiServerList;
    private long time;

    public List<String> getFileServerList() {
        return fileServerList;
    }

    public void setFileServerList(List<String> fileServerList) {
        this.fileServerList = fileServerList;
    }

    public List<String> getImServerList() {
        return imServerList;
    }

    public void setImServerList(List<String> imServerList) {
        this.imServerList = imServerList;
    }

    public List<String> getApiServerList() {
        return apiServerList;
    }

    public void setApiServerList(List<String> apiServerList) {
        this.apiServerList = apiServerList;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

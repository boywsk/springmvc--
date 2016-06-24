package com.gome.im.platform.model;

import java.io.Serializable;

/**
 * Created by wangshikai on 2016/4/6.
 */
public class GateKeeperRsp implements Serializable{
    //"cpuRate":11.34,"id":0,"inConnNum":2,"isCheck":0,"memRate":14.37,"outConnNum":5,"reportTime":0,
    // "serverIp":"10.125.3.21","serverPort":9000,"serverType":1,"status":1
    private int id;
    private float cpuRate;
    private int inConnNum;
    private int isCheck;
    private float memRate;
    private int outConnNum;
    private int reportTime;
    private String serverIp;
    private int serverPort;
    private int serverType;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCpuRate() {
        return cpuRate;
    }

    public void setCpuRate(float cpuRate) {
        this.cpuRate = cpuRate;
    }

    public int getInConnNum() {
        return inConnNum;
    }

    public void setInConnNum(int inConnNum) {
        this.inConnNum = inConnNum;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public float getMemRate() {
        return memRate;
    }

    public void setMemRate(float memRate) {
        this.memRate = memRate;
    }

    public int getOutConnNum() {
        return outConnNum;
    }

    public void setOutConnNum(int outConnNum) {
        this.outConnNum = outConnNum;
    }

    public int getReportTime() {
        return reportTime;
    }

    public void setReportTime(int reportTime) {
        this.reportTime = reportTime;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getServerType() {
        return serverType;
    }

    public void setServerType(int serverType) {
        this.serverType = serverType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

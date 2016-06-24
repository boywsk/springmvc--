package cn.com.gome.manage.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import cn.com.gome.manage.utils.IPUtils;

public class Server implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String serverIp;
	private int serverPort;
	private String serverKey;
	private int serverType;
	private String serverTypeName;
	private long inConnNum;
	private long outConnNum;
	private double cpuRate;
	private double memRate;
	private int status;
	private String statusDesc;
	private int isCheck;
	private long reportTime;
	private String formateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getServerKey() {
		serverKey = IPUtils.ipToLong(serverIp) + ":" +serverPort;
		return serverKey;
	}

	public void setServerKey(String serverKey) {
		this.serverKey = serverKey;
	}

	public int getServerType() {
		return serverType;
	}

	public void setServerType(int serverType) {
		this.serverType = serverType;
	}

	public String getServerTypeName() {
		switch(serverType) {
			case 1 :
				serverTypeName = "接入服务";
				break;
			case 2 :
				serverTypeName = "逻辑服务";
				break;
			case 3 :
				serverTypeName = "API服务";
				break;
			case 4 :
				serverTypeName = "文件服务";
				break;
			 default :
				 serverTypeName = "未定义";
		}
		return serverTypeName;
	}

	public void setServerTypeName(String serverTypeName) {
		this.serverTypeName = serverTypeName;
	}

	public long getInConnNum() {
		return inConnNum;
	}

	public void setInConnNum(long inConnNum) {
		this.inConnNum = inConnNum;
	}

	public long getOutConnNum() {
		return outConnNum;
	}

	public void setOutConnNum(long outConnNum) {
		this.outConnNum = outConnNum;
	}

	public double getCpuRate() {
		return cpuRate;
	}

	public void setCpuRate(double cpuRate) {
		this.cpuRate = cpuRate;
	}

	public double getMemRate() {
		return memRate;
	}

	public void setMemRate(double memRate) {
		this.memRate = memRate;
	}

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getStatusDesc() {
		switch (status) {
			case 0:
				statusDesc = "停止";
				break;
			case 1:
				statusDesc = "运行";
				break;
			default:
				statusDesc = "停止";
				break;
		}
		
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public long getReportTime() {
		return reportTime;
	}

	public void setReportTime(long reportTime) {
		this.reportTime = reportTime;
	}
	
	public void setFormateTime(String formateTime) {
		this.formateTime = formateTime;
	}

	public String getFormateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formateTime = formatter.format(new Date(reportTime));
		return formateTime;
	}
}

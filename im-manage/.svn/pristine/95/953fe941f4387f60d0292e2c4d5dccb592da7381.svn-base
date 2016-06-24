package cn.com.gome.manage.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 服务器资源
 */
public class ServerResource implements Serializable {
	private static final long serialVersionUID = 1L;

	private String serverIp;
	private int serverPort;
	private double cpuRate;
	private double memRate;
	private long time;
	private String formateTime;

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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setFormateTime(String formateTime) {
		this.formateTime = formateTime;
	}

	public String getFormateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formateTime = formatter.format(new Date(time));
		return formateTime;
	}
}

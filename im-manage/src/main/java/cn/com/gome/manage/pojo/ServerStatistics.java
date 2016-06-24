package cn.com.gome.manage.pojo;

import java.io.Serializable;

/**
 * 服务统计
 */
public class ServerStatistics implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private int serverType;
	private String serverKey;
	private long avgInConn;
	private long maxInConn;
	private long minInConn;
	private long avgOutConn;
	private long maxOutConn;
	private long minOutConn;
	private double avgCpuRate;
	private double maxCpuRate;
	private double minCpuRate;
	private double avgMemRate;
	private double maxMemRate;
	private double minMemRate;
	private long maxReportTime;
	private long statisticsTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getServerType() {
		return serverType;
	}

	public void setServerType(int serverType) {
		this.serverType = serverType;
	}

	public String getServerKey() {
		return serverKey;
	}

	public void setServerKey(String serverKey) {
		this.serverKey = serverKey;
	}

	public long getAvgInConn() {
		return avgInConn;
	}

	public void setAvgInConn(long avgInConn) {
		this.avgInConn = avgInConn;
	}

	public long getMaxInConn() {
		return maxInConn;
	}

	public void setMaxInConn(long maxInConn) {
		this.maxInConn = maxInConn;
	}

	public long getMinInConn() {
		return minInConn;
	}

	public void setMinInConn(long minInConn) {
		this.minInConn = minInConn;
	}

	public long getAvgOutConn() {
		return avgOutConn;
	}

	public void setAvgOutConn(long avgOutConn) {
		this.avgOutConn = avgOutConn;
	}

	public long getMaxOutConn() {
		return maxOutConn;
	}

	public void setMaxOutConn(long maxOutConn) {
		this.maxOutConn = maxOutConn;
	}

	public long getMinOutConn() {
		return minOutConn;
	}

	public void setMinOutConn(long minOutConn) {
		this.minOutConn = minOutConn;
	}

	public double getAvgCpuRate() {
		return avgCpuRate;
	}

	public void setAvgCpuRate(double avgCpuRate) {
		this.avgCpuRate = avgCpuRate;
	}

	public double getMaxCpuRate() {
		return maxCpuRate;
	}

	public void setMaxCpuRate(double maxCpuRate) {
		this.maxCpuRate = maxCpuRate;
	}

	public double getMinCpuRate() {
		return minCpuRate;
	}

	public void setMinCpuRate(double minCpuRate) {
		this.minCpuRate = minCpuRate;
	}

	public double getAvgMemRate() {
		return avgMemRate;
	}

	public void setAvgMemRate(double avgMemRate) {
		this.avgMemRate = avgMemRate;
	}

	public double getMaxMemRate() {
		return maxMemRate;
	}

	public void setMaxMemRate(double maxMemRate) {
		this.maxMemRate = maxMemRate;
	}

	public double getMinMemRate() {
		return minMemRate;
	}

	public void setMinMemRate(double minMemRate) {
		this.minMemRate = minMemRate;
	}

	public long getMaxReportTime() {
		return maxReportTime;
	}

	public void setMaxReportTime(long maxReportTime) {
		this.maxReportTime = maxReportTime;
	}

	public long getStatisticsTime() {
		return statisticsTime;
	}

	public void setStatisticsTime(long statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

}

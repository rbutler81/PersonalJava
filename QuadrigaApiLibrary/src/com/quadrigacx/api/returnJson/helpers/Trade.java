package com.quadrigacx.api.returnJson.helpers;

public class Trade {

	private String major;
	private String minor;
	private String majorMinor;
	private String orderId;
	private String fee;
	private String rate;
	private String date;
	private String id;
	
	public Trade() {
		this.major = "";
		this.minor = "";
		this.majorMinor = "";
		this.orderId = "";
		this.fee = "";
		this.rate = "";
		this.date = "";
		this.id = "";
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}

	public String getMajorMinor() {
		return majorMinor;
	}

	public void setMajorMinor(String majorMinor) {
		this.majorMinor = majorMinor;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
		
}

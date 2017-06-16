package com.kraken.api.json;

public class SpreadData {

	private long time;
	private String bid;
	private String ask;
	
	public SpreadData(long time, String bid, String ask) {
		this.time = time;
		this.bid = bid;
		this.ask = ask;
	}
	
	public SpreadData() {
		this.time = 0;
		this.bid = "";
		this.ask = "";
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getAsk() {
		return ask;
	}

	public void setAsk(String ask) {
		this.ask = ask;
	}
	
	
	
}

package com.quadrigacx.api.returnJson;

public class TickerResponse {

	private String high = "";
	private String last = "";
	private String timeStamp = "";
	private String volume = "";
	private String vwap = "";
	private String low = "";
	private String ask = "";
	private String bid = "";
	
	public TickerResponse(){
		this.high = "";
		this.last = "";
		this.timeStamp = "";
		this.volume = "";
		this.vwap = "";
		this.low = "";
		this.ask = "";
		this.bid = "";		
	}

	public TickerResponse(String high, String last, String timeStamp, String volume, String vwap, String low, String ask, String bid) {
		this.high = high;
		this.last = last;
		this.timeStamp = timeStamp;
		this.volume = volume;
		this.vwap = vwap;
		this.low = low;
		this.ask = ask;
		this.bid = bid;
	}
	
	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getVwap() {
		return vwap;
	}

	public void setVwap(String vwap) {
		this.vwap = vwap;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getAsk() {
		return ask;
	}

	public void setAsk(String ask) {
		this.ask = ask;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}
	
}

package com.kraken.api.json;

public class OHLCData {

	private long time;
	private String open;
	private String high;
	private String low;
	private String close;
	private String vwap;
	private String volume;
	private long count;
	
	public OHLCData(){
		this.time = 0;
		this.open = "";
		this.high = "";
		this.low = "";
		this.close = "";
		this.vwap = "";
		this.volume = "";
		this.count = 0;
	}
	
	public OHLCData(long time, String open, String high, String low, String close, String vwap, String volume, long count){
		this.time = time;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.vwap = vwap;
		this.volume = volume;
		this.count = count;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getVwap() {
		return vwap;
	}

	public void setVwap(String vwap) {
		this.vwap = vwap;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
	
	
	
}

package com.kraken.api.json;

public class AskBidEntry {

	private String price;
	private String volume;
	private long time;
	
	public AskBidEntry(){
		this.price = "";
		this.volume = "";
		this.time = 0;
	}
	
	public AskBidEntry(String price, String volume, long time){
		this.price = price;
		this.volume = volume;
		this.time = time;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	
}

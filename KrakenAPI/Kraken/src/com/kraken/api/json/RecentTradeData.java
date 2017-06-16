package com.kraken.api.json;

public class RecentTradeData {

	private String price;
	private String volume;
	private double time;
	private String buySell;
	private String marketLimit;
	private String misc;
	
	public RecentTradeData(String price, String volume, double time, String buySell, String marketLimit, String misc) {
		this.price = price;
		this.volume = volume;
		this.time = time;
		this.buySell = buySell;
		this.marketLimit = marketLimit;
		this.misc = misc;
	}
	
	public RecentTradeData(){
		this.price = "";
		this.volume = "";
		this.time = 0.0;
		this.buySell = "";
		this.marketLimit = "";
		this.misc = "";
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

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public String getBuySell() {
		return buySell;
	}

	public void setBuySell(String buySell) {
		this.buySell = buySell;
	}

	public String getMarketLimit() {
		return marketLimit;
	}

	public void setMarketLimit(String marketLimit) {
		this.marketLimit = marketLimit;
	}

	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}
	
	
	
	
}

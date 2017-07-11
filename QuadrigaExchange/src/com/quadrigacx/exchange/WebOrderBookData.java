package com.quadrigacx.exchange;

import java.util.ArrayList;
import java.util.List;

import helpers.econ.currency.PriceAmount;

public class WebOrderBookData {

	private List<PriceAmount> asks = new ArrayList<PriceAmount>();
	private List<PriceAmount> bids = new ArrayList<PriceAmount>();
	private String lastTradePrice = "";
	private String lastTradeTime = "";
	
	public WebOrderBookData(List<PriceAmount> asks, List<PriceAmount> bids, String lastTradePrice, String lastTradeTime){
		
		this.asks = asks;
		this.bids = bids;
		this.lastTradePrice = lastTradePrice;
		this.lastTradeTime = lastTradeTime;
	}

	public List<PriceAmount> getAsks() {
		return asks;
	}

	public List<PriceAmount> getBids() {
		return bids;
	}

	public String getLastTradePrice() {
		return lastTradePrice;
	}

	public String getLastTradeTime() {
		return lastTradeTime;
	}
	
	
}

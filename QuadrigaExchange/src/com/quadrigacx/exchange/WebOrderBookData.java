package com.quadrigacx.exchange;

import java.util.ArrayList;
import java.util.List;

import helpers.econ.currency.PriceAmount;

public class WebOrderBookData {

	private List<PriceAmount> asks = new ArrayList<PriceAmount>();
	private List<PriceAmount> bids = new ArrayList<PriceAmount>();
	private String lastTradePrice = "";
	
	public WebOrderBookData(List<PriceAmount> asks, List<PriceAmount> bids, String lastTradePrice){
		
		this.asks = asks;
		this.bids = bids;
		this.lastTradePrice = lastTradePrice;
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
	
	
}

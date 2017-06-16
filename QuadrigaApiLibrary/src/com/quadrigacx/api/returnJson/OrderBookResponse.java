package com.quadrigacx.api.returnJson;

import java.util.ArrayList;
import java.util.List;

import com.quadrigacx.api.returnJson.helpers.BidAsk;

public class OrderBookResponse {
	
	private List<BidAsk> bids;
	private List<BidAsk> asks;
	private String timeStamp;
	
	public OrderBookResponse(){
		this.bids = new ArrayList<BidAsk>();
		this.asks = new ArrayList<BidAsk>();
		this.timeStamp = "";
	}

	public OrderBookResponse(List<BidAsk> bids, List<BidAsk> asks, String timeStamp) {
		this.bids = bids;
		this.asks = asks;
		this.timeStamp = timeStamp;
	}
	
	public List<BidAsk> getBids() {
		return bids;
	}

	public void setBids(List<BidAsk> bids) {
		this.bids = bids;
	}

	public List<BidAsk> getAsks() {
		return asks;
	}

	public void setAsks(List<BidAsk> asks) {
		this.asks = asks;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}

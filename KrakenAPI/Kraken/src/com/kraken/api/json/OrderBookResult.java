package com.kraken.api.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using=OrderBookResultDeserializer.class)
public class OrderBookResult {
	
	private String pair;
	private List<AskBidEntry> asks;
	private List<AskBidEntry> bids;
	
	public OrderBookResult(){
		this.asks = new ArrayList<AskBidEntry>();
		this.bids = new ArrayList<AskBidEntry>();
		this.pair = "";
	}
	
	public OrderBookResult(String pair, List<AskBidEntry> asks, List<AskBidEntry> bids) {
		this.asks = asks;
		this.bids = bids;
		this.pair = pair;
	}

	public List<AskBidEntry> getAsks() {
		return asks;
	}

	public void setAsks(List<AskBidEntry> asks) {
		this.asks = asks;
	}

	public List<AskBidEntry> getBids() {
		return bids;
	}

	public void setBids(List<AskBidEntry> bids) {
		this.bids = bids;
	}

	public String getPair() {
		return pair;
	}

	public void setPair(String pair) {
		this.pair = pair;
	}

}

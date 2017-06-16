package com.kraken.api.json;

import java.util.ArrayList;
import java.util.List;

public class AskBidArray {
	
	private List<ArrayList<Object>> asks;
	private List<ArrayList<Object>> bids;
	
	public AskBidArray(){
		this.asks = new ArrayList<ArrayList<Object>>();
		this.bids = new ArrayList<ArrayList<Object>>();
	}
	
	public AskBidArray(List<ArrayList<Object>> asks, List<ArrayList<Object>> bids){
		this.asks = asks;
		this.bids = bids;
	}
	
	public List<ArrayList<Object>> getAsks(){
		return asks;
	}
	
	public List<ArrayList<Object>> getBids(){
		return bids;
	}
	
}

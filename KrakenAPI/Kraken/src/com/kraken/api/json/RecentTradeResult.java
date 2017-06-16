package com.kraken.api.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using=RecentTradeResultDeserializer.class)
public class RecentTradeResult {

	private String last;
	private List<RecentTradeData> data;
	private String pair;
	
	public RecentTradeResult() {
		this.last = "";
		this.pair = "";
		this.data = new ArrayList<RecentTradeData>();
	}
	
	public RecentTradeResult(String last, List<RecentTradeData> data, String pair) {
		this.last = last;
		this.pair = pair;
		this.data = data;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public List<RecentTradeData> getData() {
		return data;
	}

	public void setData(List<RecentTradeData> data) {
		this.data = data;
	}

	public String getPair() {
		return pair;
	}

	public void setPair(String pair) {
		this.pair = pair;
	}
	
	
	
	
	
}

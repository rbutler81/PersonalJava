package com.kraken.api.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using=SpreadDataResultDeserializer.class)
public class SpreadDataResult {

	private long last;
	private List<SpreadData> data;
	private String pair;
	
	public SpreadDataResult() {
		this.last = 0;
		this.data = new ArrayList<SpreadData>();
		this.pair = "";
	}
	
	public SpreadDataResult(long last, List<SpreadData> data, String pair) {
		this.last = last;
		this.data = data;
		this.pair = pair;
	}

	public long getLast() {
		return last;
	}

	public void setLast(long last) {
		this.last = last;
	}

	public List<SpreadData> getData() {
		return data;
	}

	public void setData(List<SpreadData> data) {
		this.data = data;
	}

	public String getPair() {
		return pair;
	}

	public void setPair(String pair) {
		this.pair = pair;
	}
	
	
	
	
	
}

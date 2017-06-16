package com.kraken.api.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using=OHLCResultDeserializer.class)
public class OHLCResult {

	private long last;
	private List<OHLCData> data;
	private String name;
	
	public OHLCResult(){
		this.last = 0;
		this.data = new ArrayList<OHLCData>();
		this.name = "";
	}
	
	public OHLCResult(long last, List<OHLCData> data, String name){
		this.last = last;
		this.data = data;
		this.name = name;
	}
	
	public long getLast(){
		return last;
	}
	
	public List<OHLCData> getData(){
		return data;
	}
	
	public String getName(){
		return name;
	}
	

}
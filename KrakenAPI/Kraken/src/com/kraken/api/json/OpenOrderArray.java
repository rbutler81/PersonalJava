package com.kraken.api.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using=OpenOrderArrayDeserializer.class)
public class OpenOrderArray {
	
	private Map<String, OpenOrder> open;

	
	public OpenOrderArray() {
		this.open = new HashMap<String, OpenOrder>();
	}
	
	public OpenOrderArray(Map<String, OpenOrder> open) {
		this.open = open;
	}
	
	public Map<String, OpenOrder> getOpen() {
		return open;
	}
	
	
	
	
}

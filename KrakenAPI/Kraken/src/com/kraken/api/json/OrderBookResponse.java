package com.kraken.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderBookResponse extends Response {
	
	private static final String RESULT = "result";
	
	@JsonProperty(RESULT)
	private OrderBookResult result;
	
	public OrderBookResult getResult(){
		return result;
	}

}

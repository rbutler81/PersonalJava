package com.kraken.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenOrderResponse extends Response {
	
	private static final String RESULT = "result";
	
	@JsonProperty(RESULT)
	private OpenOrderArray result;
	
	public OpenOrderArray getResult(){
		return result;
	}
	
	public OpenOrder getOrderDetails(String order){
		return result.getOpen().get(order);
	}

}

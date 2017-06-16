package com.kraken.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecentTradeResponse extends Response {

	private static final String RESULT = "result";
	
	@JsonProperty(RESULT)
	private RecentTradeResult result;
	
	public RecentTradeResult getResult(){
		return result;
	}
	
}

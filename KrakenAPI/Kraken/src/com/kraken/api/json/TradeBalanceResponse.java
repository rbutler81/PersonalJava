package com.kraken.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TradeBalanceResponse extends Response {
	private static final String RESULT = "result";
	
	@JsonProperty(RESULT)
	private TradeBalanceData result;
	
	@JsonProperty(RESULT)
	public TradeBalanceData getResult(){
		return result;
	}
}

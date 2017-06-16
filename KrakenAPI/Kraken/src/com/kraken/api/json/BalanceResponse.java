package com.kraken.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BalanceResponse extends Response {

	private static final String RESULT = "result";
	
	@JsonProperty(RESULT)
	private BalanceResult result;

	public BalanceResult getResult() {
		return result;
	}
	
	
}

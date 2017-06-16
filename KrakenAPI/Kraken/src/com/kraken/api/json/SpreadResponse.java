package com.kraken.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpreadResponse extends Response {

	private static final String RESULT = "result";
	
	@JsonProperty(RESULT)
	private SpreadDataResult result;

	public SpreadDataResult getResult() {
		return result;
	}
	
	
}

package com.kraken.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OHLCResponse extends Response {

	private static final String RESULT = "result";
	
	@JsonProperty(RESULT)
	private OHLCResult result;
	
	public OHLCResult getResult(){
		return result;
	}

}

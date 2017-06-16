package com.kraken.api.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TickersResponse extends Response {
	
	private static final String RESULT = "result";
	
	@JsonProperty(RESULT)
	private Tickers result;
	
	public TickersResponse(){
		super();
		this.result = new Tickers();
	}
	
	public TickersResponse(Tickers tickers){
		super();
		this.result = tickers;
	}
	
	public TickersResponse(List<String> error, Tickers tickers){
		super(error);
		this.result = tickers;
	}
	
	@JsonProperty(RESULT)
	public void setResult(Tickers result){
		this.result = result;
	}
	
	@JsonProperty(RESULT)
	public Tickers getResult(){
		return this.result;
	}

}

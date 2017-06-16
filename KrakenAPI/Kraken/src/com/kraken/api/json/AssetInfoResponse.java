package com.kraken.api.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssetInfoResponse extends Response{

	private static final String RESULT = "result";
	
	@JsonProperty(RESULT)
	private Assets result;
	
	public AssetInfoResponse(){
		super();
		this.result = new Assets();
	}
	
	public AssetInfoResponse(Assets assets){
		super();
		this.result = assets;
	}
	
	public AssetInfoResponse(List<String> error, Assets assets){
		super(error);
		this.result = assets;
	}
	
	@JsonProperty(RESULT)
	public void setResult(Assets result){
		this.result = result;
	}
	
	@JsonProperty(RESULT)
	public Assets getResult(){
		return this.result;
	}
	
	
}

package com.kraken.api.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssetPairsResponse extends Response{

private static final String RESULT = "result";
	
	@JsonProperty(RESULT)
	private AssetPairs result;
	
	public AssetPairsResponse(){
		super();
		this.result = new AssetPairs();
	}
	
	public AssetPairsResponse(AssetPairs assetPairs){
		super();
		this.result = assetPairs;
	}
	
	public AssetPairsResponse(List<String> error, AssetPairs assetPairs){
		super(error);
		this.result = assetPairs;
	}
	
	@JsonProperty(RESULT)
	public void setResult(AssetPairs result){
		this.result = result;
	}
	
	@JsonProperty(RESULT)
	public AssetPairs getResult(){
		return this.result;
	}
}

package com.kraken.api.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using=BalanceResultDeserializer.class)
public class BalanceResult {
	
	private List<AssetBalance> asset;
	
	public BalanceResult(){
		this.asset = new ArrayList<AssetBalance>();
	}
	
	public BalanceResult(List<AssetBalance> asset){
		this.asset = asset;
	}

	public List<AssetBalance> getAsset() {
		return asset;
	}

	public void setAsset(List<AssetBalance> asset) {
		this.asset = asset;
	}
	
}

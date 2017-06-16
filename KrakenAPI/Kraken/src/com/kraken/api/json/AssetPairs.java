package com.kraken.api.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using=AssetPairsDeserializer.class)
public class AssetPairs implements Iterable<AssetPair> {

	private final List<AssetPair> assetPairs;
	
	public AssetPairs(){
		this.assetPairs = new ArrayList<>();
	}
	
	public AssetPairs(List<AssetPair> assetPairs){
		this.assetPairs = assetPairs;
	}
	
	public List<AssetPair> getAssetPairs(){
		return assetPairs;
	}
	
	@Override
	public Iterator<AssetPair> iterator() {
		return assetPairs.iterator();
	}

}

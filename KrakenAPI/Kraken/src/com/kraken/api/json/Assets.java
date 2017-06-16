package com.kraken.api.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using=AssetsDeserializer.class)
public class Assets implements Iterable<Asset> {

	private final List<Asset> assets;
	
	public Assets(List<Asset> assets){
		this.assets = assets;
	}
	
	public Assets(){
		this.assets = new ArrayList<>();
	}
	
	public List<Asset> getAssets(){
		return assets;
	}
	
	@Override
	public Iterator<Asset> iterator() {
		return assets.iterator();
	}

}

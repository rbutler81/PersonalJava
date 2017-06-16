package com.kraken.api.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using=TickersDeserializer.class)
public class Tickers implements Iterable<Ticker>{

	private final List<Ticker> tickers;
	
	public Tickers(List<Ticker> tickers){
		this.tickers = tickers;
	}
	
	public Tickers(){
		this.tickers = new ArrayList<>();
	}
	
	public List<Ticker> getAssets(){
		return tickers;
	}
	
	@Override
	public Iterator<Ticker> iterator() {
		return tickers.iterator();
	}

}

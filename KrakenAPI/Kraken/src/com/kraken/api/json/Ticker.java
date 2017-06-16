package com.kraken.api.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticker {
	
	public static final String A = "a";
	public static final String B = "b";
	public static final String C = "c";
	public static final String V = "v";
	public static final String P = "p";
	public static final String T = "t";
	public static final String L = "l";
	public static final String H = "h";
	public static final String O = "o";
	
	private String name;
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	@JsonProperty(A)
	private List<String> askArray;
	@JsonProperty(B)
	private List<String> bidArray;
	@JsonProperty(C)
	private List<String> lastTradeArray;
	@JsonProperty(V)
	private List<String> volumeArray;
	@JsonProperty(P)
	private List<String> volumeWeightedArray;
	@JsonProperty(T)
	private List<Long> numberOfTradesArray;
	@JsonProperty(L)
	private List<String> lowArray;
	@JsonProperty(H)
	private List<String> highArray;
	@JsonProperty(O)
	private String openingPrice;

	@JsonProperty(A)
	public List<String> getAskArray() {
		return askArray;
	}
	@JsonProperty(A)
	public void setAskArray(List<String> askArray) {
		this.askArray = askArray;
	}
	
	@JsonProperty(B)
	public List<String> getBidArray() {
		return bidArray;
	}
	@JsonProperty(B)
	public void setBidArray(List<String> bidArray) {
		this.bidArray = bidArray;
	}

	@JsonProperty(C)	
	public List<String> getLastTradeArray() {
		return lastTradeArray;
	}
	@JsonProperty(C)
	public void setLastTradeArray(List<String> lastTradeArray) {
		this.lastTradeArray = lastTradeArray;
	}
	
	@JsonProperty(V)
	public List<String> getVolumeArray() {
		return volumeArray;
	}
	@JsonProperty(V)
	public void setVolumeArray(List<String> volumeArray) {
		this.volumeArray = volumeArray;
	}

	@JsonProperty(P)
	public List<String> getVolumeWeightedArray() {
		return volumeWeightedArray;
	}
	@JsonProperty(P)
	public void setVolumeWeightedArray(List<String> volumeWeightedArray) {
		this.volumeWeightedArray = volumeWeightedArray;
	}
	
	@JsonProperty(T)
	public List<Long> getNumberOfTradesArray() {
		return numberOfTradesArray;
	}
	@JsonProperty(T)
	public void setNumberOfTradesArray(List<Long> numberOfTradesArray) {
		this.numberOfTradesArray = numberOfTradesArray;
	}

	@JsonProperty(L)
	public List<String> getLowArray() {
		return lowArray;
	}
	@JsonProperty(L)
	public void setLowArray(List<String> lowArray) {
		this.lowArray = lowArray;
	}
	
	@JsonProperty(H)
	public List<String> getHighArray() {
		return highArray;
	}
	@JsonProperty(H)
	public void setHighArray(List<String> highArray) {
		this.highArray = highArray;
	}
	
	@JsonProperty(O)
	public String getOpeningPrice() {
		return openingPrice;
	}
	@JsonProperty(O)
	public void setOpeningPrice(String openingPrice) {
		this.openingPrice = openingPrice;
	}

	//Get data from array
	public double getAskPrice(){
		return Double.parseDouble(getAskArray().get(0));
	}
	public double getAskWholeLotVolume(){
		return Double.parseDouble(getAskArray().get(1));
	}
	public double getAskLotVolume(){
		return Double.parseDouble(getAskArray().get(2));
	}
	
	public double getBidPrice(){
		return Double.parseDouble(getBidArray().get(0));
	}
	public double getBidWholeLotVolume(){
		return Double.parseDouble(getBidArray().get(1));
	}
	public double getBidLotVolume(){
		return Double.parseDouble(getBidArray().get(2));
	}
	
	public double getLastTradePrice(){
		return Double.parseDouble(getLastTradeArray().get(0));
	}
	public double getLastTradeVolume(){
		return Double.parseDouble(getLastTradeArray().get(1));
	}
	
	public double getVolumeToday(){
		return Double.parseDouble(getVolumeArray().get(0));
	}
	public double getVolumeLast24(){
		return Double.parseDouble(getVolumeArray().get(1));
	}
	
	public double getVWAPToday(){
		return Double.parseDouble(getVolumeWeightedArray().get(0));
	}
	public double getVWAPLast24(){
		return Double.parseDouble(getVolumeWeightedArray().get(1));
	}
	
	public double getNumberOfTradesToday(){
		return getNumberOfTradesArray().get(0);
	}
	public double getNumberOfTradesLast24(){
		return getNumberOfTradesArray().get(1);
	}
	
	public double getLowToday(){
		return Double.parseDouble(getLowArray().get(0));
	}
	public double getLowLast24(){
		return Double.parseDouble(getLowArray().get(1));
	}
	
	public double getHighToday(){
		return Double.parseDouble(getHighArray().get(0));
	}
	public double getHighLast24(){
		return Double.parseDouble(getHighArray().get(1));
	}
	
	public double getTodaysOpen(){
		return Double.parseDouble(getOpeningPrice());
	}
	
}

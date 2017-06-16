package com.kraken.api.json;

public class AssetBalance {
	
	private String asset;
	private String balance;
	
	public AssetBalance() {
		this.asset = "";
		this.balance = "";
	}
	
	public AssetBalance(String asset, String balance) {
		this.asset = asset;
		this.balance = balance;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	
	
}

package com.quadrigacx.api.returnJson;

import com.quadrigacx.api.returnJson.helpers.Balance;

public class BalanceResponse {

	private Balance cad;
	private Balance usd;
	private Balance eth;
	private Balance btc;
	
	public BalanceResponse(){
		this.cad = new Balance();
		this.usd = new Balance();
		this.eth = new Balance();
		this.btc = new Balance();
	}

	public Balance getCad() {
		return cad;
	}

	public void setCad(Balance cad) {
		this.cad = cad;
	}

	public Balance getUsd() {
		return usd;
	}

	public void setUsd(Balance usd) {
		this.usd = usd;
	}

	public Balance getEth() {
		return eth;
	}

	public void setEth(Balance eth) {
		this.eth = eth;
	}

	public Balance getBtc() {
		return btc;
	}

	public void setBtc(Balance btc) {
		this.btc = btc;
	}
	
	
}

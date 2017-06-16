package com.quadrigacx.exchange;

import com.quadrigacx.api.QuadrigaMethods;
import com.quadrigacx.api.returnJson.ResponseWrapper;
import com.quadrigacx.api.returnJson.helpers.Balance;
import com.quadrigacx.api.returnJson.helpers.GetCoinType;

import helpers.RateLimiter;
import helpers.econ.currency.CoinType;

public class Balances extends QuadrigaCall{

	public Balances(){
		this.r = new ResponseWrapper(); 
	}
	
	public Balances(RateLimiter rl){
		super(rl);
		qm = QuadrigaMethods.Balance;
		refreshData();
	}
	
	public Balance getMajor(String book){
		
		CoinType major = GetCoinType.getMajor(book);
		
		if (major == CoinType.CAD) return r.getBalanceResponse().getCad();
		else if (major == CoinType.USD) return r.getBalanceResponse().getUsd();
		else if (major == CoinType.ETH) return r.getBalanceResponse().getEth();
		else return r.getBalanceResponse().getBtc();
		
	}
	
	public Balance getMinor(String book){
		
		CoinType minor = GetCoinType.getMinor(book);
		
		if (minor == CoinType.CAD) return r.getBalanceResponse().getCad();
		else if (minor == CoinType.USD) return r.getBalanceResponse().getUsd();
		else if (minor == CoinType.ETH) return r.getBalanceResponse().getEth();
		else return r.getBalanceResponse().getBtc();
		
	}
	
	public Balance getCad(){
		return r.getBalanceResponse().getCad();
	}
	
	public Balance getUsd(){
		return r.getBalanceResponse().getUsd();
	}
	
	public Balance getEth(){
		return r.getBalanceResponse().getEth();
	}
	
	public Balance getBtc(){
		return r.getBalanceResponse().getBtc();
	}
}

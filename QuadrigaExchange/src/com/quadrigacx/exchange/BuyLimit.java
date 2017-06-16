package com.quadrigacx.exchange;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.quadrigacx.api.QuadrigaMethods;
import com.quadrigacx.api.returnJson.ResponseWrapper;
import com.quadrigacx.api.returnJson.helpers.GetCoinType;
import com.quadrigacx.api.returnJson.helpers.OrderResult;

import helpers.KeyValuePair;
import helpers.RateLimiter;
import helpers.econ.currency.CoinType;

public class BuyLimit extends QuadrigaCall{

	public BuyLimit(){
		this.r = new ResponseWrapper(); 
	}
	
	public BuyLimit(RateLimiter rl){
		super(rl);
		qm = QuadrigaMethods.BuyLimitOrder;
	}
	
	public boolean buy(String price, String amountMajor, String book){
		kv.clear();
		kv.add(new KeyValuePair("amount", amountMajor));
		kv.add(new KeyValuePair("price", price));
		kv.add(new KeyValuePair("book", book));
		return refreshData();
	}
	
	public boolean buyNow(String price, String amountMajor, String book){
		kv.clear();
		kv.add(new KeyValuePair("amount", amountMajor));
		kv.add(new KeyValuePair("price", price));
		kv.add(new KeyValuePair("book", book));
		return refreshDataNow();
	}
	
	public boolean buyVolumeInMinor(String price, String amountMinor, String book){
		
		CoinType minor = GetCoinType.getMinor(book);
		CoinType major = GetCoinType.getMajor(book);
		
		BigDecimal priceBD = new BigDecimal(price);
		priceBD = priceBD.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
		
		BigDecimal amountMinorBD = new BigDecimal(amountMinor);
		amountMinorBD = amountMinorBD.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
		
		BigDecimal majorToBuy = amountMinorBD.divide(priceBD, major.getDecimalPlaces(), RoundingMode.DOWN);
		
		kv.clear();
		kv.add(new KeyValuePair("amount", majorToBuy.toString()));
		kv.add(new KeyValuePair("price", price));
		kv.add(new KeyValuePair("book", book));
		
		return refreshData();
		
	}
	
	public boolean buyVolumeInMinorNow(String price, String amountMinor, String book){
		
		CoinType minor = GetCoinType.getMinor(book);
		CoinType major = GetCoinType.getMajor(book);
	
		BigDecimal priceBD = new BigDecimal(price);
		priceBD = priceBD.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
	
		BigDecimal amountMinorBD = new BigDecimal(amountMinor);
		amountMinorBD = amountMinorBD.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
	
		BigDecimal majorToBuy = amountMinorBD.divide(priceBD, major.getDecimalPlaces(), RoundingMode.DOWN);
	
		kv.clear();
		kv.add(new KeyValuePair("amount", majorToBuy.toString()));
		kv.add(new KeyValuePair("price", price));
		kv.add(new KeyValuePair("book", book));
	
		return refreshDataNow();
		
	}
	
	public OrderResult getOrderResult(){
		return r.getLimitOrderResponse().getBuy();
	}
}

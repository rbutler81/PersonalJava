
package com.quadrigacx.exchange;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.quadrigacx.api.QuadrigaMethods;
import com.quadrigacx.api.returnJson.ResponseWrapper;
import com.quadrigacx.api.returnJson.helpers.OrderResult;

import helpers.KeyValuePair;
import helpers.RateLimiter;

public class SellLimit extends QuadrigaCall{
	
	public SellLimit(){
		this.r = new ResponseWrapper(); 
	}
	
	public SellLimit(RateLimiter rl){
		super(rl);
		qm = QuadrigaMethods.SellLimitOrder;
	}
	
	public boolean sell(String price, String amountMajor, String book){
		kv.clear();
		kv.add(new KeyValuePair("amount", amountMajor));
		kv.add(new KeyValuePair("price", price));
		
		BigDecimal zero = new BigDecimal("0").setScale(0, RoundingMode.DOWN);
		BigDecimal pricebd = new BigDecimal(price).setScale(10, RoundingMode.DOWN);
		if (zero.compareTo(pricebd) == 0){
			System.out.println();
		}
		
		kv.add(new KeyValuePair("book", book));
		return refreshData();
	}
	
	public boolean sellNow(String price, String amountMajor, String book){
		kv.clear();
		kv.add(new KeyValuePair("amount", amountMajor));
		kv.add(new KeyValuePair("price", price));
		
		BigDecimal zero = new BigDecimal("0").setScale(0, RoundingMode.DOWN);
		BigDecimal pricebd = new BigDecimal(price).setScale(10, RoundingMode.DOWN);
		if (zero.compareTo(pricebd) == 0){
			System.out.println();
		}
		
		kv.add(new KeyValuePair("book", book));
		return refreshDataNow();
	}
	
	public OrderResult getOrderResult(){
		return r.getLimitOrderResponse().getSell();
	}
}

package com.quadrigacx.exchange;

import com.quadrigacx.api.QuadrigaMethods;
import com.quadrigacx.api.returnJson.ResponseWrapper;

import helpers.KeyValuePair;
import helpers.RateLimiter;

public class LookupOrder extends QuadrigaCall {
	
	public LookupOrder(){
		this.r = new ResponseWrapper(); 
	}
	
	public LookupOrder(RateLimiter rl){
		super(rl);
		qm = QuadrigaMethods.LookupOrder;
	}
	
	public boolean orderLookup(String id){
		kv.clear();
		kv.add(new KeyValuePair("id", id));
		return refreshData();
	}
	
	public boolean orderLookupNow(String id){
		kv.clear();
		kv.add(new KeyValuePair("id", id));
		return refreshDataNow();
	}
}

package com.quadrigacx.exchange;

import com.quadrigacx.api.QuadrigaMethods;
import com.quadrigacx.api.returnJson.ResponseWrapper;

import helpers.KeyValuePair;
import helpers.RateLimiter;

public class CancelOrder extends QuadrigaCall{

	public CancelOrder(){
		this.r = new ResponseWrapper(); 
	}
	
	public CancelOrder(RateLimiter rl){
		super(rl);
		qm = QuadrigaMethods.CancelOrder;
	}
	
	public boolean cancelOrder(String id){
		kv.clear();
		kv.add(new KeyValuePair("id", id));
		return refreshData();
	}
	
	public boolean cancelOrderNow(String id){
		kv.clear();
		kv.add(new KeyValuePair("id", id));
		return refreshDataNow();
	}
	
	public boolean isOrderCancelled(){
		return r.isOrderCanceled();
	}
	
	public boolean isOrderNotFound(){
		if (this.getErrCode() == 106) return true;
		else return false;
	}
}

package com.quadrigacx.exchange;

import java.util.ArrayList;
import java.util.List;

import com.quadrigacx.api.QuadrigaMethods;
import com.quadrigacx.api.returnJson.ResponseWrapper;
import com.quadrigacx.api.returnJson.helpers.Order;

import helpers.KeyValuePair;
import helpers.RateLimiter;

public class OpenOrders extends QuadrigaCall {

	private String book = "";
	
	public OpenOrders(){
		this.r = new ResponseWrapper();
	}
	
	public OpenOrders(RateLimiter rl, String book){
		super(rl);
		qm = QuadrigaMethods.OpenOrders;
		this.book = book;
		kv.clear();
		kv.add(new KeyValuePair("book", this.book));
	}
	
	public int getNumberOfOpenOrders(){
		while (!super.refreshData()){}
		return r.getOpenOrdersResponse().getOpenOrders().size();
	}
	
	public List<String> getOpenBuyOrders(){
		List<String> ob = new ArrayList<String>();
		while (!super.refreshData()){}
		List<Order> oo = r.getOpenOrdersResponse().getOpenOrders();
		if (oo.size() > 0){
			for (int i = 0; i < oo.size(); i++){
				if (oo.get(i).getType().equals("0")){
					ob.add(oo.get(i).getId());
				}
			}
		}
		return ob;
	}
	
	public List<String> getOpenSellOrders(){
		List<String> os = new ArrayList<String>();
		while (!super.refreshData()){}
		List<Order> oo = r.getOpenOrdersResponse().getOpenOrders();
		if (oo.size() > 0){
			for (int i = 0; i < oo.size(); i++){
				if (oo.get(i).getType().equals("1")){
					os.add(oo.get(i).getId());
				}
			}
		}
		return os;
	}
}

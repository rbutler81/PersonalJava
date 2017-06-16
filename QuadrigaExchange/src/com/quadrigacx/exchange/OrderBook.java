package com.quadrigacx.exchange;

import com.quadrigacx.exchange.QuadrigaCall;

import java.util.List;

import com.quadrigacx.api.QuadrigaMethods;
import com.quadrigacx.api.returnJson.ResponseWrapper;
import com.quadrigacx.api.returnJson.helpers.BidAsk;

import helpers.KeyValuePair;
import helpers.RateLimiter;

public class OrderBook extends QuadrigaCall {
	
	private String book = "";
	
	public OrderBook(){
		this.r = new ResponseWrapper(); 
	}
	
	public OrderBook(RateLimiter rl, String book){
		super(rl);
		qm = QuadrigaMethods.OrderBook;
		this.book = book;
		kv.clear();
		kv.add(new KeyValuePair("book", book));
		kv.add(new KeyValuePair("group", "0"));
		refreshData();
	}
	
	public String getBook() {
		return book;
	}
	
	public List<BidAsk> getAllBids(){
		return r.getOrderBookResponse().getBids();
	}
	
	public List<BidAsk> getAllAsks(){
		return r.getOrderBookResponse().getAsks();
	}

	public BidAsk getTopBid(){
		
		BidAsk ba = new BidAsk();
		
		ba.setAmount(r.getOrderBookResponse().getBids().get(0).getAmount());
		ba.setPrice(r.getOrderBookResponse().getBids().get(0).getPrice());
		
		return ba;
	}
	
	public BidAsk getTopAsk(){
		
		BidAsk ba = new BidAsk();
		
		ba.setAmount(r.getOrderBookResponse().getAsks().get(0).getAmount());
		ba.setPrice(r.getOrderBookResponse().getAsks().get(0).getPrice());
		
		return ba;
	}
	
	public double getSpread(){
		
		double ask = Double.parseDouble(r.getOrderBookResponse().getAsks().get(0).getPrice());
		double bid = Double.parseDouble(r.getOrderBookResponse().getBids().get(0).getPrice());
		return ((ask - bid) / bid) * 100.0;
	
	}
	
		
}

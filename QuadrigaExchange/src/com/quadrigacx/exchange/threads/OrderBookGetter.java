package com.quadrigacx.exchange.threads;


public class OrderBookGetter extends GenericThread implements Runnable {

	public OrderBookGetter(CommonData cd) {
		super(cd);
	}

	@Override
	public void run() {
		
		while (!cd.getOrderBook().getTc().isDone()){
			while (!cd.getOrderBook().getTc().isPaused()){
				
				//Refresh Order Book Data
				cd.getOrderBook().refreshData();
				
			}
		}
	}
	
	
	
	 
}

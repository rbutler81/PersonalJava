package com.quadrigacx.api.returnJson;

import com.quadrigacx.api.returnJson.helpers.OrderResult;

public class OrderResponse {

	private OrderResult buy;
	private OrderResult sell;
	
	public OrderResponse() {
		this.buy = new OrderResult();
		this.sell = new OrderResult();
	}

	public OrderResult getBuy() {
		return buy;
	}

	public void setBuy(OrderResult buy) {
		this.buy = buy;
	}

	public OrderResult getSell() {
		return sell;
	}

	public void setSell(OrderResult sell) {
		this.sell = sell;
	}
	
		
}

package com.quadrigacx.api.returnJson;

import java.util.ArrayList;
import java.util.List;

import com.quadrigacx.api.returnJson.helpers.Order;

public class OpenOrdersResponse {

	private List<Order> openOrders;

	public OpenOrdersResponse() {
		this.openOrders = new ArrayList<Order>();
	}

	public List<Order> getOpenOrders() {
		return openOrders;
	}

	public void setOpenOrders(List<Order> openOrders) {
		this.openOrders = openOrders;
	}
	
}

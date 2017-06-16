package com.kraken.api.json;

public class OpenOrderDescription {

	private String pair;
	private String type;
	private String orderType;
	private String price;
	private String price2;
	private String leverage;
	private String order;
	
	public OpenOrderDescription(String pair, String type, String orderType, String price, String price2,
			String leverage, String order) {
		this.pair = pair;
		this.type = type;
		this.orderType = orderType;
		this.price = price;
		this.price2 = price2;
		this.leverage = leverage;
		this.order = order;
	}
	
	public OpenOrderDescription() {
		this.pair = "";
		this.type = "";
		this.orderType = "";
		this.price = "";
		this.price2 = "";
		this.leverage = "";
		this.order = "";
	}

	public String getPair() {
		return pair;
	}

	public void setPair(String pair) {
		this.pair = pair;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrice2() {
		return price2;
	}

	public void setPrice2(String price2) {
		this.price2 = price2;
	}

	public String getLeverage() {
		return leverage;
	}

	public void setLeverage(String leverage) {
		this.leverage = leverage;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
}

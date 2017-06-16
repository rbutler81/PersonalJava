package com.quadrigacx.api.returnJson.helpers;

public class Order {

	private String amount;
	private String date;
	private String id;
	private String price;
	private String status;
	private String type;
	
	public Order() {
		this.amount = "";
		this.date = "";
		this.id = "";
		this.price = "";
		this.status = "";
		this.type = "";
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}

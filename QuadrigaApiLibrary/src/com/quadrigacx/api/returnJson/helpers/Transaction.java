package com.quadrigacx.api.returnJson.helpers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

	@JsonProperty("amount")
	private String amount;
	@JsonProperty("date")
	private String date;
	@JsonProperty("price")
	private String price;
	@JsonProperty("tid")
	private int tId;
	@JsonProperty("side")
	private String side;
	
	public Transaction(String amount, String date, String price, int tId, String side) {
		this.amount = amount;
		this.date = date;
		this.price = price;
		this.tId = tId;
		this.side = side;
	}
	
	public Transaction() {
		this.amount = "";
		this.date = "";
		this.price = "";
		this.tId = 0;
		this.side = "";
	}

	@JsonProperty("amount")
	public String getAmount() {
		return amount;
	}
	@JsonProperty("amount")
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@JsonProperty("date")
	public String getDate() {
		return date;
	}
	@JsonProperty("date")
	public void setDate(String date) {
		this.date = date;
	}
	
	@JsonProperty("price")
	public String getPrice() {
		return price;
	}
	@JsonProperty("price")
	public void setPrice(String price) {
		this.price = price;
	}

	@JsonProperty("tid")
	public int gettId() {
		return tId;
	}
	@JsonProperty("tid")
	public void settId(int tId) {
		this.tId = tId;
	}

	@JsonProperty("side")
	public String getSide() {
		return side;
	}
	@JsonProperty("side")
	public void setSide(String side) {
		this.side = side;
	}
	
}
